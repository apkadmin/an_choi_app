package com.anchoi.repository.media;

import com.anchoi.entity.MediaI18n;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaI18nRepository extends JpaRepository<MediaI18n, String> {

    void deleteAllByMediaId(String mediaId);
}
