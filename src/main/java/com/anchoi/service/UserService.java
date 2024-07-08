package com.anchoi.service;

import com.anchoi.config.BusinessException;
import com.anchoi.entity.RoleUser;
import com.anchoi.entity.User;
import com.anchoi.repository.manage.RoleUserRepository;
import com.anchoi.repository.manage.UserRepository;
import com.anchoi.request.ChangePasswordRequest;
import com.anchoi.request.UserRequest;
import com.anchoi.response.UserResponse;
import com.anchoi.security.jwt.JwtUtils;
import com.anchoi.security.services.UserDetailsImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleUserRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public UserService(UserRepository userRepository, RoleUserRepository roleRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }


    public UserResponse update(UserRequest request) throws BusinessException {
        if (request.getId() == null)
            throw new BusinessException("400", "Id not null");
        // phải tồn tại record với id được truyền lên
        Optional<User> userOpt = userRepository.findById(request.getId());
        if (!userOpt.isPresent())
            throw new BusinessException("400", "User not found");
        // username của record với id truyền lên phải không đuợc thay đổi
        Optional<User> userCheckOpt = userRepository.findByUsernameAndId(request.getUsername(), request.getId());
        if (!userCheckOpt.isPresent())
            throw new BusinessException("400", "Username not allow to change");

        if (StringUtils.isBlank(request.getEmail()))
            throw new BusinessException("400", "email not empty");
        List<User> emailCheck = userRepository.findByEmailAndId(request.getEmail(), request.getId());
        if (!emailCheck.isEmpty())
            throw new BusinessException("400", "email existed");

        User userEnt = userCheckOpt.get();
        userEnt.setName(request.getName());
        userEnt.setEmail(request.getEmail());
        userEnt.setPhone(request.getPhone());
        User user =  userRepository.save(userEnt);

        return convertToResponse(user);
    }

    public void delete(String id) throws BusinessException {
        // phải tồn tại record với id được truyền lên
        Optional<User> uOpt = userRepository.findById(id);
        if (!uOpt.isPresent())
            throw new BusinessException("400", "User not found");
        User user = uOpt.get();
        if ("admin".equalsIgnoreCase(user.getUsername()))
            throw new BusinessException("999", "User cannot delete");
        userRepository.deleteById(id);
    }

    public List<UserResponse> getAll() {
        List<User> users = userRepository.findAll();
        List<UserResponse> result = users.stream().map(this::convertToResponse).collect(Collectors.toList());

        return result;
    }

    private UserResponse convertToResponse(User u) {
        return UserResponse.builder()
                .id(u.getId())
                .phone(u.getPhone())
                .email(u.getEmail())
                .createdDate(u.getCreatedDate())
                .createdBy(u.getCreatedBy())
                .updatedDate(u.getUpdatedDate())
                .updatedBy(u.getUpdatedBy())
                .name(u.getName())
                .username(u.getUsername())
                .build();
    }

    public UserResponse getById(String id) throws BusinessException {
        Optional<User> uOpt = userRepository.findById(id);
        if (!uOpt.isPresent())
            throw new BusinessException("400", "User not found");
        User user = uOpt.get();
        return convertToResponse(user);
    }

    public UserResponse getByUsername(String username) throws BusinessException {
        Optional<User> uOpt = userRepository.findByUsername(username);
        if (!uOpt.isPresent())
            throw new BusinessException("400", "User not found");
        User user = uOpt.get();
        RoleUser roleUser = roleRepository.findFirstByUserId(username);
        UserResponse userResponse = convertToResponse(user);
        userResponse.setRole(roleUser);
        return userResponse;
    }

    public boolean updatePassword(ChangePasswordRequest request) throws BusinessException {
//        ResponseCookie cookie = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Optional<User> loginUserOpt = userRepository.findById(userDetails.getId());
        if (loginUserOpt.isPresent()) {
            User loginUser = loginUserOpt.get();
            if (!checkIfValidOldPassword(loginUser, request.getOldPassword())) {
                throw new BusinessException("400", "Invalid password");
            }
            changeUserPassword(loginUser, request.getNewPassword());
//            cookie = jwtUtils.getCleanJwtCookie();
//            jwtUtils.expireToken((String) authentication.getCredentials());
            return true;
        }
        return false;
    }

    public boolean checkIfValidOldPassword(final User user, final String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    public void changeUserPassword(final User user, final String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

}
