package com.anchoi.repository.province;

import com.anchoi.entity.ProvinceI18n;
import com.anchoi.response.AreaResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceI18nRepository extends JpaRepository<ProvinceI18n,String> {
    public List<ProvinceI18n> findByName(String name);
    public void deleteAllByProvinceId(String provinceId);
    public List<ProvinceI18n> findAllByLanguageId(String languageId);
}
