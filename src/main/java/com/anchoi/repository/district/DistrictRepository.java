package com.anchoi.repository.district;

import com.anchoi.entity.District;
import com.anchoi.response.DistrictV1Response;
import com.anchoi.response.SearchResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends DistrictRepositoryCustomer, JpaRepository<District, String> {

  @Query(value="select * from district d", nativeQuery = true)
  List<District> findByName(String name);


  @Query("SELECT new com.anchoi.response.SearchResponse(d.id, '', 'district', '', d.provinceId) FROM District d")
  List<SearchResponse> searchAll();

  @Query("SELECT new com.anchoi.response.DistrictV1Response(d.id, i18n.name,d.provinceId) FROM District d join DistrictI18n i18n on i18n.districtId = d.id")
  List<DistrictV1Response> searchAllV1();
  @Query("SELECT new com.anchoi.response.DistrictV1Response(d.id,  '' ,d.provinceId) FROM District d where d.provinceId = :provinceId")
  List<DistrictV1Response> findAllByProvinceId(String provinceId);


  @Query("SELECT new com.anchoi.response.SearchResponse(d.id, i18n.name, 'district', '') FROM District d join DistrictI18n i18n on i18n.districtId = d.id where i18n.name = :name")
  List<SearchResponse> searchAllByName(String name);

}
