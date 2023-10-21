package com.example.todo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.todo.model.CategoryEntity;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {

	List<CategoryEntity> findByUserId(String userId);

	CategoryEntity findByIdAndUserId(String id, String userId);
}
