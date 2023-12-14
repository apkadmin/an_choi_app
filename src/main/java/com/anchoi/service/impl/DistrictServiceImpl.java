package com.anchoi.service.impl;

import com.anchoi.config.BusinessException;
import com.anchoi.models.District;
import com.anchoi.models.Province;
import com.anchoi.repository.DistrictRepository;
import com.anchoi.repository.ProvinceRepository;
import com.anchoi.request.DistrictRequest;
import com.anchoi.response.DistrictResponse;
import com.anchoi.response.DistrictV1Response;
import com.anchoi.service.DistrictService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DistrictServiceImpl implements DistrictService {

    final DistrictRepository districtRepository;
    final ProvinceRepository provinceRepository;

    private final ObjectMapper mapper = new ObjectMapper().configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true).registerModule(new JavaTimeModule());

    public DistrictServiceImpl(DistrictRepository districtRepository, ProvinceRepository provinceRepository) {
        this.districtRepository = districtRepository;
        this.provinceRepository = provinceRepository;
    }

    @Override
    public DistrictResponse save(DistrictRequest request) throws BusinessException {
        Optional<Province> provinces = provinceRepository.findById(request.getProvinceId());
        if (!provinces.isPresent())
            throw new BusinessException("002", "Province not found");

        List<District> districts = districtRepository.findByNameAndProvinceId(request.getName().toLowerCase(), request.getProvinceId());
        if (!districts.isEmpty())
            throw new BusinessException("003","District has exist");

        District toSave = mapper.convertValue(request, District.class);
        toSave.setId("");
        District ent = districtRepository.save(toSave);
        DistrictResponse response = mapper.convertValue(ent, DistrictResponse.class);

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
            response = mapper.convertValue(entity, DistrictResponse.class);
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
        if (entOpt.isPresent()) {
            // k cho phep sua ma tinh (provinceId)
//            if (!request.getProvinceId().equals(entOpt.get().getProvinceId()))
//                throw new BusinessException("004", "Province not allowed update");
            District toUpdate = mapper.convertValue(request, District.class);

            District updatedEnt = districtRepository.save(toUpdate);
            response = mapper.convertValue(updatedEnt, DistrictResponse.class);
        }

        return response;
    }
}
