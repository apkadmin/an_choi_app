package com.anchoi.controllers.admin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.anchoi.models.ffmpeg.FFmpegUtils;
import com.anchoi.models.ffmpeg.TranscodeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class UploadVideoController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UploadVideoController.class);

	@Value("${app.video-folder}")
	private String videoFolder;

	private Path tempDir = Paths.get(System.getProperty("java.io.tmpdir"));

	/**
	 * Upload the video for slice processing and return the access path
	 * @param video
	//	 * @param transcodeConfig
	 * @return
	 * @throws IOException
	 */
	@PostMapping
	public Object upload (@RequestPart(name = "file", required = true) MultipartFile video
//			,@RequestPart(name = "title", required = true) String title
	) throws IOException {
		TranscodeConfig transcodeConfig = new TranscodeConfig();

		LOGGER.info("file information：title={}, size={}", video.getOriginalFilename(), video.getSize());
		LOGGER.info("transcoding configuration：{}", transcodeConfig);

		// The original file name, which is the title of the video
		String title = video.getOriginalFilename();

		// io to temp file
		Path tempFile = tempDir.resolve(title);
		LOGGER.info("io to temp file：{}", tempFile.toString());

		try {

			video.transferTo(tempFile);

			// remove suffix
			title = title.substring(0, title.lastIndexOf("."));

			// Generate subdirectories by date
			String today = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now());

			// try to create video directory
			Path targetFolder = Files.createDirectories(Paths.get(videoFolder, title));

			LOGGER.info("Create folder directory：{}", targetFolder);
			Files.createDirectories(targetFolder);

			// TODO check resolution of video

			// Perform transcoding
			LOGGER.info("start transcoding:");
			try {
				FFmpegUtils.transcodeToM3u8(tempFile.toString(), targetFolder.toString(), transcodeConfig);
			} catch (Exception e) {
				LOGGER.error("Transcoding exception：{}", e.getMessage());
				Map<String, Object> result = new HashMap<>();
				result.put("success", false);
				result.put("message", e.getMessage());
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
			}

			// Package result
			Map<String, Object> videoInfo = new HashMap<>();
			videoInfo.put("title", title);
			videoInfo.put("m3u8", String.join("/", "", title, "index.m3u8"));
			videoInfo.put("poster", String.join("/", "", title, "poster.jpg"));

			Map<String, Object> result = new HashMap<>();
			result.put("success", true);
			result.put("data", videoInfo);
			return result;
		} finally {
			// Always delete temporary files
			Files.delete(tempFile);
		}
	}
}
