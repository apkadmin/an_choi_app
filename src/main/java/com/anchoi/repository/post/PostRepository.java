package com.anchoi.repository.post;

import com.anchoi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,String> {
        List<Post> findAllByGroupId(String id);
        Post findFirstByGroupIdAndLanguageId(String groupId,String lang);
        void deleteAllByGroupId(String id);
        List<Post> findAllByLanguageIdOrderByCreatedDate(String langId);

        List<Post> findAllByLanguageId(String lang);
}
