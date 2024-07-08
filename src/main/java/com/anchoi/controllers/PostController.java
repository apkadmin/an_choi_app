package com.anchoi.controllers;
import com.anchoi.config.BusinessException;
import com.anchoi.entity.Post;
import com.anchoi.response.PostResponse;
import com.anchoi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

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
    public ResponseEntity<List<PostResponse>> getAllPosts(@RequestHeader(value = "lang",defaultValue = "vi") String langId) {
        return ResponseEntity.ok().body(postService.getAllPosts(langId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable String id, @RequestHeader("lang") String langId) {
        return ResponseEntity.ok().body(postService.getPostById(id, langId));
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok().body(postService.createPost(post));
    }

    @PostMapping("/all")
    public ResponseEntity<List<Post>> createPostAll(@RequestBody List<Post> posts) {
        return ResponseEntity.ok().body(postService.createPost(posts));
    }


    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable String id) {
        postService.deletePost(id);
    }
}