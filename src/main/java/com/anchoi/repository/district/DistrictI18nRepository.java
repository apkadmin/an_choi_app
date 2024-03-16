package com.anchoi.repository.district;

import com.anchoi.entity.DistrictI18n;
import com.anchoi.entity.ProvinceI18n;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictI18nRepository extends JpaRepository<DistrictI18n,String> {
    void deleteAllByDistrictId(String districtId);

    public List<DistrictI18n> findAllByLanguageId(String languageId);
}
