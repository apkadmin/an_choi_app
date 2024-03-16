package com.anchoi.controllers;

import com.anchoi.entity.Post;
import com.anchoi.service.SyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/api/sync")
@RequiredArgsConstructor
public class SyncData {
   private final SyncService syncService;

    @GetMapping
    public ResponseEntity<String> syncPost(@RequestHeader(value = "lang", defaultValue = "vi") String lang) {
        syncService.syncService(lang);
        return ResponseEntity.ok().body("OK");
    }

    @GetMapping("/replace")
    public ResponseEntity<String> replaceData(@RequestHeader(value = "lang", defaultValue = "vi") String lang) {
        syncService.replaceData(lang);
        return ResponseEntity.ok().body("OK");
    }
    @GetMapping("/removeData")
    public ResponseEntity<String> removeData(@RequestHeader(value = "lang", defaultValue = "vi") String lang) {
        syncService.removeData(lang);
        return ResponseEntity.ok().body("OK");
    }

}
