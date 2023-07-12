package com.anchoi.service;

import com.anchoi.models.ffmpeg.FFmpegUtils;
import com.anchoi.models.ffmpeg.TranscodeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Service
public class UploadVideoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadVideoService.class);
    private final Path tempDir = Paths.get(System.getProperty("java.io.tmpdir"));

    @Value("${app.video-folder}")
    private String videoFolder;

    public String saveOriginVideo(MultipartFile video) throws IOException {
        LOGGER.info("file information：title={}, size={}", video.getOriginalFilename(), video.getSize());

        // The original file name, which is the title of the video
        String title = video.getOriginalFilename();
        String titleOrigin = video.getOriginalFilename();

        // io to temp file
        assert title != null;
        Path tempFile = tempDir.resolve(title);
        LOGGER.info("io to temp file：{}", tempFile.toString());

        try {
            video.transferTo(tempFile);

            // remove suffix
            title = title.substring(0, title.lastIndexOf("."));

            // try to create video directory
            Path targetFolder = Files.createDirectories(Paths.get(videoFolder, title));

            LOGGER.info("Create folder directory：{}", targetFolder);
            Files.createDirectories(targetFolder);
            Path path = Files.write(Paths.get(String.join(File.separator, targetFolder.toString(), titleOrigin)),
                    video.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return path.toString();
        } finally {
            // Always delete temporary files
            Files.delete(tempFile);
        }
    }

    @Async
    public void transcodeToM3u8s(MultipartFile[] videos) throws IOException {
        TranscodeConfig transcodeConfig = new TranscodeConfig();
        LOGGER.info("transcoding configuration：{}", transcodeConfig);
        for (MultipartFile video : videos) {
            // The original file name, which is the title of the video
            String title = video.getOriginalFilename();
            // io to temp file
            assert title != null;
            Path tempFile = tempDir.resolve(title);
            LOGGER.info("io to temp file：{}", tempFile.toString());
            video.transferTo(tempFile);

            // remove suffix
            title = title.substring(0, title.lastIndexOf("."));

            try {

                // try to create video directory
                Path targetFolder = Files.createDirectories(Paths.get(videoFolder, title));

                LOGGER.info("Create folder directory：{}", targetFolder);
                Files.createDirectories(targetFolder);

                // Perform transcoding
                LOGGER.info("start transcoding:");
                try {
                    FFmpegUtils.transcodeToM3u8(tempFile.toString(), targetFolder.toString(), transcodeConfig);
                } catch (Exception e) {
                    LOGGER.error("Transcoding exception：{}", e.getMessage());
                }
                LOGGER.error("Transcoding success video：{}", title);
            } finally {
                // Always delete temporary files
                Files.delete(tempFile);
            }
        }
    }
}
