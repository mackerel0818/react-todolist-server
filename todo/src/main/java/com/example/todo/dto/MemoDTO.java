package com.example.todo.dto;

import com.example.todo.model.MemoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemoDTO {
    private String id;
    private String title;
    private String content;
    private String categoryId; // 카테고리 ID를 직접 다루도록 변경

    public MemoDTO(final MemoEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.categoryId = entity.getCategoryId();
        if (entity.getCategoryId() == null) {
            this.categoryId = "all";
        } else {
            this.categoryId = entity.getCategoryId();
        }
    }

    public static MemoEntity toEntity(final MemoDTO dto) {
        return MemoEntity.builder()
            .id(dto.getId())
            .title(dto.getTitle())
            .content(dto.getContent())
            .build();
    }
}
