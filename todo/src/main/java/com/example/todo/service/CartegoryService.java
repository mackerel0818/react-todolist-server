package com.example.todo.service;

import com.example.todo.dto.CategoryDTO;
import com.example.todo.model.CategoryEntity;
import com.example.todo.persistence.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDTO> getAllCategories() {
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();
        return categoryEntities.stream().map(CategoryDTO::new).collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(String id) {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);
        return categoryEntity.map(CategoryDTO::new).orElse(null);
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = CategoryDTO.toEntity(categoryDTO);
        categoryEntity = categoryRepository.save(categoryEntity);
        return new CategoryDTO(categoryEntity);
    }

    public CategoryDTO updateCategory(String id, CategoryDTO categoryDTO) {
        Optional<CategoryEntity> existingCategory = categoryRepository.findById(id);
        if (existingCategory.isPresent()) {
            CategoryEntity updatedCategory = CategoryDTO.toEntity(categoryDTO);
            updatedCategory.setId(id);
            updatedCategory = categoryRepository.save(updatedCategory);
            return new CategoryDTO(updatedCategory);
        }
        return null; // Handle not found
    }

    public void deleteCategory(String id) {
        categoryRepository.deleteById(id);
    }
}
