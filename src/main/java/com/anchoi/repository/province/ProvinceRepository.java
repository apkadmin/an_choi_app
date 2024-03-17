package com.anchoi.repository.province;

import com.anchoi.entity.Province;
import com.anchoi.response.AreaResponse;
import com.anchoi.response.ProvinceI18nResponse;
import com.anchoi.response.SearchResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, String> {

//xu ly cho web
  @Query("SELECT new com.anchoi.response.SearchResponse(p.id, i18n.name, 'province', p.type ) FROM Province p join ProvinceI18n i18n on i18n.provinceId = p.id and i18n.languageId=:lang")
    List<SearchResponse> searchAllWeb(String lang);

  //xu ly cho app
  @Query("SELECT new com.anchoi.response.ProvinceI18nResponse(p.id, i18n.name, p.driverCode) FROM Province p join ProvinceI18n i18n on i18n.provinceId = p.id and i18n.languageId=:lang")
  List<ProvinceI18nResponse> findAll(String lang);

  @Query("SELECT new com.anchoi.response.SearchResponse(p.id, i18n.name, 'province', p.type ) FROM Province p join ProvinceI18n i18n on i18n.provinceId = p.id where i18n.name= :name")
  List<SearchResponse> searchAllByNameApp(String name);


  @Query("SELECT new com.anchoi.response.SearchResponse(p.id, i18n.name, 'province', p.type ) FROM Province p join ProvinceI18n i18n on i18n.provinceId = p.id where i18n.languageId= :lang")
  List<SearchResponse> searchAllByLang(String lang);

  @Query("SELECT new com.anchoi.response.AreaResponse(p.id, i18n.name, p.squareArea, p.type,p.population,p.density,p.yearOfDensity, p.coastline,i18n.description, p.latitude, p.longitude,p.mapImage, p.driverCode,p.createdDate,p.createdBy,p.updatedDate,p.updatedBy, i18n.urlAudio ) FROM Province p join ProvinceI18n i18n on i18n.provinceId = p.id where i18n.languageId=:lang and p.id=:id")
  AreaResponse searchByIdAndLang(String id,String lang);

  @Query("SELECT new com.anchoi.response.SearchResponse(p.id, i18n.name, 'province', p.type ) FROM Province p join ProvinceI18n i18n on i18n.provinceId = p.id where i18n.languageId= :lang and p.id in (:id)")
  List<SearchResponse> searchByListIdAndLang(List<String> id,String lang);

}
