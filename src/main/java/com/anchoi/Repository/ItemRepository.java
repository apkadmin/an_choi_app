package com.anchoi.repository;

import com.anchoi.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
    List<Item> getAllByProvinceId(String provinceId);
    List<Item> getAllByDistrictId(String districtId);
}