package com.anchoi.repository.point;

import com.anchoi.entity.PointVietnamEntity;
import com.anchoi.response.PointVietnamV1Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointVietnamRepository extends JpaRepository<PointVietnamEntity, String> {
    List<PointVietnamEntity> findAllByParentId(String parentId);
//    @Query("SELECT new com.anchoi.response.PointVietnamV1Response(point.id,t.categoryId,point.itemId,point.parentId,point.x,point.y,t.districtId,'') FROM PointVietnamEntity point JOIN com.anchoi.models.Item t ON t.id = point.itemId WHERE point.parentId = :parentId")
//    List<PointVietnamV1Response> getByParentID(String parentId);
    void deleteByParentId(String parentId);
}
