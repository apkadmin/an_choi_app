package com.anchoi.controllers.admin;

import com.anchoi.entity.Category;
import com.anchoi.response.ResponseData;
import com.anchoi.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/admin/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody @NotNull Category request) {
        try {
            return ResponseEntity.ok(ResponseData.ok(categoryService.save(request)));
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @PostMapping("/save-all")
    public ResponseEntity<?> save(@RequestBody List<Category> request) {
        try {
            return ResponseEntity.ok(ResponseData.ok(categoryService.saveAll(request)));
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> delete(@RequestParam("id") @NotNull String id) {
        try {
            categoryService.delete(id);
            return ResponseEntity.ok(ResponseData.ok("Success!!"));
        } catch (Exception e) {
            return ResponseEntity.status(502).body(ResponseData.error(null,e.getMessage()));
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(ResponseData.ok(categoryService.getAll()));
        } catch (Exception e) {
            return ResponseEntity.status(502).body(e.getMessage());
        }
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<?> getById(@RequestParam("id") @NotNull String id) {
        try {
            return ResponseEntity.ok(ResponseData.ok(categoryService.getById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(502).body(e.getMessage());
        }
    }

    @GetMapping("/get-by-type")
    public ResponseEntity<?> getByType(@RequestParam("type") @NotNull String type, @RequestHeader(value = "lang",defaultValue = "vi") String langCode) {
        try {
            return ResponseEntity.ok(ResponseData.ok(categoryService.getAllByType(type, langCode)));
        } catch (Exception e) {
            return ResponseEntity.status(502).body(e.getMessage());
        }
    }
}
