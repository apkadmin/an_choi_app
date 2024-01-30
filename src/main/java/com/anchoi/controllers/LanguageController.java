package com.anchoi.controllers;

import com.anchoi.entity.Language;
import com.anchoi.response.ResponseData;
import com.anchoi.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/language")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class LanguageController {
    private final LanguageService languageService;
    @GetMapping(value = "")
    public ResponseEntity<?> getAllLang(){
        return ResponseEntity.ok(ResponseData.ok(languageService.getAll()));
    }
}
