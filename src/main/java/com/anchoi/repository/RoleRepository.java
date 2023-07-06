package com.anchoi.repository;

import java.util.List;
import java.util.Optional;

import com.anchoi.models.ERole;
import com.anchoi.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
  Optional<Role> findByName(ERole name);

    List<Role> findAllByNameIn(List<ERole> roles);
}
