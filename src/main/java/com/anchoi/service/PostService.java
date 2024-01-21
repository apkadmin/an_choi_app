package com.anchoi.service;

import com.anchoi.common.CommonUtils;
import com.anchoi.config.BusinessException;
import com.anchoi.entity.Post;
import com.anchoi.repository.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts(String langId) {
        if(langId == null){
            langId = "vi";
        }
        return postRepository.findAllByLanguageIdOrderByCreatedDate(langId);
    }

    public Post getPostById(String id, String langId) {
        Post post = postRepository.findFirstByGroupIdAndLanguageId(id,langId);
        if(CommonUtils.isEmpty(post)){
            post = new Post();
            post.setGroupId(id);
            post.setLanguageId(langId);
            post.setId(UUID.randomUUID().toString());
        }
            return post;
    }

    public Post createPost(Post post) {
        post.setUpdatedDate(new Date());
        return postRepository.save(post);
    }

    @Transactional
    public List<Post> createPost(List<Post> posts) {
        List<Post> postResult = postRepository.saveAllAndFlush(posts);
        return postResult;
    }

    public Post updatePost(String id, Post updatedPost) throws BusinessException{
        Optional<Post> existingPost = postRepository.findById(id);
        if (existingPost.isPresent()) {
            Post post = existingPost.get();
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
