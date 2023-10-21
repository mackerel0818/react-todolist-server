package com.example.todo.dto;

import com.example.todo.model.CategoryEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryDTO {
    private String id;
    private String name;

    public CategoryDTO(final CategoryEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }

    public static CategoryEntity toEntity(final CategoryDTO dto) {
        return CategoryEntity.builder()
            .id(dto.getId())
            .name(dto.getName())
            .build();
    }
}
