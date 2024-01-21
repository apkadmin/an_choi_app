package com.anchoi.controllers.admin;

import com.anchoi.config.BusinessException;
import com.anchoi.request.ProvinceRequest;
import com.anchoi.response.ProvinceResponse;
import com.anchoi.response.ProvinceI18nResponse;
import com.anchoi.response.ResponseData;
import com.anchoi.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/province")
public class ProvinceController {
  @Autowired
  ProvinceService provinceService;

  @GetMapping()
  public ResponseEntity<?> findAll(@RequestHeader(value = "lang", defaultValue = "vi") String lang) {
    List<ProvinceI18nResponse> response = provinceService.findAll(lang);
    return ResponseEntity.ok(response);
  }

  @GetMapping("{id}")
  public ResponseEntity<?> findById(@PathVariable  String id) throws Exception {
    try {
      ProvinceResponse response = provinceService.findById(id);
      return ResponseEntity.ok(response);
    } catch (Exception businessException) {
      return ResponseEntity.ok(businessException.getMessage());
    }
  }

  @PostMapping("save")
  public ResponseEntity<?> save(@Valid @RequestBody ProvinceRequest request) throws Exception {
    try {
      ProvinceResponse response = provinceService.save(request);
      return ResponseEntity.ok(response);
    } catch (BusinessException businessException) {
      return ResponseEntity.ok(new BusinessException(businessException.getCode(), businessException.getDesc()));
    }

  }

  @PutMapping("/update")
  public ResponseEntity<?> update(@Valid @RequestBody ProvinceRequest request, @RequestParam() String id) throws Exception {
    try {
      ProvinceResponse response = provinceService.update(request, id);
      return ResponseEntity.ok(response);
    } catch (Exception businessException) {
      return ResponseEntity.ok(businessException);
    }
  }

  @DeleteMapping("{id}")
  public ResponseEntity deleteProvince(@PathVariable String id) throws Exception {
    try {
      provinceService.delete(id);
      return ResponseEntity.ok(ResponseData.ok("OK"));
    } catch (Exception businessException) {
      return ResponseEntity.ok(businessException);
    }
  }




}
