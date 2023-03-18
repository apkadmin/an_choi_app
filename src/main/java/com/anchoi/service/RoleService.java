package com.anchoi.service;

import com.anchoi.models.Role;
import com.anchoi.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    public Role save(Role request) {
        return roleRepository.save(request);
    }

    public void delete(String id) {
        roleRepository.deleteById(id);
    }

    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    public Optional<Role> getById(String id) {
        return roleRepository.findById(id);
    }
}
