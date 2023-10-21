package com.example.todo.controller;

import com.example.todo.dto.CategoryDTO;
import com.example.todo.dto.ResponseDTO;
import com.example.todo.model.CategoryEntity;
import com.example.todo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> createCategory(@AuthenticationPrincipal String userId, @RequestBody CategoryEntity entity) {
        try {
            List<CategoryEntity> entities = categoryService.createCategory(entity, userId);
            List<CategoryDTO> dtos = entities.stream().map(CategoryDTO::new).collect(Collectors.toList());
            ResponseDTO<CategoryDTO> response = ResponseDTO.<CategoryDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<CategoryDTO> response = ResponseDTO.<CategoryDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveCategories(@AuthenticationPrincipal String userId) {
        List<CategoryEntity> entities = categoryService.retrieveCategories(userId);
        List<CategoryDTO> dtos = entities.stream().map(CategoryDTO::new).collect(Collectors.toList());
        ResponseDTO<CategoryDTO> response = ResponseDTO.<CategoryDTO>builder().data(dtos).build();
        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateCategory(@AuthenticationPrincipal String userId, @RequestBody CategoryEntity entity) {
        try {
            List<CategoryEntity> entities = categoryService.updateCategory(entity, userId);
            List<CategoryDTO> dtos = entities.stream().map(CategoryDTO::new).collect(Collectors.toList());
            ResponseDTO<CategoryDTO> response = ResponseDTO.<CategoryDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<CategoryDTO> response = ResponseDTO.<CategoryDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCategory(@AuthenticationPrincipal String userId, @RequestBody CategoryEntity entity) {
        try {
            List<CategoryEntity> entities = categoryService.deleteCategory(entity, userId);
            List<CategoryDTO> dtos = entities.stream().map(CategoryDTO::new).collect(Collectors.toList());
            ResponseDTO<CategoryDTO> response = ResponseDTO.<CategoryDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<CategoryDTO> response = ResponseDTO.<CategoryDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
