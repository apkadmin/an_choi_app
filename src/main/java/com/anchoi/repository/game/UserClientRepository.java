package com.anchoi.repository.game;

import com.anchoi.entity.GameUser;
import com.anchoi.entity.UserClient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserClientRepository extends JpaRepository<UserClient, String> {

}