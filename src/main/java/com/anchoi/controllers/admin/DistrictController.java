package com.anchoi.controllers.admin;

import com.anchoi.config.BusinessException;
import com.anchoi.request.DistrictRequest;
import com.anchoi.response.DistrictResponse;
import com.anchoi.response.DistrictI18nResponse;
import com.anchoi.response.ResponseData;
import com.anchoi.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/district")
public class DistrictController {
  @Autowired
  DistrictService districtService;
  @GetMapping("")
  public ResponseEntity<?> findAll(@RequestHeader(value = "lang", defaultValue = "vi") String lang) throws BusinessException {
    List<DistrictI18nResponse> response = districtService.findAll(lang);
    return ResponseEntity.ok(response);
  }
  @GetMapping("{id}")
  public ResponseEntity<?> findById(@PathVariable() String id) throws Exception {
    try {
      DistrictResponse response = districtService.findById(id);

      return ResponseEntity.ok(response);
    } catch (Exception businessException) {
      return ResponseEntity.ok(businessException);
    }
  }

  @PostMapping("/save")
  public ResponseEntity<?> save(@Valid @RequestBody DistrictRequest request) throws Exception {
    try {
      DistrictResponse response = districtService.save(request);
      return ResponseEntity.ok(response);
    } catch (BusinessException businessException) {
      return ResponseEntity.ok(new BusinessException(businessException.getCode(), businessException.getDesc()));
    }

  }

  @PutMapping("/update")
  public ResponseEntity<?> update(@Valid @RequestBody DistrictRequest request, @RequestParam() String id) throws Exception {
    try {
      DistrictResponse response = districtService.update(request, id);
      return ResponseEntity.ok(response);
    } catch (BusinessException businessException) {
      return ResponseEntity.ok(new BusinessException(businessException.getCode(), businessException.getDesc()));
    }
  }

  @DeleteMapping("{id}")
  public ResponseEntity delete(@PathVariable String id) throws Exception {
    try {
      districtService.delete(id);

      return ResponseEntity.ok(ResponseData.ok("OK"));
    } catch (BusinessException businessException) {
      return ResponseEntity.ok(new BusinessException(businessException.getCode(), businessException.getDesc()));
    }

  }




  @GetMapping("/find-by-province")
  public ResponseEntity<?> findAllByProvinceId(@RequestParam("id") String id, @RequestHeader(value = "lang", defaultValue = "vi") String lang) throws BusinessException {
    List<DistrictI18nResponse> response = districtService.findAllByProvinceId(id, lang);
    return ResponseEntity.ok(response);
  }

//  @GetMapping("/v1.1/findAll")
//  public ResponseEntity<?> findAllV1()  {
//    List<DistrictV1Response> response = districtService.findAllV1();
//    return ResponseEntity.ok(response);
//  }
}
