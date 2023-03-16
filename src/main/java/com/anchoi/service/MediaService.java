package com.anchoi.service;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import com.anchoi.models.Media;
import com.anchoi.request.MediaRequest;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {
    public void init();

    public List<Media> save(MediaRequest mediaRequest, MultipartFile[] medias);

    public Resource load(String filename);

    public void deleteAll();

    public boolean deleteByUrl(String filename);

    public Stream<Path> loadAll();
}

