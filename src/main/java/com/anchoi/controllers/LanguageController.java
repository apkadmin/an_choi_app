package com.anchoi.controllers;

import com.anchoi.entity.Language;
import com.anchoi.response.ResponseData;
import com.anchoi.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController(value = "/language")
@RequiredArgsConstructor
public class LanguageController {
    private final LanguageService languageService;
    @GetMapping()
    public ResponseData<List<Language>> getAllLang(){
        return ResponseData.ok(languageService);
    }
}
