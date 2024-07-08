package com.anchoi.service;

import com.anchoi.request.ProvinceRequest;
import com.anchoi.response.AreaResponse;
import com.anchoi.response.ProvinceResponse;
import com.anchoi.response.ProvinceI18nResponse;

import java.util.List;

public interface ProvinceService {

  ProvinceResponse save(ProvinceRequest request) throws Exception;
  List<ProvinceI18nResponse> findAll(String lang);
  void delete(String id) throws Exception;

  ProvinceResponse findById(String id) throws Exception;

  ProvinceResponse update(ProvinceRequest request, String id) throws Exception;

  AreaResponse findByIdAndLang(String id, String lang);


}
