package com.anchoi.service;

import com.anchoi.entity.Province;
import com.anchoi.request.ProvinceRequest;
import com.anchoi.response.ProvinceResponse;
import com.anchoi.response.ProvinceV1Response;

import java.util.List;

public interface ProvinceService {

  ProvinceResponse save(ProvinceRequest request) throws Exception;
  List<ProvinceV1Response> findAllV1();
  void delete(String id) throws Exception;

  Province findById(String id) throws Exception;

  List<Province> findAll();

  ProvinceResponse update(ProvinceRequest request) throws Exception;
}
