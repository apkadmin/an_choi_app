package com.anchoi.repository.item;

import com.anchoi.entity.Item;
import com.anchoi.response.ItemAppResponse;
import com.anchoi.response.ItemI18nResponse;
import com.anchoi.response.SearchResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
    @Query("SELECT new com.anchoi.response.ItemI18nResponse(i.id,i18n.name,i.categoryId,i.provinceId,i.districtId) FROM Item i join ItemI18n i18n on i18n.itemId = i.id where i18n.languageId=:lang and  i.provinceId=:provinceId")
    List<ItemI18nResponse> getAllByProvinceId(String provinceId, String lang);
    @Query("SELECT new com.anchoi.response.ItemI18nResponse(i.id,i18n.name,i.categoryId,i.provinceId,i.districtId) FROM Item i join ItemI18n i18n on i18n.itemId = i.id where i18n.languageId=:lang and  i.districtId=:districtId")
    List<ItemI18nResponse> getAllByDistrictId(String districtId, String lang);

    @Query("SELECT new com.anchoi.response.ItemI18nResponse(i.id,i18n.name,i.categoryId,i.provinceId,i.districtId) FROM Item i join ItemI18n i18n on i18n.itemId = i.id where i18n.languageId=:lang")
    List<ItemI18nResponse> findAll(String lang);
    @Query("SELECT new com.anchoi.response.SearchResponse(i.id, i18n.name, 'item', i.categoryId) FROM Item i join ItemI18n i18n on i18n.itemId = i.id  where i18n.name = :name")
    List<SearchResponse> searchAllByName(String name);

    @Query("SELECT new com.anchoi.response.SearchResponse(i.id, i18n.name, 'item', i.categoryId) FROM Item i join ItemI18n i18n on i18n.itemId = i.id where i18n.languageId=:lang")
    List<SearchResponse> searchAllByLang(String lang);


    @Query("SELECT new com.anchoi.response.ItemAppResponse(i.id, i.categoryId,i.districtId,i.provinceId,i.latitude,i.longitude,i.latMap,i.longMap,i.createdDate, i.createdBy, i.updatedDate, i.updatedBy,i18n.name,i18n.description, i18n.urlAudio, i18n.address) FROM Item i join ItemI18n i18n on i18n.itemId = i.id where i18n.languageId=:lang and i.id=:id")
    ItemAppResponse getDetailByLang(String id, String lang);

}