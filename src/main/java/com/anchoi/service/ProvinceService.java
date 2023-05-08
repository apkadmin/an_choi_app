package com.anchoi.service;

import com.anchoi.models.Province;
import com.anchoi.request.ProvinceRequest;
import com.anchoi.response.ProvinceResponse;

import java.util.List;

public interface ProvinceService {

  ProvinceResponse save(ProvinceRequest request) throws Exception;
  void delete(String id) throws Exception;

  Province findById(String id) throws Exception;

  List<Province> findAll();

  ProvinceResponse update(ProvinceRequest request) throws Exception;
}
