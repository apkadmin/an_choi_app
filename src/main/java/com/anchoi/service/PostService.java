package com.anchoi.service;

import com.anchoi.common.CommonUtils;
import com.anchoi.config.BusinessException;
import com.anchoi.entity.Post;
import com.anchoi.repository.post.PostRepository;
import com.anchoi.response.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final SyncService syncService;

    @Autowired
    public PostService(PostRepository postRepository, SyncService syncService) {
        this.postRepository = postRepository;
        this.syncService = syncService;
    }

    public List<PostResponse> getAllPosts(String langId) {
        if(langId == null){
            langId = "vi";
        }
        List<Post> posts = postRepository.findAllByLanguageIdOrderByCreatedDate(langId);
        return posts.stream().map(e -> CommonUtils.toObject(e, PostResponse.class)).collect(Collectors.toList());
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

    public Post createPost(Post post) throws ExecutionException, InterruptedException {
        post.setUpdatedDate(new Date());
        Optional<Post> existingPost = postRepository.findById(post.getId());
        if(existingPost.isPresent() && !post.getTitle().equals(existingPost.get().getTitle())) {
            syncService.syncData(post.getGroupId(), existingPost.get().getTitle(), post.getTitle(), post.getLanguageId());
        } else {
            syncService.syncData(post.getGroupId(), null, post.getTitle(), post.getLanguageId());
        }
        return postRepository.save(post);
    }

    @Transactional
    public List<Post> createPost(List<Post> posts) {
        if(posts != null && !posts.isEmpty()) {
            List<Post> oldPost = postRepository.findAllByGroupId(posts.get(0).getGroupId());
                posts.forEach(i -> {
                    AtomicBoolean isExsis = new AtomicBoolean(false);
                    if (oldPost != null && !oldPost.isEmpty()) {
                        oldPost.forEach(item -> {
                            if (item.getLanguageId().equals(i.getLanguageId())) {
                                if (!item.getTitle().equals(i.getTitle())) {
                                    try {
                                        syncService.syncData(item.getGroupId(), item.getTitle(), i.getTitle(), item.getLanguageId());
                                    } catch (ExecutionException e) {
                                        throw new RuntimeException(e);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                } else {
                                    try {
                                        syncService.syncData(item.getGroupId(), null, i.getTitle(), item.getLanguageId());
                                    } catch (ExecutionException e) {
                                        throw new RuntimeException(e);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                            if (item.getLanguageId().equals(i.getLanguageId())) {
                                isExsis.set(true);
                            }
                        });
                    }

                if(!isExsis.get()) {
                    try {
                        syncService.syncData(i.getGroupId(),null,i.getTitle(),i.getLanguageId());
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                });
            List<Post> postResult = postRepository.saveAllAndFlush(posts);
            return postResult;
        }
        return new ArrayList<>();
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

    @Transactional
    public void deletePost(String id) {
        List<Post> existingPost = postRepository.findAllByGroupId(id);
        if(existingPost != null && !existingPost.isEmpty()) {
            existingPost.forEach(i -> {
                try {
                    syncService.syncData(id, i.getTitle(), "", i.getLanguageId());
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        postRepository.deleteAllByGroupId(id);
    }
}
