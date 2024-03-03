package com.anchoi.service;

import com.anchoi.repository.district.DistrictRepository;
import com.anchoi.repository.item.ItemRepository;
import com.anchoi.repository.province.ProvinceRepository;
import com.anchoi.response.SearchResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final ItemRepository itemRepository;

    public SearchService(ProvinceRepository provinceRepository,
                         DistrictRepository districtRepository,
                         ItemRepository itemRepository) {
        this.provinceRepository = provinceRepository;
        this.districtRepository =districtRepository;
        this. itemRepository = itemRepository;
    }
    public List<SearchResponse> searchAllApp(String lang) {
        List<SearchResponse> result = new ArrayList<>();
        result.addAll(provinceRepository.searchAllByLang(lang));
        result.addAll(districtRepository.searchAllByLang(lang));
        result.addAll(itemRepository.searchAllByLang(lang));

        return result;
    }

    public List<SearchResponse> searchAllByName(String name, String lang) {
        List<SearchResponse> result = new ArrayList<>();
        List<SearchResponse> provinces = provinceRepository.searchAllByNameApp(name);
        List<SearchResponse> districts = districtRepository.searchAllByName(name);
        List<SearchResponse> items = itemRepository.searchAllByName(name);

        result.addAll(provinces);
        result.addAll(districts);
        result.addAll(items);

        return result;
    }
}
