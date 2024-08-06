package com.anchoi.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import com.anchoi.config.BusinessException;
import com.anchoi.entity.Media;
import com.anchoi.request.MediaRequest;
import com.anchoi.response.MediaResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {
    public void init();

    public List<Media> save(MediaRequest mediaRequest, MultipartFile[] medias) throws BusinessException;
    public List<Media> saveV2(MediaRequest mediaRequest, MultipartFile[] medias);

    public Resource load(String filename);

    public void deleteAll();

    public boolean deleteByUrl(String filename);

    public void deleteById(String fileId) throws IOException;

    public Stream<Path> loadAll();

    List<MediaResponse> loadByRefId(String id, String lang);

    String uploadMedia(MultipartFile media,String type);
    public void updateMediaDes(String id,String des, String lang);
    List<Media> saveAll(List<Media> items);

    public boolean deleteByUrlNotMedia(String url);
}

