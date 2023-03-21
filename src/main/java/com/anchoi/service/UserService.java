package com.anchoi.service;

import com.anchoi.config.BusinessException;
import com.anchoi.models.User;
import com.anchoi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User update(User request) throws BusinessException {
        if (request.getId() == null)
            throw new BusinessException("400", "Id not null");
        Optional<User> userOpt = userRepository.findById(request.getId());
        if (!userOpt.isPresent())
            throw new BusinessException("400", "User not found");
        User userEnt = userOpt.get();
        request.setPassword(userEnt.getPassword());
        return userRepository.save(request);
    }

    public void delete(String id) {
        userRepository.deleteById(id);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> getById(String id) {
        return userRepository.findById(id);
    }
}
