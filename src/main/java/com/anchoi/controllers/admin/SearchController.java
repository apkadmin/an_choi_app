package com.anchoi.controllers.admin;

import com.anchoi.response.SearchResponse;
import com.anchoi.service.SearchService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/admin/search")
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping()
    public List<SearchResponse> searchAll() {
        return searchService.searchAll();
    }

    @GetMapping("/by-name")
    public List<SearchResponse> searchAllByName(String name) {
        return searchService.searchAllByName(name);
    }
}
