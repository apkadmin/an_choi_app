package com.anchoi.service.impl;

import com.anchoi.common.CommonUtils;
import com.anchoi.config.BusinessException;
import com.anchoi.entity.Province;
import com.anchoi.entity.ProvinceI18n;
import com.anchoi.repository.province.ProvinceI18nRepository;
import com.anchoi.repository.province.ProvinceRepository;
import com.anchoi.request.I18nRequest;
import com.anchoi.request.ProvinceRequest;
import com.anchoi.response.ProvinceResponse;
import com.anchoi.response.ProvinceV1Response;
import com.anchoi.service.ProvinceService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceRepository provinceRepository;
    private final ProvinceI18nRepository provinceI18nRepository;
    //For WEB
    @Override
    @Transactional()
    public ProvinceResponse save(ProvinceRequest request) throws BusinessException {
        String name = "";

        if(!CommonUtils.isEmpty(request.getId())){
            request.setId(UUID.randomUUID().toString());
        }

        List<ProvinceI18n> i18nList = new ArrayList<>();

        if(!CommonUtils.isEmpty(request.getProvinceI18ns())) {
            for (I18nRequest res : request.getProvinceI18ns()){
                if(!CommonUtils.isEmpty(res.getName())){
                    name = res.getName();
                }
                if(CommonUtils.isEmpty(res.getId())){
                    res.setId(UUID.randomUUID().toString());
                }
                ProvinceI18n temp = CommonUtils.toObject(res, ProvinceI18n.class);
                temp.setProvinceId(request.getId());
                i18nList.add(temp);
            }
        }
        List<ProvinceI18n> provinces = provinceI18nRepository.findByName(name.toLowerCase());
        if (!provinces.isEmpty())
            throw new BusinessException("001","Province has exist");
        Province toSave = CommonUtils.toObject(request, Province.class);
        Province ent = provinceRepository.save(toSave);
        provinceI18nRepository.deleteAllByProvinceId(toSave.getId());
        provinceI18nRepository.saveAll(i18nList);
        return CommonUtils.toObject(request, ProvinceResponse.class);
    }


    @Override
    public void delete(String id) throws Exception {
        Optional<Province> entOpt = Optional.ofNullable(provinceRepository.findById(id)
                .orElseThrow(() -> new BusinessException("005", "Not found record")));

        entOpt.ifPresent(provinceRepository::delete);
        provinceI18nRepository.deleteAllByProvinceId(id);
    }

    @Override
    public ProvinceResponse findById(String id) throws Exception {
        Optional<Province> entOpt = provinceRepository.findById(id);
        if(!entOpt.isEmpty()){
           return CommonUtils.toObject(entOpt, ProvinceResponse.class);
        }
        return null;
    }

    @Override
    public ProvinceResponse update(ProvinceRequest request, String id) throws Exception {
        String name = "";

        if(CommonUtils.isEmpty(id)){
            throw new BusinessException("001","Id is Required");
        }

        List<ProvinceI18n> i18nList = new ArrayList<>();

        if(!CommonUtils.isEmpty(request.getProvinceI18ns())) {
            for (I18nRequest res : request.getProvinceI18ns()){
                if(!CommonUtils.isEmpty(res.getName())){
                    name = res.getName();
                }
                if(CommonUtils.isEmpty(res.getId())){
                    res.setId(UUID.randomUUID().toString());
                }
                ProvinceI18n temp = CommonUtils.toObject(res, ProvinceI18n.class);
                temp.setProvinceId(id);
                i18nList.add(temp);
            }
        }
        List<ProvinceI18n> provinces = provinceI18nRepository.findByName(name.toLowerCase());
        if (!provinces.isEmpty())
            throw new BusinessException("001","Province has exist");
        Province toSave = CommonUtils.toObject(request, Province.class);
        toSave.setId(id);
        Province ent = provinceRepository.save(toSave);
        provinceI18nRepository.deleteAllByProvinceId(id);
        provinceI18nRepository.saveAll(i18nList);
        return CommonUtils.toObject(request, ProvinceResponse.class);
    }


    //for APP
    @Override
  public List<ProvinceV1Response> findAllV1(String lang){
       return provinceRepository.searchAllOnlyNameAndIdApp(lang);
    }
}
