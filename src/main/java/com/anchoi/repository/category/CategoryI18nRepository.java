package com.anchoi.repository.category;

import com.anchoi.entity.CategoryI18n;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryI18nRepository extends JpaRepository<CategoryI18n, String> {

}
