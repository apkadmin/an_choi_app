package com.anchoi.repository.jigsaw;

import com.anchoi.entity.JigsawDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JigsawDetailRepository extends JpaRepository<JigsawDetailEntity, String> {
    void deleteAllByParentId(String id);
}
