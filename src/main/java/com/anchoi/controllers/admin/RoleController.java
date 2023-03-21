package com.anchoi.controllers.admin;

import com.anchoi.models.Role;
import com.anchoi.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/admin/role")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

//    @PostMapping("/save")
//    @PreAuthorize("hasAnyRole('ADMIN')")
//    public ResponseEntity<?> save(@RequestBody @NotNull Role request) {
//        try {
//            return ResponseEntity.ok(roleService.save(request));
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body(e.getMessage());
//        }
//    }

//    @DeleteMapping("/delete")
//    @PreAuthorize("hasAnyRole('ADMIN')")
//    public ResponseEntity<?> delete(@RequestParam("id") @NotNull String id) {
//        try {
//            roleService.delete(id);
//            return ResponseEntity.ok("Deleted");
//        } catch (Exception e) {
//            return ResponseEntity.status(502).body(e.getMessage());
//        }
//    }

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
