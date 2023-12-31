package com.anchoi.repository.item;

import com.anchoi.entity.Item;
import com.anchoi.response.ItemResponse;
import com.anchoi.response.SearchResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
    List<Item> getAllByProvinceId(String provinceId);
    List<Item> getAllByDistrictId(String districtId);

    @Query("SELECT new com.anchoi.response.SearchResponse(i.id, i18n.name, 'item', i.categoryId) FROM Item i join ItemI18n i18n on i18n.itemId = i.id where i18n.languageId=:lang")
    List<SearchResponse> searchAll(String lang);

    @Query("SELECT new com.anchoi.response.SearchResponse(i.id, i18n.name, 'item', i.categoryId) FROM Item i join ItemI18n i18n on i18n.itemId = i.id  where i18n.name = :name")
    List<SearchResponse> searchAllByName(String name);

}