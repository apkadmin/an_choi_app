package com.anchoi.controllers.admin;

import java.util.List;

import com.anchoi.config.BusinessException;
import com.anchoi.models.Media;
import com.anchoi.request.MediaRequest;
import com.anchoi.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

//    @GetMapping("/files")
//    public ResponseEntity<List<Media>> getListFiles() {
//        List<Media> fileInfos = mediaService.loadAll().map(path -> {
//            String filename = path.getFileName().toString();
//            String url = MvcUriComponentsBuilder
//                    .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();
//
//            return new Media(filename, url);
//        }).collect(Collectors.toList());
//
//        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
//    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = mediaService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
