package com.anchoi.repository;

import com.anchoi.models.Province;
import com.anchoi.response.SearchResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, String> {

  @Query(value="select * from province p where lower(p.name) = :name", nativeQuery = true)
  List<Province> findByName(String name);

  @Query("SELECT new com.anchoi.response.SearchResponse(p.id, p.name, 'province', p.type ) FROM Province p")
    List<SearchResponse> searchAll();

  @Query("SELECT new com.anchoi.response.SearchResponse(p.id, p.name, 'province', p.type ) FROM Province p where p.name = :name")
  List<SearchResponse> searchAllByName(String name);
}
