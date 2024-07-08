package com.anchoi.service;

import com.anchoi.entity.Category;
import com.anchoi.repository.category.CategoryRepository;
import com.anchoi.response.CategoryResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public Category save(Category request) {
        return categoryRepository.save(request);
    }

    @Transactional
    public List<Category> saveAll(List<Category> request) {
        request.stream().forEach(item -> {
            if(item.getId().isEmpty()){
                item.setId(UUID.randomUUID().toString());
            }
        });

        return categoryRepository.saveAll(request);
    }

    public void delete(String id) {
        categoryRepository.deleteById(id);
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getById(String id) {
        return categoryRepository.findById(id);
    }

    public List<CategoryResponse> getAllByType(String type, String langCode){return categoryRepository.getAllByType(type, langCode);}

    //for APP
    public List<CategoryResponse> getAllByLang(String lang) {
        return categoryRepository.getAllByLang(lang);
    }

}
