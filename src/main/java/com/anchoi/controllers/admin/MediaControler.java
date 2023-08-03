package com.anchoi.controllers.admin;

import com.anchoi.models.MediaDesRequest;
import com.anchoi.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/media")
public class MediaControler {
    @Autowired
    MediaService mediaService;
    @PostMapping("/add-des")
    public ResponseEntity<String> updateDescription(@RequestBody() MediaDesRequest request){
        mediaService.updateMediaDes(request.getId(),request.getDescription());
        return ResponseEntity.ok("OK");
    }
}
