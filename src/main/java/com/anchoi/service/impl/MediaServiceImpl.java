package com.anchoi.service.impl;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import com.anchoi.common.CommonUtils;
import com.anchoi.common.FileUtils;
import com.anchoi.config.BusinessException;
import com.anchoi.entity.Media;
import com.anchoi.entity.MediaI18n;
import com.anchoi.entity.ffmpeg.FFmpegUtils;
import com.anchoi.entity.ffmpeg.TranscodeConfig;
import com.anchoi.repository.CommonRepository;
import com.anchoi.repository.media.MediaI18nRepository;
import com.anchoi.repository.media.MediaRepository;
import com.anchoi.request.MediaRequest;
import com.anchoi.response.MediaResponse;
import com.anchoi.service.MediaService;
import com.anchoi.service.UploadVideoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    @Value("${base.uri}")
    private String baseUri;
    @Value("${base.uri.separate}")
    private String separate;
    private Path rootImage;
    private Path rootAudio;
    private Path rootVideo;
    private String pathUrlImage;
    private String pathUrlVideo;
    private String pathUrlAudio;

    private final MediaRepository mediaRepository;
    private final CommonRepository commonRepository;
    private final MediaI18nRepository mediaI18nRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadVideoService.class);
    @Override
    public void init() {
        try {
            pathUrlVideo = baseUri + separate + "upload";
            rootVideo = Paths.get(pathUrlVideo);
            Files.createDirectories(rootVideo);
        }catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
        try {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            int month = Calendar.getInstance().get(Calendar.MONTH);
            int day = Calendar.getInstance().get(Calendar.DATE);
            pathUrlImage = baseUri + separate + "uploads" + separate + year + separate + month + separate + day;
            rootImage = Paths.get(pathUrlImage);
            Files.createDirectories(rootImage);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }

        try {
            pathUrlAudio = baseUri + separate + "audios";
            rootAudio = Paths.get(pathUrlAudio);
            Files.createDirectories(rootAudio);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public List<Media> save(MediaRequest mediaRequest, MultipartFile[] medias) throws BusinessException {
        init();
        List<Media> mediaList = new ArrayList<>();
        try {
            for (MultipartFile media : medias) {
                String mimeType = FileUtils.getRealMimeType(media);
                if(mediaRequest.getTypeMedia().contains("VIDEO") && !mimeType.startsWith("video")){
                    throw new BusinessException("500", "Kiểu file và loại danh mục không phù hợp");
                }
                if(mediaRequest.getTypeMedia().contains("IMAGE") && !mimeType.startsWith("image")){
                    throw new BusinessException("500", "Kiểu file và loại danh mục không phù hợp");
                }
            }
            List<CompletableFuture> completableFutures = new ArrayList<>();
            for (MultipartFile media : medias) {
                String url = "";
                Path path = null;
                String mimeType = FileUtils.getRealMimeType(media);
                if(mimeType.startsWith("video")) {
                    path = this.rootVideo.resolve(new Date().getTime() + "_" + media.getOriginalFilename());
                    url = pathUrlVideo + separate + path.getFileName();
                    completableFutures.add(transcodeToM3u8(media,path));
                } else if (mimeType.startsWith("image")) {
                    path = this.rootImage.resolve(new Date().getTime() + "_" + media.getOriginalFilename());
                    url = pathUrlImage + separate + path.getFileName();
                } else {
                    throw new BusinessException("500", "File not support");
                }
//                Files.write(Paths.get(String.join(File.separator, path.getParent().toString(), path.getFileName().toString())),
//                        media.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                Files.copy(media.getInputStream(), path);

                Media mediaEnt = new Media().builder()
                        .url(url.replace(baseUri, ""))
                        .typeMedia(mediaRequest.getTypeMedia())
                        .type(mediaRequest.getType())
                        .fileName("" + path.getFileName())
                        .idRefer(mediaRequest.getIdRefer())
                        .build();
                mediaList.add(mediaEnt);
            }
            if(!completableFutures.isEmpty()) {
                Thread thread = new Thread(){
                    public void run(){
                        transformData(completableFutures);
                    }
                };
                thread.start();

            }

            return mediaRepository.saveAll(mediaList);
        } catch (BusinessException ex){
            throw ex;
        }catch (Exception e) {
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
            for (MultipartFile media : medias) {
                byte[] encoded;
                try {
                    encoded = Base64Utils.encode(toByteArr(media));
                } catch (IOException e) {
                    throw new BusinessException("500", "File failed to upload " + media.getOriginalFilename());
                }
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

    private byte[] toByteArr(MultipartFile multipartfile) throws IOException {
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int count = 0;

        BufferedInputStream fileInputStream = new BufferedInputStream(multipartfile.getInputStream());
        int temp;
        while ((temp = fileInputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, temp);
        }

        // Mow converting byte array output stream to byte
        // array
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return byteArray;
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = rootImage.resolve(filename);
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
        FileSystemUtils.deleteRecursively(rootImage.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootImage, 1)
                    .filter(path -> !path.equals(this.rootImage)).map(this.rootImage::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

    public void deleteById(String id)  {
        Optional<Media> media = mediaRepository.findById(id);
        if(media.isPresent()) {
                String path = baseUri + separate + media.get().getUrl();
                Path temp = Paths.get(path);
                try {
                    Files.deleteIfExists(temp);
                    if(path.contains("upload/")){
                        File directoryToDelete = new File(path.substring(0,path.lastIndexOf(".")));
                        FileSystemUtils.deleteRecursively(directoryToDelete);
                    }
                } catch (Exception exception){
                    exception.printStackTrace();
                }
            mediaRepository.deleteById(id);
        }
    }

    @Override
    public boolean deleteByUrl(String url) {
        try {
            Media media = mediaRepository.findByUrl(url);
            if (media != null)
                mediaRepository.delete(media);
            String path = baseUri + separate + media.getUrl();
            Path temp = Paths.get(path);
            Files.deleteIfExists(temp);
            if(path.contains("upload/")){
                File directoryToDelete = new File(path.substring(0,path.lastIndexOf(".")));
                FileSystemUtils.deleteRecursively(directoryToDelete);
            }

            return true;
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public List<MediaResponse> loadByRefId(String idRefer, String lang) {
        return commonRepository.findAllByIdReferOrderByIndex(idRefer, lang);
    }

    @Override
    public String uploadMedia(MultipartFile media, String type) {
        init();
        try {
            if(type.equals("audio")) {
                Path path = this.rootAudio.resolve(new Date().getTime() + "_" + media.getOriginalFilename());
                Files.copy(media.getInputStream(), path);
                return (pathUrlAudio + separate + path.getFileName()).replace(baseUri, "");
            } else if(type.equals("image")){
                Path path = this.rootImage.resolve(new Date().getTime() + "_" + media.getOriginalFilename());
                Files.copy(media.getInputStream(), path);
                return (pathUrlImage + separate + path.getFileName()).replace(baseUri, "");
            }
          else  if(type.equals("video")){
                Path path = this.rootVideo.resolve(new Date().getTime() + "_" + media.getOriginalFilename());
                Files.copy(media.getInputStream(), path);
                return (pathUrlVideo + separate + path.getFileName()).replace(baseUri, "");
            } else {
                return "";
            }

        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void updateMediaDes(String id, String des, String lang) {
        List<MediaI18n> media = mediaI18nRepository.findAllByMediaIdAndLanguageId(id,lang);
        if(!CommonUtils.isEmpty(media)){
            media.get(0).setDescription(des);
            mediaI18nRepository.save(media.get(0));
        } else {
            MediaI18n mediaI18n = new MediaI18n();
            mediaI18n.setId(UUID.randomUUID().toString());
            mediaI18n.setDescription(des);
            mediaI18n.setMediaId(id);
            mediaI18n.setLanguageId(lang);
            mediaI18nRepository.save(mediaI18n);
        }
    }

    @Override
    @Transactional
    public List<Media> saveAll(List<Media> items) {
        if(!items.isEmpty()) {
            for (int i = 0; i < items.size(); i++) {
                items.get(i).setIndex(i);
            }
            mediaRepository.saveAll(items);
        }
        return items;
    }


    public CompletableFuture<Void> transcodeToM3u8(MultipartFile video, Path path) {
            return CompletableFuture.supplyAsync(() -> {
                TranscodeConfig transcodeConfig = new TranscodeConfig();
                LOGGER.info("transcoding configuration：{}", transcodeConfig);

                // io to temp file
                assert path != null;

                // remove suffix
                String newDir = path.toFile().getName().substring(0, path.toFile().getName().lastIndexOf("."));
                Path targetFolder;
                try {
                    // try to create video directory
                     targetFolder = Files.createDirectories(Paths.get(path.getParent().toString() + separate + newDir));
                } catch (Exception ex){
                    targetFolder = path.getParent().resolve(newDir);
                }


                // Perform transcoding
                LOGGER.info("start transcoding:");
                try {
                    video.transferTo(targetFolder.resolve(video.getOriginalFilename()));
                    FFmpegUtils.transcodeToM3u8(targetFolder.resolve(video.getOriginalFilename()).toString(), targetFolder.toString(), transcodeConfig);

                }  catch (Exception e) {
                    LOGGER.error("Transcoding exception：{}", e.getMessage());
                } finally {
                    try {
                        // Always delete temporary files
                        Files.delete(targetFolder.resolve(video.getOriginalFilename()));
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
                LOGGER.error("Transcoding success video：{}", newDir);
                return null;
            });

        }

        @Async
    public void transformData(List<CompletableFuture> completableFutures){
        try {
            CompletableFuture.anyOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()])).get();
        } catch (Exception exception){
            exception.printStackTrace();
        }
        }
    @Override
    public boolean deleteByUrlNotMedia(String url) {
        try {
            if (url != null) {
                String path = baseUri + separate + url.replaceAll("..", "");
                Path temp = Paths.get(path);
                Files.deleteIfExists(temp);
                if (path.contains("upload/")) {
                    File directoryToDelete = new File(path.substring(0, path.lastIndexOf(".")));
                    FileSystemUtils.deleteRecursively(directoryToDelete);
                }
            }

            return true;
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

}
