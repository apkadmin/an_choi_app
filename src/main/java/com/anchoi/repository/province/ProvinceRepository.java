package com.anchoi.repository.province;

import com.anchoi.entity.Province;
import com.anchoi.response.ProvinceV1Response;
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
  @Query("SELECT new com.anchoi.response.ProvinceV1Response(p.id, i18n.name, p.driverCode) FROM Province p join ProvinceI18n i18n on i18n.provinceId = p.id and i18n.languageId=:lang")
  List<ProvinceV1Response> searchAllOnlyNameAndIdApp();

  @Query("SELECT new com.anchoi.response.SearchResponse(p.id, p.name, 'province', p.type ) FROM Province p join ProvinceI18n i18n on i18n.provinceId = p.id where i18n.name = :name")
  List<SearchResponse> searchAllByNameApp(String name);
}
