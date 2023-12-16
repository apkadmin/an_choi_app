package com.anchoi.service;

import com.anchoi.config.BusinessException;
import com.anchoi.request.DistrictRequest;
import com.anchoi.response.DistrictResponse;
import com.anchoi.response.DistrictV1Response;

import java.util.List;

public interface DistrictService {

  DistrictResponse save(DistrictRequest request) throws Exception;
  void delete(String id) throws Exception;

  DistrictResponse findById(String id) throws Exception;

  List<DistrictResponse> findAll() throws BusinessException;
  List<DistrictV1Response> findAllV1();
  List<DistrictV1Response> findAllByProvinceId(String provinceId);

  DistrictResponse update(DistrictRequest request) throws Exception;
}
