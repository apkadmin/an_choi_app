package com.anchoi.controllers.admin;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.anchoi.config.BusinessException;
import com.anchoi.entity.RoleUser;
import com.anchoi.entity.User;
import com.anchoi.request.LoginRequest;
import com.anchoi.request.SignupRequest;
import com.anchoi.repository.manage.RoleUserRepository;
import com.anchoi.repository.manage.UserRepository;
import com.anchoi.response.JwtResponse;
import com.anchoi.response.MessageResponse;
import com.anchoi.response.ResponseData;
import com.anchoi.security.jwt.JwtUtils;
import com.anchoi.security.services.UserDetailsImpl;
import com.anchoi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  UserService userService;

  @Autowired
  RoleUserRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    RoleUser roleUser = roleRepository.findFirstByUserId(userDetails.getUsername());
    if(userDetails.getUsername().toLowerCase().equals("admin")){
      roleUser = new RoleUser();
      roleUser.setUserId(userDetails.getUsername());
      roleUser.setObjectList("ADMIN");
      roleUser.setRoleList("ADMIN");
    }
    String jwt = jwtUtils.generateJwtToken(authentication,roleUser);

    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());
    if(userDetails.getUsername().toLowerCase().equals("admin")){
      roles = List.of("ADMIN");
    }
    return ResponseEntity.ok(new JwtResponse(jwt,
            userDetails.getId(),
            userDetails.getUsername(),
            userDetails.getEmail(),
            roles));
  }

  @PostMapping("/signup")
  @Transactional
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: email is already taken!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(),
                         signUpRequest.getEmail(),
                         encoder.encode(signUpRequest.getPassword()));
    RoleUser roleUser = signUpRequest.getRole();
    roleUser.setId(UUID.randomUUID());
    roleUser.setUserId(user.getUsername());
    roleRepository.save(roleUser);
    userRepository.save(user);
    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }


  @PostMapping("/create-user")
  @PreAuthorize("hasAuthority('ADMIN')")
  @Transactional
  public ResponseEntity<?> createUser(@RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: email is already taken!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            encoder.encode(signUpRequest.getPassword()));
    user.setPhone(signUpRequest.getPhone());
    RoleUser roleUser = new RoleUser();
    roleUser.setId(UUID.randomUUID());
    roleUser.setObjectList(signUpRequest.getRole().getObjectList());
    roleUser.setRoleList(signUpRequest.getRole().getRoleList());
    roleUser.setUserId(signUpRequest.getUsername());
  roleRepository.save(roleUser);
    userRepository.save(user);
    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @PostMapping("/update-user")
  @PreAuthorize("hasAuthority('ADMIN')")
  @Transactional
  public ResponseEntity<?> updateUser(@RequestBody SignupRequest signUpRequest) {

    // Create new user's account
   Optional<User> user = userRepository.findByUsername(signUpRequest.getUsername());
    if(user.isPresent()) {
      if (signUpRequest.getPassword() != null && !signUpRequest.getPassword().isEmpty()) {
        user.get().setPassword(encoder.encode(signUpRequest.getPassword()));
      }
      user.get().setEmail(signUpRequest.getEmail());
      user.get().setPhone(signUpRequest.getPhone());
      RoleUser roleUser = roleRepository.findFirstByUserId(signUpRequest.getUsername());
      roleUser.setId(signUpRequest.getRole().getId());
      roleUser.setObjectList(signUpRequest.getRole().getObjectList());
      roleUser.setRoleList(signUpRequest.getRole().getRoleList());
      roleUser.setUserId(signUpRequest.getUsername());
      roleRepository.save(roleUser);
      userRepository.save(user.get());
    } else {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: User name error"));
    }
    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }



  @GetMapping("/get-user")
  @PreAuthorize("hasAuthority('ADMIN')")
  @Transactional
  public ResponseEntity<?> getUser(@RequestParam String userName) throws BusinessException {
    return ResponseEntity.ok(userService.getByUsername(userName));
  }


  @PostMapping("/signout")
  public ResponseEntity<?> logoutUser() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(new MessageResponse("You've been signed out!"));
  }

  @PostMapping("/verify")
  public ResponseEntity<?> verifyToken(@RequestHeader(value = "Authorization", required = false) String authHeader) {
    try {
      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        return ResponseEntity.status(401).body(new MessageResponse("Unauthorized: Missing or invalid token"));
      }

      String token = authHeader.substring(7);
      
      if (!jwtUtils.validateJwtToken(token)) {
        return ResponseEntity.status(401).body(new MessageResponse("Unauthorized: Invalid or expired token"));
      }

      String username = jwtUtils.getUserNameFromJwtToken(token);
      Optional<User> user = userRepository.findByUsername(username);
      
      if (!user.isPresent()) {
        return ResponseEntity.status(401).body(new MessageResponse("Unauthorized: User not found"));
      }

      RoleUser roleUser = roleRepository.findFirstByUserId(username);
      if (username.toLowerCase().equals("admin")) {
        roleUser = new RoleUser();
        roleUser.setUserId(username);
        roleUser.setObjectList("ADMIN");
        roleUser.setRoleList("ADMIN");
      }

      List<String> roles = new java.util.ArrayList<>();
      if (roleUser != null && roleUser.getRoleList() != null) {
        roles.add(roleUser.getRoleList());
      } else {
        roles.add("ROLE_USER");
      }

      return ResponseEntity.ok(ResponseData.ok(new JwtResponse(token, user.get().getId(), username, user.get().getEmail(), roles)));
      
    } catch (Exception e) {
      return ResponseEntity.status(401).body(new MessageResponse("Unauthorized: " + e.getMessage()));
    }
  }
}
