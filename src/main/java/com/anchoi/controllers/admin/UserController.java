package com.anchoi.controllers.admin;

import com.anchoi.config.BusinessException;
import com.anchoi.request.ChangePasswordRequest;
import com.anchoi.request.UserRequest;
import com.anchoi.response.MessageResponse;
import com.anchoi.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/admin/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody @NotNull UserRequest request) {
        try {
            return ResponseEntity.ok(userService.update(request));
        } catch (BusinessException e) {
            return ResponseEntity.ok(e);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam("id") @NotNull String id) {
        try {
            userService.delete(id);
            return ResponseEntity.ok("Deleted");
        } catch (BusinessException ex) {
            return ResponseEntity.ok(ex);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(userService.getAll());
        } catch (Exception e) {
            return ResponseEntity.status(502).body(e.getMessage());
        }
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<?> getById(@RequestParam("id") @NotNull String id) {
        try {
            return ResponseEntity.ok(userService.getById(id));
        } catch (BusinessException ex) {
            return ResponseEntity.ok(ex);
        }
    }

    @GetMapping("/get-by-username")
    public ResponseEntity<?> getByUsername(@RequestParam("username") @NotNull String username) {
        try {
            return ResponseEntity.ok(userService.getByUsername(username));
        } catch (BusinessException ex) {
            return ResponseEntity.ok(ex);
        }
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@NotNull @RequestBody ChangePasswordRequest request) {
        try {
            Boolean isUpdated = userService.updatePassword(request);
            if (isUpdated)
                return ResponseEntity.ok(new MessageResponse("Your password has changed"));
            return ResponseEntity.ok("Password has not changed");
        } catch (BusinessException ex) {
            return ResponseEntity.ok(ex);
        }
    }
}
