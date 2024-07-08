package com.anchoi.repository.category;

import com.anchoi.entity.Category;
import com.anchoi.response.CategoryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    @Query("SELECT new com.anchoi.response.CategoryResponse(c.id,c.icon, c.type,c.value,i18n.name) from Category c join  CategoryI18n i18n on i18n.categoryId = c.id where i18n.languageId =:langCode and c.type=:type")
    List<CategoryResponse> getAllByType(String type, String langCode);

    @Query("SELECT new com.anchoi.response.CategoryResponse(c.id,c.icon, c.type,c.value,i18n.name) from Category c join  CategoryI18n i18n on i18n.categoryId = c.id where i18n.languageId =:langCode or c.type in ('MAP_CONFIG','TRUE_AUDIO_GAME','FAIL_AUDIO_GAME','BACKGROUND_AUDIO_GAME') ")
    List<CategoryResponse> getAllByLang(String langCode);
}