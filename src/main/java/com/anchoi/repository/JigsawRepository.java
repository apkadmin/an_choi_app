package com.anchoi.repository;

import com.anchoi.models.JigsawDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JigsawRepository extends JpaRepository<JigsawDataEntity, String> {

}
