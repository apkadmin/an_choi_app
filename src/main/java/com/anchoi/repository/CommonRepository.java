package com.anchoi.repository;

import com.anchoi.response.MediaResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CommonRepository {
    List<MediaResponse> findAllByIdReferOrderByIndex(String id,String lang);
}
