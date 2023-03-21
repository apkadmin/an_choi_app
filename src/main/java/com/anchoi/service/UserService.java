package com.anchoi.service;

import com.anchoi.config.BusinessException;
import com.anchoi.models.User;
import com.anchoi.repository.UserRepository;
import com.anchoi.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserResponse update(User request) throws BusinessException {
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
        User userEnt = userCheckOpt.get();
        request.setPassword(userEnt.getPassword());
        // khong cho phep sua role cua user admin
        if ("admin".equalsIgnoreCase(request.getUsername())) {
            request.setRoles(userEnt.getRoles());
        }

        User user =  userRepository.save(request);

        return convertToResponse(user);
    }

    public void delete(String id) {
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
                .roles(u.getRoles().stream().map(role->role.getName().toString()).collect(Collectors.toList()))
                .build();
    }

    public UserResponse getById(String id) throws BusinessException {
        Optional<User> uOpt = userRepository.findById(id);
        if (!uOpt.isPresent())
            throw new BusinessException("400", "User not found");
        User user = uOpt.get();
        return convertToResponse(user);
    }
}
