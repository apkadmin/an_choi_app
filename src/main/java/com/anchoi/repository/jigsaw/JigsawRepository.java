package com.anchoi.repository.jigsaw;

import com.anchoi.entity.JigsawDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JigsawRepository extends JpaRepository<JigsawDataEntity, String> {

}
