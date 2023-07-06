package com.anchoi.repository;

import java.util.List;
import java.util.Optional;

import com.anchoi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Optional<User> findByUsernameAndId(String username, String Id);

  Boolean existsByEmail(String email);

  @Query(value = "select * from user u where u.id != :id and u.email= :email", nativeQuery = true)
  List<User> findByEmailAndId(String email, String id);
}
