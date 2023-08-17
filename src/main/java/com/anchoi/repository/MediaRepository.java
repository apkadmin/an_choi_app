package com.anchoi.repository;

import com.anchoi.models.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media, String> {

    Media findByUrl(String url);

    List<Media> findAllByIdReferOrderByIndex(String idRefer);
}
