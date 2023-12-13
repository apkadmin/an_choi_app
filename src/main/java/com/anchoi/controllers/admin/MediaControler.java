package com.anchoi.controllers.admin;

import com.anchoi.models.Media;
import com.anchoi.request.MediaDesRequest;
import com.anchoi.response.ResponseData;
import com.anchoi.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/media")
public class MediaControler {
    @Autowired
    MediaService mediaService;
    @PostMapping("/add-des")
    public ResponseEntity<?> updateDescription(@RequestBody() MediaDesRequest request){
        mediaService.updateMediaDes(request.getId(),request.getDescription());
        return ResponseEntity.ok(ResponseData.ok("OK"));
    }

    @PostMapping(value = "/save-all")
    public ResponseEntity save(@RequestBody List<Media> item){
        try {
            return ResponseEntity.ok(mediaService.saveAll(item));
        } catch (Exception e){
            return ResponseEntity.status(502).body(ResponseData.error(null,e.getMessage()));
        }
    }
}
