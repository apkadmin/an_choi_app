package com.anchoi.controllers.admin;

import com.anchoi.config.BusinessException;
import com.anchoi.models.Province;
import com.anchoi.request.ProvinceRequest;
import com.anchoi.response.ProvinceResponse;
import com.anchoi.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/province")
public class ProvinceController {
  @Autowired
  ProvinceService provinceService;

  @PostMapping("/v1.0/save")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> saveProvince(@Valid @RequestBody ProvinceRequest request) throws Exception {
    try {
      ProvinceResponse response = provinceService.save(request);
      return ResponseEntity.ok(response);
    } catch (BusinessException businessException) {
      return ResponseEntity.ok(new BusinessException(businessException.getCode(), businessException.getDesc()));
    }

  }

  @PostMapping("/v1.0/update")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> updateProvince(@Valid @RequestBody ProvinceRequest request) throws Exception {
    try {
      ProvinceResponse response = provinceService.update(request);
      return ResponseEntity.ok(response);
    } catch (Exception businessException) {
      return ResponseEntity.ok(businessException);
    }
  }

  @PostMapping("/v1.0/delete")
  public ResponseEntity deleteProvince(@NotBlank String id) throws Exception {
    try {
      provinceService.delete(id);

      return ResponseEntity.ok("Deleted");
    } catch (Exception businessException) {
      return ResponseEntity.ok(businessException);
    }
  }

  @GetMapping("/v1.0/detail")
  public ResponseEntity<?> findById(@RequestParam("id")  String id) throws Exception {
    try {
      Province response = provinceService.findById(id);

      return ResponseEntity.ok(response);
    } catch (Exception businessException) {
      return ResponseEntity.ok(businessException);
    }
  }

  @GetMapping("/v1.0/findAll")
//  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> findAll() {
    List<Province> response = provinceService.findAll();

    return ResponseEntity.ok(response);
  }
}
