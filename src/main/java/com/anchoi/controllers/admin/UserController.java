package com.anchoi.controllers.admin;

import com.anchoi.models.User;
import com.anchoi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/admin/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody @NotNull User request) {
        try {
            return ResponseEntity.ok(userService.update(request));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> delete(@RequestParam("id") @NotNull String id) {
        try {
            userService.delete(id);
            return ResponseEntity.ok("Deleted");
        } catch (Exception e) {
            return ResponseEntity.status(502).body(e.getMessage());
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
        } catch (Exception e) {
            return ResponseEntity.status(502).body(e.getMessage());
        }
    }
}
