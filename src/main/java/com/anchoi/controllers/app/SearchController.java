package com.anchoi.controllers.app;

import com.anchoi.response.SearchResponse;
import com.anchoi.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/app/search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;
    @GetMapping()
    public List<SearchResponse> searchAll(@RequestHeader(value = "lang", defaultValue = "vi") String lang) {
        return searchService.searchAllApp(lang);
    }
    @GetMapping("/by-name")
    public List<SearchResponse> searchAllByName(String name) {
        return searchService.searchAllByName(name);
    }
}
