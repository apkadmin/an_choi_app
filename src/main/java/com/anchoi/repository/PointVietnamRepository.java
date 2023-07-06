package com.anchoi.repository;

import com.anchoi.models.PointVietnamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointVietnamRepository extends JpaRepository<PointVietnamEntity, String> {
    List<PointVietnamEntity> findAllByParentId(String parentId);
    void deleteByParentId(String parentId);
}
