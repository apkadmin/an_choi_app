package com.anchoi.controllers;

import com.anchoi.entity.Post;
import com.anchoi.service.SyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<String> syncPostId(@PathVariable() String id) {
        syncService.syncServicePostId(id);
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
