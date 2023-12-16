package com.anchoi.repository.province;

import com.anchoi.entity.ProvinceI18n;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceI18nRepository extends JpaRepository<ProvinceI18n,String> {
}
