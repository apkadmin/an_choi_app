package com.anchoi.repository.district;

import com.anchoi.entity.District;
import com.anchoi.response.AreaResponse;
import com.anchoi.response.DistrictI18nResponse;
import com.anchoi.response.SearchResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends DistrictRepositoryCustomer, JpaRepository<District, String> {
  @Query("SELECT new com.anchoi.response.DistrictI18nResponse(d.id,  i18n.name ,d.provinceId) FROM District d join DistrictI18n i18n on i18n.districtId = d.id where i18n.languageId=:lang")
  List<DistrictI18nResponse> findAll(String lang);


  @Query("SELECT new com.anchoi.response.DistrictI18nResponse(d.id, i18n.name,d.provinceId) FROM District d join DistrictI18n i18n on i18n.districtId = d.id")
  List<DistrictI18nResponse> searchAllV1();
  @Query("SELECT new com.anchoi.response.DistrictI18nResponse(d.id,  i18n.name ,d.provinceId) FROM District d join DistrictI18n i18n on i18n.districtId = d.id where d.provinceId = :provinceId and i18n.languageId=:lang")
  List<DistrictI18nResponse> findAllByProvinceId(String provinceId, String lang);


  @Query("SELECT new com.anchoi.response.SearchResponse(d.id, i18n.name, 'district', '') FROM District d join DistrictI18n i18n on i18n.districtId = d.id where i18n.name = :name")
  List<SearchResponse> searchAllByName(String name);

  @Query("SELECT new com.anchoi.response.SearchResponse(d.id, i18n.name, 'district', '', d.provinceId) FROM District d join DistrictI18n i18n on i18n.districtId = d.id where i18n.languageId = :lang")
  List<SearchResponse> searchAllByLang(String lang);


  @Query("SELECT new com.anchoi.response.AreaResponse(d.id, i18n.name, d.squareArea, '',d.population,d.density,d.yearOfDensity, d.coastline,i18n.description, d.latitude, d.longitude,d.mapImage, '',d.createdDate,d.createdBy,d.updatedDate,d.updatedBy, i18n.urlAudio ) FROM District d join DistrictI18n i18n on i18n.districtId = d.id where i18n.languageId=:lang and d.id=:id")
  AreaResponse searchByIdAndLang(String id, String lang);
  @Query("SELECT new com.anchoi.response.SearchResponse(d.id, i18n.name, 'district', '', d.provinceId) FROM District d join DistrictI18n i18n on i18n.districtId = d.id where i18n.languageId = :lang and d.id in (:id)")
  List<SearchResponse> searchByListIdAndLang(List<String> id, String lang);

  @Query("SELECT new com.anchoi.response.SearchResponse(d.id, i18n.name, 'district', '', d.provinceId) FROM District d join DistrictI18n i18n on i18n.districtId = d.id")
  List<SearchResponse> searchAll();
}
