package com.anchoi.repository.district;

import com.anchoi.entity.DistrictI18n;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictI18nRepository extends JpaRepository<DistrictI18n,String> {
    void deleteAllByDistrictId(String id);
}
