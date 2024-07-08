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
    @Query("SELECT new com.anchoi.response.PointVietnamV1Response(point.id,t.categoryId,point.itemId,point.parentId,point.x,point.y,t.districtId,tn.name) FROM PointVietnamEntity point JOIN Item t ON t.id = point.itemId join  ItemI18n tn on tn.itemId = t.id WHERE point.parentId = :parentId and tn.languageId = :language")
    List<PointVietnamV1Response> getByParentID(String parentId, String language);
    void deleteByParentId(String parentId);
}
