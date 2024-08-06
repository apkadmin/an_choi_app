package com.anchoi.service;

import com.anchoi.entity.Language;
import com.anchoi.response.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageService {
    private final LanguageRepository languageRepository;
    public  List<Language> getAll(){
        return languageRepository.findAll();
    }

    public  List<Language> getAllApp(){
        return languageRepository.findAllByActive(true);
    }
}
