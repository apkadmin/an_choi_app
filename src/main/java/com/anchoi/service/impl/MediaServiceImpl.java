package com.anchoi.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Month;
import java.time.Year;
import java.util.*;
import java.util.stream.Stream;

import com.anchoi.models.Media;
import com.anchoi.repository.MediaRepository;
import com.anchoi.request.MediaRequest;
import com.anchoi.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MediaServiceImpl implements MediaService {

    @Value("${base.uri}")
    private String baseUri;
    @Value("${base.uri.separate}")
    private String separate;
    private Path root;
    @Autowired
    private MediaRepository mediaRepository;

    @Override
    public void init() {
        try {
            Date now = new Date();
            int year = Calendar.getInstance().get(Calendar.YEAR);
            int month = Calendar.getInstance().get(Calendar.MONTH);
            int day = Calendar.getInstance().get(Calendar.DATE);
            String uri = baseUri + separate + year + separate + month + separate + day;
            root = Paths.get(uri);
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public List<Media> save(MediaRequest mediaRequest, MultipartFile[] medias) {
        init();
        List<Media> mediaList = new ArrayList<>();
        try {
            for (MultipartFile media:medias) {
                Path path = this.root.resolve(new Date().getTime() + "_" + media.getOriginalFilename());
                Files.copy(media.getInputStream(), path);
                Media mediaEnt = new Media().builder()
                        .url(path.toString())
                        .typeMedia(mediaRequest.getTypeMedia())
                        .type(mediaRequest.getType())
                        .idRefer(mediaRequest.getIdRefer())
                        .build();
                mediaList.add(mediaEnt);
            }
            return mediaRepository.saveAll(mediaList);
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Media> saveV2(MediaRequest mediaRequest, MultipartFile[] medias) {
        List<Media> mediaList = new ArrayList<>();
        try {
            for (MultipartFile media:medias) {
                byte[] encoded = Base64Utils.encode(media.getBytes());
                String fileEncode = new String(encoded);
                Media mediaEnt = new Media().builder()
                        .url(fileEncode)
                        .typeMedia(mediaRequest.getTypeMedia())
                        .type(mediaRequest.getType())
                        .idRefer(mediaRequest.getIdRefer())
                        .fileName(media.getOriginalFilename())
                        .build();
                mediaList.add(mediaEnt);
            }
            return mediaRepository.saveAll(mediaList);
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1)
                    .filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

    public void deleteById(String id){
         mediaRepository.deleteById(id);
    }
    @Override
    public boolean deleteByUrl(String url) {
        try {
            Media media = mediaRepository.findByUrl(url);
            if (media != null)
                mediaRepository.delete(media);
            String[] arr = url.split(separate);
            String fileName = arr[arr.length - 1];
            String path = "";
            for (int i = 0; i < arr.length - 1; i++) {
                path += separate + arr[i];
            }
            Path file = Paths.get(path).resolve(fileName);
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public List<Media> loadById(String idRefer) {
        return mediaRepository.findAllByIdRefer(idRefer);
    }
}
