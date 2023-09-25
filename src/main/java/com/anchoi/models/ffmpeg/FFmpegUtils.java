package com.anchoi.models.ffmpeg;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.KeyGenerator;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import org.springframework.web.multipart.MultipartFile;


public class FFmpegUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(FFmpegUtils.class);


	// 跨平台换行符
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private static final List<String> RESOLUTIONS = Arrays.asList("360", "480", "720", "1080");
	private static final List<String> DIMENSIONS = Arrays.asList("480x360", "854x480", "1280x720", "1920x1080");
	private static final List<String> SCALE = Arrays.asList("scale=-2:360,setsar=1", "scale=-2:480,setsar=1", "scale=-2:720,setsar=1", "scale=-2:1080,setsar=1");
	private static final List<String> BANDWIDTHS = Arrays.asList("1000000", "2000000", "3000000", "4000000");

	/**
	 * 生成随机16个字节的AESKEY
	 * @return
	 */
	private static byte[] genAesKey ()  {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128);
			return keyGenerator.generateKey().getEncoded();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	/**
	 * 在指定的目录下生成key_info, key文件，返回key_info文件
	 * @param folder
	 * @throws IOException
	 */
	private static Path genKeyInfo(String folder) throws IOException {
		// AES 密钥
		byte[] aesKey = genAesKey();
		// AES 向量
		String iv = Hex.encodeHexString(genAesKey());

		// key 文件写入
		Path keyFile = Paths.get(folder, "key");
		Files.write(keyFile, aesKey, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

		// key_info 文件写入
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("key").append(LINE_SEPARATOR);                    // m3u8加载key文件网络路径
		stringBuilder.append(keyFile.toString()).append(LINE_SEPARATOR);    // FFmeg加载key_info文件路径
		stringBuilder.append(iv);                                            // ASE 向量

		Path keyInfo = Paths.get(folder, "key_info");

		Files.write(keyInfo, stringBuilder.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

		return keyInfo;
	}

	/**
	 * 指定的目录下生成 master index.m3u8 文件
//	 * @param fileName			master m3u8文件地址
	 * @param indexPath            访问子index.m3u8的路径
	 * @param bandWidth            流码率
	 * @throws IOException
	 */
	private static void genIndex(String file, String indexPath, String bandWidth) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("#EXTM3U").append(LINE_SEPARATOR);
		stringBuilder.append("#EXT-X-STREAM-INF:BANDWIDTH=" + bandWidth).append(LINE_SEPARATOR);  // 码率
		stringBuilder.append(indexPath);
		Files.write(Paths.get(file), stringBuilder.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
	}

	private static void appendToFile(StringBuilder indexStringBuilder, String destFolder, int resolutionIndex) {
		indexStringBuilder.append("#EXT-X-STREAM-INF:")
				.append("BANDWIDTH=").append(BANDWIDTHS.get(resolutionIndex))
				.append(",RESOLUTION=").append(DIMENSIONS.get(resolutionIndex))
				.append(LINE_SEPARATOR)
				.append(" ts_").append(RESOLUTIONS.get(resolutionIndex)).append("/index.m3u8")
				.append(LINE_SEPARATOR);
//	 https://chunks01.tvpublica.com.ar/video/BJQRNMF6b_240/index.m3u8 ")
	}

	/**
	 * Transcode video to m3u8
	 * @param source                source video
	 * @param destFolder            target folder
	 * @param config                configuration information
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void transcodeToM3u8(String source, String destFolder, TranscodeConfig config) throws IOException, InterruptedException {
		// FFmpegUtils.transcodeToM3u8(tempFile.toString(), targetFolder.toString(), transcodeConfig);

		// Check if source video exists
		if (!Files.exists(Paths.get(source))) {
			throw new IllegalArgumentException("file does not exist：" + source);
		}

		// get video information
		MediaInfo mediaInfo = getMediaInfo(source);
		if (mediaInfo == null) {
			throw new RuntimeException("Abnormal access to media information");
		}

		// get list resolution by bitrate
		double bitrateVideo = Double.parseDouble(mediaInfo.getFormat().getBitRate())/1000000;
		LOGGER.info("bit rate: {}", bitrateVideo);


		int heightVideo = 360;
		for (MediaInfo.Stream stream : mediaInfo.getStreams()) {
			if(stream.getHeight() != null){
				heightVideo = Integer.parseInt(stream.getHeight());
			}
		}

		int numVideo = 0;
		if (360 <= heightVideo && heightVideo < 480) {
			numVideo = 1;
		} else if (480 <= heightVideo && heightVideo < 720) {
			numVideo = 2;
		} else if (720 <= heightVideo && heightVideo < 1080) {
			numVideo = 3;
		} else if (1080 <= heightVideo) {
			numVideo = 4;
		}

		StringBuilder indexStringBuilder = genFirstInIndex();
		// convert to list of resolution
		for (int i=0; i < numVideo; i++) {
			convertToM3u8(source, destFolder, config, mediaInfo, i);
			appendToFile(indexStringBuilder, destFolder, i);
		}

		Files.write(Paths.get(String.join(File.separator, destFolder, "index.m3u8")), indexStringBuilder.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
	}


	private static StringBuilder genFirstInIndex() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("#EXTM3U").append(LINE_SEPARATOR);
		return stringBuilder;
	}

	private static void convertToM3u8(String source, String destFolder, TranscodeConfig config, MediaInfo mediaInfo, int videoIndex) throws IOException, InterruptedException {
		String resolution = RESOLUTIONS.get(videoIndex);
//		String dimension = DIMENSIONS.get(videoIndex);
		String scale = SCALE.get(videoIndex);
		String bandwidth = BANDWIDTHS.get(videoIndex);
		// create working directory
		Path workDir = Paths.get(destFolder, "ts_" + resolution);
		Files.createDirectories(workDir);
		// Generate a KeyInfo file in the working directory
		Path keyInfo = genKeyInfo(workDir.toString());
		// build command
		List<String> commands = new ArrayList<>();
		commands.add("ffmpeg");
		commands.add("-i");commands.add(source);                    // Source File
		commands.add("-c:v");commands.add("libx264");                // The video is encoded as H264
		commands.add("-c:a");commands.add("copy");                    // 音频直接copy
		commands.add("-hls_key_info_file");commands.add(keyInfo.toString());        // 指定密钥文件路径
		commands.add("-hls_time");commands.add(config.getTsSeconds());    // ts切片大小
		commands.add("-hls_playlist_type");commands.add("vod");                    // 点播模式
		commands.add("-hls_segment_filename");commands.add("%06d.ts");                // ts切片文件名称
//		commands.add("-s")						;commands.add(dimension);				// dimension
		commands.add("-vf");commands.add(scale);                // scale
		if (StringUtils.hasText(config.getCutStart())) {
			commands.add("-ss");commands.add(config.getCutStart());    // start time
		}
		if (StringUtils.hasText(config.getCutEnd())) {
			commands.add("-to");commands.add(config.getCutEnd());        // end time
		}
		commands.add("index.m3u8");                                                        // Generate m3u8 file

		// build process
		Process process = new ProcessBuilder()
				.command(commands)
				.directory(workDir.toFile())
				.start();

		// Read process standard output
		new Thread(() -> {
			try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					LOGGER.info(line);
				}
			} catch (IOException e) {
			}
		}).start();

		// Read process exception output
		new Thread(() -> {
			try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					LOGGER.info(line);
				}
			} catch (IOException e) {
			}
		}).start();


		// block until the task ends
		if (process.waitFor() != 0) {
			throw new RuntimeException("Video slice exception");
		}

		// cut out cover
		if (!screenShots(source, String.join(File.separator, destFolder, "poster.jpg"), config.getPoster())) {
			throw new RuntimeException("Cover interception exception");
		}

//		// Generate index.m3u8 file
//		genIndex(String.join(File.separator, destFolder, "index.m3u8"), "ts_" + resolution +"/index.m3u8", mediaInfo.getFormat().getBitRate());

		// Delete keyInfo file
//		Files.delete(keyInfo);
	}

	/**
	 * 获取视频文件的媒体信息
	 * @param source
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static MediaInfo getMediaInfo(String source) throws IOException, InterruptedException {
		List<String> commands = new ArrayList<>();
		commands.add("ffprobe");
		commands.add("-i");commands.add(source);
		commands.add("-show_format");
		commands.add("-show_streams");
		commands.add("-print_format");commands.add("json");

		Process process = new ProcessBuilder(commands)
				.start();

		MediaInfo mediaInfo = null;

		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			mediaInfo = new Gson().fromJson(bufferedReader, MediaInfo.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (process.waitFor() != 0) {
			return null;
		}

		return mediaInfo;
	}

	/**
	 * 截取视频的指定时间帧，生成图片文件
	 * @param source        源文件
	 * @param file            图片文件
	 * @param time            截图时间 HH:mm:ss.[SSS]
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static boolean screenShots(String source, String file, String time) throws IOException, InterruptedException {

		List<String> commands = new ArrayList<>();
		commands.add("ffmpeg");
		commands.add("-i");commands.add(source);
		commands.add("-ss");commands.add(time);
		commands.add("-y");
		commands.add("-q:v");commands.add("1");
		commands.add("-frames:v");commands.add("1");
		commands.add("-f");
		;commands.add("image2");
		commands.add(file);

		Process process = new ProcessBuilder(commands)
				.start();

		// 读取进程标准输出
		new Thread(() -> {
			try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					LOGGER.info(line);
				}
			} catch (IOException e) {
			}
		}).start();

		// 读取进程异常输出
		new Thread(() -> {
			try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					LOGGER.error(line);
				}
			} catch (IOException e) {
			}
		}).start();

		return process.waitFor() == 0;
	}
}

