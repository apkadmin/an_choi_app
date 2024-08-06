package com.anchoi.service;

import com.anchoi.config.BusinessException;
import com.anchoi.request.DistrictRequest;
import com.anchoi.response.AreaResponse;
import com.anchoi.response.DistrictResponse;
import com.anchoi.response.DistrictI18nResponse;

import java.util.List;

public interface DistrictService {

  DistrictResponse save(DistrictRequest request) throws Exception;
  void delete(String id) throws Exception;

  DistrictResponse findById(String id) throws Exception;

  List<DistrictI18nResponse> findAll(String lang) throws BusinessException;
  List<DistrictI18nResponse> findAllByProvinceId(String provinceId, String lang);

  DistrictResponse update(DistrictRequest request, String id) throws Exception;

  AreaResponse findByIdAndLang(String id, String lang);
}
