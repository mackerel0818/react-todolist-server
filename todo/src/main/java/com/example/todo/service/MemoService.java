package com.example.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todo.model.CategoryEntity;
import com.example.todo.model.MemoEntity;
import com.example.todo.persistence.MemoRepository;

@Service
public class MemoService {
    @Autowired
    private MemoRepository memoRepository;
    
    public List<MemoEntity> createMemo(MemoEntity entity, String userId) {
        entity.setUserId(userId);
        validate(entity);
        memoRepository.save(entity);
        return memoRepository.findByUserId(userId);
    }

    public List<MemoEntity> retrieveMemos(String userId) {
        return memoRepository.findByUserId(userId);
    }

    public List<MemoEntity> retrieveMemosByCategory(String userId, String categoryId) {
        return memoRepository.findByUserIdAndCategory_Id(userId, categoryId);
    }


    public List<MemoEntity> updateMemo(MemoEntity entity, String userId) {
        entity.setUserId(userId);
        validate(entity);
        if (memoRepository.existsById(entity.getId())) {
            MemoEntity existingMemo = memoRepository.findById(entity.getId()).orElse(null);
            if (existingMemo != null) {
                existingMemo.setTitle(entity.getTitle());
                existingMemo.setContent(entity.getContent());

                // 새로운 카테고리 엔티티 생성
                CategoryEntity newCategory = new CategoryEntity();
                newCategory.setId(entity.getCategoryId());

                existingMemo.setCategory(newCategory); // 카테고리 변경
                memoRepository.save(existingMemo);
            }
        } else {
            throw new RuntimeException("Unknown id");
        }
        return memoRepository.findByUserId(userId);
    }


    public List<MemoEntity> deleteMemo(MemoEntity entity, String userId) {
        entity.setUserId(userId);
        if (memoRepository.existsById(entity.getId())) {
            memoRepository.deleteById(entity.getId());
        } else {
            throw new RuntimeException("id does not exist");
        }
        return memoRepository.findByUserId(userId);
    }

    private void validate(MemoEntity entity) {
        if (entity == null) {
            throw new RuntimeException("Entity cannot be null.");
        }
        if (entity.getUserId() == null) {
            throw new RuntimeException("Unknown user.");
        }
    }

    
}
