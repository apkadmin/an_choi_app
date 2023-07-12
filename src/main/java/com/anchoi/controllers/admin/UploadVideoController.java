package com.anchoi.controllers.admin;

import com.anchoi.service.UploadVideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/upload")
public class UploadVideoController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UploadVideoController.class);

	@Autowired
	private UploadVideoService uploadVideoService;

	/**
	 * Upload the video for slice processing and return the access path
	 * @param
	//	 * @param transcodeConfig
	 * @return
	 * @throws IOException
	 */
	@PostMapping
	public Object upload (@RequestPart(name = "files", required = true) MultipartFile[] videos
//			,@RequestPart(name = "title", required = true) String title
	) throws IOException {
		// save origin videos
		List<String> paths = new ArrayList<>();
		for (MultipartFile video : videos) {
			String path = uploadVideoService.saveOriginVideo(video);
			paths.add(path);
		}
		// convert to m3u8
		uploadVideoService.transcodeToM3u8s(videos);

		return paths;

	}
}
