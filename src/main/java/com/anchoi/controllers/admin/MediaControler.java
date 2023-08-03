package com.anchoi.controllers.admin;

import com.anchoi.models.MediaDesRequest;
import com.anchoi.response.ResponseData;
import com.anchoi.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
}
