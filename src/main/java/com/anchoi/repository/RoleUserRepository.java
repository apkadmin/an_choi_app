package com.anchoi.repository;

import com.anchoi.models.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleUserRepository extends JpaRepository<RoleUser, String> {
    RoleUser findFirstByUserId(String userId);
}
