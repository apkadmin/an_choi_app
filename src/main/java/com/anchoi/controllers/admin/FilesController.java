package com.anchoi.controllers.admin;

import java.util.List;

import com.anchoi.config.BusinessException;
import com.anchoi.models.Media;
import com.anchoi.request.MediaRequest;
import com.anchoi.response.MessageResponse;
import com.anchoi.response.ResponseData;
import com.anchoi.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/file")
public class FilesController {

    @Autowired
    MediaService mediaService;

    @PostMapping(value="/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<Media>> uploadFile(
            @RequestParam("medias") MultipartFile[] medias,
            MediaRequest mediaRequest
    ) throws BusinessException {
        String message = "";
        try {
            List<Media> result = mediaService.save(mediaRequest, medias);

            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            throw new BusinessException("500", e.getMessage());
        }
    }

    @PostMapping(value="/upload-media", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAudio(
            @RequestParam("audio") MultipartFile media,
            @RequestParam("type") String type
    ) throws BusinessException {
        String message = "";
        try {
            String url = mediaService.uploadMedia(media,type);

            return ResponseEntity.status(HttpStatus.OK).body(url);
        } catch (Exception e) {
            throw new BusinessException("500", e.getMessage());
        }
    }



    @PostMapping(value="/v2.0/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<Media>> uploadFileV2(
            @RequestParam("medias") MultipartFile[] medias,
            MediaRequest mediaRequest
    ) throws BusinessException {
        String message = "";
        try {
            List<Media> result = mediaService.saveV2(mediaRequest, medias);

            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            throw new BusinessException("500", e.getMessage());
        }
    }

    @DeleteMapping(value="/delete")
    public ResponseEntity deleteFile( @RequestParam("fileId") String fileId) throws BusinessException {
        String message = "";
        try {
            mediaService.deleteById(fileId);
            return ResponseEntity.ok(ResponseData.ok("OK"));
        } catch (Exception e) {
            throw new BusinessException("500", e.getMessage());
        }
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = mediaService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/files-by-id-reference")
    @ResponseBody
    public ResponseEntity<?> getFileByReference(@RequestParam("id") @NotBlank String id) {
        List<Media> media = mediaService.loadById(id);
        return ResponseEntity.ok(media);
    }

    @DeleteMapping()
    public ResponseEntity<MessageResponse> deleteFile(@RequestBody Media media) {
        String message = "";
        String url = media.getUrl();

        try {
            boolean existed = mediaService.deleteByUrl(url);

            if (existed) {
                message = "Delete the file successfully: " + url;
                return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
            }

            message = "The file does not exist!";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(message));
        } catch (Exception e) {
            message = "Could not delete the file: " + url + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse(message));
        }
    }
}
