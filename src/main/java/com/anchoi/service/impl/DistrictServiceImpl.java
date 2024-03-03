package com.anchoi.service.impl;

import com.anchoi.common.CommonUtils;
import com.anchoi.config.BusinessException;
import com.anchoi.entity.District;
import com.anchoi.entity.DistrictI18n;
import com.anchoi.entity.Province;
import com.anchoi.repository.district.DistrictI18nRepository;
import com.anchoi.repository.district.DistrictRepository;
import com.anchoi.repository.province.ProvinceRepository;
import com.anchoi.request.DistrictRequest;
import com.anchoi.response.AreaResponse;
import com.anchoi.response.DistrictResponse;
import com.anchoi.response.DistrictI18nResponse;
import com.anchoi.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DistrictServiceImpl implements DistrictService {

    private final DistrictRepository districtRepository;
    private final ProvinceRepository provinceRepository;
    private final DistrictI18nRepository districtI18nRepository;


    @Override
    public DistrictResponse save(DistrictRequest request) throws BusinessException {
        Optional<Province> provinces = provinceRepository.findById(request.getProvinceId());
        if (!provinces.isPresent())
            throw new BusinessException("002", "Province not found");


        District toSave = CommonUtils.toObject(request, District.class);
        toSave.setId(UUID.randomUUID().toString());
        if(!CommonUtils.isEmpty(toSave.getDistrictI18ns()))
            toSave.getDistrictI18ns().forEach(item -> item.setDistrictId(toSave.getId()));
        toSave.setUpdatedDate(new Date());
        toSave.setCreatedDate(new Date());
        District ent = districtRepository.save(toSave);
        DistrictResponse response = CommonUtils.toObject(ent, DistrictResponse.class);

        return response;
    }

    @Override
    public void delete(String id) throws Exception {
        Optional<District> entOpt = Optional.ofNullable(districtRepository.findById(id)
                .orElseThrow(() -> new BusinessException("005","Not found record")));

        entOpt.ifPresent(districtRepository::delete);
    }

    @Override
    public DistrictResponse findById(String id) throws Exception {
        District entity;
        DistrictResponse response = null;
        Optional<District> entOpt = Optional.ofNullable(districtRepository.findById(id)
                .orElseThrow(() -> new BusinessException("005","Not found record")));
        if (entOpt.isPresent()) {
            entity = entOpt.get();
            response = CommonUtils.toObject(entity, DistrictResponse.class);
        }

        return response;
    }

    @Override
    public List<DistrictI18nResponse> findAll(String lang) throws BusinessException {
        return districtRepository.findAll(lang);
    }

    @Override
    public List<DistrictI18nResponse> findAllByProvinceId(String provinceId, String lang) {
        return districtRepository.findAllByProvinceId(provinceId, lang);
    }

    @Override
    @Transactional()
    public DistrictResponse update(DistrictRequest request, String id) throws Exception {
        DistrictResponse response = null;
        Optional<District> entOpt = Optional.ofNullable(districtRepository.findById(id)
                .orElseThrow(() -> new BusinessException("005","Not found record")));

        if (entOpt.isPresent()) {
            District toUpdate = CommonUtils.toObject(request, District.class);
            toUpdate.setDistrictI18ns(new ArrayList<>());
            toUpdate.setUpdatedDate(new Date());
            District updatedEnt = districtRepository.save(toUpdate);
            districtI18nRepository.deleteAllByDistrictId(id);
            districtI18nRepository.flush();
            if(!CommonUtils.isEmpty(request.getDistrictI18ns())) {
                List<DistrictI18n> i18nList = request.getDistrictI18ns().stream().map(i18nRequest -> {
                    DistrictI18n districtI18n =  CommonUtils.toObject(i18nRequest, DistrictI18n.class);
                    districtI18n.setId(UUID.randomUUID().toString());
                    districtI18n.setDistrictId(id);
                    return districtI18n ;
                }).collect(Collectors.toList());

                districtI18nRepository.saveAll(i18nList);
            }

            response = CommonUtils.toObject(updatedEnt, DistrictResponse.class);
        }

        return response;
    }

    @Override
    public AreaResponse findByIdAndLang(String id, String lang) {
        return districtRepository.searchByIdAndLang(id, lang);
    }


}
