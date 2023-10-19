package com.example.todo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.todo.model.MemoEntity;

import java.util.List;

@Repository
public interface MemoRepository extends JpaRepository<MemoEntity, String> {
    List<MemoEntity> findByCategoryIsNull();
	List<MemoEntity> findByUserId(String userId);
	List<MemoEntity> findByUserIdAndCategoryId(String userId, String categoryId);
}
