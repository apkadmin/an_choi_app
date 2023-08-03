package com.anchoi.repository;

import com.anchoi.config.BusinessException;
import com.anchoi.response.DistrictResponse;

import java.util.List;

public interface DistrictRepositoryCustomer {
    List<DistrictResponse> findAllWithProvinceName() throws BusinessException;
}
