package com.anchoi.repository;

import com.anchoi.models.District;
import com.anchoi.response.SearchResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends DistrictRepositoryCustomer, JpaRepository<District, String> {

  @Query(value="select * from district d where lower(d.name) = :name", nativeQuery = true)
  List<District> findByName(String name);

  @Query(value="select * from district d where lower(d.name) = :name and d.province_id = :provinceId", nativeQuery = true)
  List<District> findByNameAndProvinceId(String name, String provinceId);

  @Query("SELECT new com.anchoi.response.SearchResponse(d.id, d.name, 'district', '') FROM District d")
  List<SearchResponse> searchAll();

  @Query("SELECT new com.anchoi.response.SearchResponse(d.id, d.name, 'district', '') FROM District d where d.name = :name")
  List<SearchResponse> searchAllByName(String name);

}
