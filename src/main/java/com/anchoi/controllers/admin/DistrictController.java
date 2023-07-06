package com.anchoi.controllers.admin;

import com.anchoi.config.BusinessException;
import com.anchoi.models.District;
import com.anchoi.request.DistrictRequest;
import com.anchoi.response.DistrictResponse;
import com.anchoi.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/district")
public class DistrictController {
  @Autowired
  DistrictService districtService;

  @PostMapping("/v1.0/save")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> save(@Valid @RequestBody DistrictRequest request) throws Exception {
    try {
      DistrictResponse response = districtService.save(request);
      return ResponseEntity.ok(response);
    } catch (BusinessException businessException) {
      return ResponseEntity.ok(new BusinessException(businessException.getCode(), businessException.getDesc()));
    }

  }

  @PostMapping("/v1.0/update")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> update(@Valid @RequestBody DistrictRequest request) throws Exception {
    try {
      DistrictResponse response = districtService.update(request);
      return ResponseEntity.ok(response);
    } catch (BusinessException businessException) {
      return ResponseEntity.ok(new BusinessException(businessException.getCode(), businessException.getDesc()));
    }
  }

  @PostMapping("/v1.0/delete")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity delete(@NotBlank String id) throws Exception {
    try {
      districtService.delete(id);

      return ResponseEntity.ok("Deleted");
    } catch (BusinessException businessException) {
      return ResponseEntity.ok(new BusinessException(businessException.getCode(), businessException.getDesc()));
    }

  }

  @GetMapping("/v1.0/detail")
//  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> findById(@RequestParam("id") String id) throws Exception {
    try {
      DistrictResponse response = districtService.findById(id);

      return ResponseEntity.ok(response);
    } catch (Exception businessException) {
      return ResponseEntity.ok(businessException);
    }
  }

  @GetMapping("/v1.0/findAll")
//  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> findAll() throws BusinessException {
    List<DistrictResponse> response = districtService.findAll();

    return ResponseEntity.ok(response);
  }
}
