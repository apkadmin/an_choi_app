package com.anchoi.repository;

import com.anchoi.models.Item;
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

    @Query("SELECT new com.anchoi.response.SearchResponse(i.id, i.name, 'item', i.categoryId) FROM Item i")
    List<SearchResponse> searchAll();
    @Query("SELECT new com.anchoi.response.ItemResponse(i.id, i.name, i.categoryId, i.provinceId,i.districtId) FROM Item i")
    List<ItemResponse> getAllItemV1();

    @Query("SELECT new com.anchoi.response.SearchResponse(i.id, i.name, 'item', i.categoryId) FROM Item i where i.name = :name")
    List<SearchResponse> searchAllByName(String name);

}