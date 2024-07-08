package com.anchoi.service;

import com.anchoi.entity.RoleUser;
import com.anchoi.repository.manage.RoleUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleUserService {
    private final RoleUserRepository roleRepository;

    public RoleUserService(RoleUserRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    public RoleUser save(RoleUser request) {
        return roleRepository.save(request);
    }

    public void delete(String id) {
        roleRepository.deleteById(id);
    }

    public List<RoleUser> getAll() {
        return roleRepository.findAll();
    }

    public Optional<RoleUser> getById(String id) {
        return roleRepository.findById(id);
    }
}
