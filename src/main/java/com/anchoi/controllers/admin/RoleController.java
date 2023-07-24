package com.anchoi.controllers.admin;

import com.anchoi.service.RoleUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/admin/role")
public class RoleController {
    private final RoleUserService roleService;

    public RoleController(RoleUserService roleService) {
        this.roleService = roleService;
    }


    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(roleService.getAll());
        } catch (Exception e) {
            return ResponseEntity.status(502).body(e.getMessage());
        }
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<?> getById(@RequestParam("id") @NotNull String id) {
        try {
            return ResponseEntity.ok(roleService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.status(502).body(e.getMessage());
        }
    }
}
