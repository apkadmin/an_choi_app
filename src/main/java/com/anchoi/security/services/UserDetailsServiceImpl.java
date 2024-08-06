package com.anchoi.security.services;

import com.anchoi.entity.RoleUser;
import com.anchoi.entity.User;
import com.anchoi.repository.manage.RoleUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anchoi.repository.manage.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepository userRepository;
  @Autowired
  RoleUserRepository roleUserRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    RoleUser roleUser = roleUserRepository.findFirstByUserId(username);
    return UserDetailsImpl.build(user,roleUser);
  }

}
