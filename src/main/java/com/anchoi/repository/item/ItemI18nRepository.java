package com.anchoi.repository.item;

import com.anchoi.entity.ItemI18n;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemI18nRepository extends JpaRepository<ItemI18n, String> {
   void deleteAllByItemId(String id);
}
