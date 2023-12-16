package com.anchoi.service;

import com.anchoi.config.BusinessException;
import com.anchoi.entity.Post;
import com.anchoi.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(String id) {
        return postRepository.findById(id);
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post updatePost(String id, Post updatedPost) throws BusinessException{
        Optional<Post> existingPost = postRepository.findById(id);
        if (existingPost.isPresent()) {
            Post post = existingPost.get();
            post.setTitle(updatedPost.getTitle());
            post.setBody(updatedPost.getBody());
            post.setUpdatedDate(new Date());
            post.setUpdatedBy(updatedPost.getUpdatedBy());
            return postRepository.save(post);
        } else {
            throw new BusinessException("Không tìm thấy bài viết với ID: " + id,"404");
        }
    }

    public void deletePost(String id) {
        postRepository.deleteById(id);
    }
}
