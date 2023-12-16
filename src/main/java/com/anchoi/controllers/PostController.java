package com.anchoi.controllers;
import com.anchoi.config.BusinessException;
import com.anchoi.entity.Post;
import com.anchoi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok().body(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Post>> getPostById(@PathVariable String id) {
        return ResponseEntity.ok().body(postService.getPostById(id));
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        return ResponseEntity.ok().body(postService.createPost(post));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable String id, @RequestBody Post updatedPost) throws BusinessException {
        return ResponseEntity.ok().body(postService.updatePost(id, updatedPost));
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable String id) {
        postService.deletePost(id);
    }
}