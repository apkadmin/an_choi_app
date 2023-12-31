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
import com.anchoi.response.DistrictResponse;
import com.anchoi.response.DistrictV1Response;
import com.anchoi.service.DistrictService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
    public List<DistrictResponse> findAll() throws BusinessException {
        return districtRepository.findAllWithProvinceName();
    }

    @Override
    public List<DistrictV1Response> findAllV1() {
        return districtRepository.searchAllV1();
    }

    @Override
    public List<DistrictV1Response> findAllByProvinceId(String provinceId) {
        return districtRepository.findAllByProvinceId(provinceId);
    }

    @Override
    public DistrictResponse update(DistrictRequest request) throws Exception {
        if (request == null || StringUtils.isBlank(request.getId()))
            throw new BusinessException("006", "Id must be not null");
        DistrictResponse response = null;
        Optional<District> entOpt = Optional.ofNullable(districtRepository.findById(request.getId())
                .orElseThrow(() -> new BusinessException("005","Not found record")));
        districtI18nRepository.deleteAllByDistrictId(request.getId());
        if(CommonUtils.isEmpty(request.getDistrictI18ns())) {
            List<DistrictI18n> i18nList = request.getDistrictI18ns().stream().map(i18nRequest -> {
                DistrictI18n districtI18n =  CommonUtils.toObject(i18nRequest, DistrictI18n.class);
                districtI18n.setId(UUID.randomUUID().toString());
                districtI18n.setDistrictId(request.getId());
             return districtI18n ;
            }).collect(Collectors.toList());
        }
        if (entOpt.isPresent()) {
            District toUpdate = CommonUtils.toObject(request, District.class);
            District updatedEnt = districtRepository.save(toUpdate);
            response = CommonUtils.toObject(updatedEnt, DistrictResponse.class);
        }

        return response;
    }
}
