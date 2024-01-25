package com.anchoi.repository.media;

import com.anchoi.entity.Media;
import com.anchoi.response.MediaResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media, String> {

    Media findByUrl(String url);
 }
