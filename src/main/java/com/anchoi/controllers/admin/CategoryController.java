package com.anchoi.controllers.admin;

import com.anchoi.models.Category;
import com.anchoi.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/admin/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> save(@RequestBody @NotNull Category request) {
        try {
            return ResponseEntity.ok(categoryService.save(request));
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> delete(@RequestParam("id") @NotNull String id) {
        try {
            categoryService.delete(id);
            return ResponseEntity.ok("Deleted");
        } catch (Exception e) {
            return ResponseEntity.status(502).body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(categoryService.getAll());
        } catch (Exception e) {
            return ResponseEntity.status(502).body(e.getMessage());
        }
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<?> getById(@RequestParam("id") @NotNull String id) {
        try {
            return ResponseEntity.ok(categoryService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.status(502).body(e.getMessage());
        }
    }

    @GetMapping("/get-by-type")
    public ResponseEntity<?> getByType(@RequestParam("type") @NotNull String type) {
        try {
            return ResponseEntity.ok(categoryService.getAllByType(type));
        } catch (Exception e) {
            return ResponseEntity.status(502).body(e.getMessage());
        }
    }
}
