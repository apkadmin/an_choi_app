package com.anchoi.repository;

import com.anchoi.models.PointVietnamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointVietnamRepository extends JpaRepository<PointVietnamEntity, String> {

}
