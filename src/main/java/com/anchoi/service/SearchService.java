package com.anchoi.service;

import com.anchoi.common.CommonUtils;
import com.anchoi.entity.Province;
import com.anchoi.repository.district.DistrictRepository;
import com.anchoi.repository.item.ItemRepository;
import com.anchoi.repository.province.ProvinceRepository;
import com.anchoi.response.SearchResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        List<SearchResponse> districts = districtRepository.searchAllByLang("vi");
        List<String> ids = districts.stream().filter(item -> CommonUtils.removeVietnameseTones(item.getName().toLowerCase().replace(" ","")).contains(CommonUtils.removeVietnameseTones(name.toLowerCase().replace(" ","")))).map(SearchResponse::getId).collect(Collectors.toList());

        if(!ids.isEmpty()) {
            result.addAll(districtRepository.searchByListIdAndLang(ids, lang));
        }

        return result;
    }
}
