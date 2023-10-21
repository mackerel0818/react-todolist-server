package com.example.todo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Memo")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemoEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String title;
    
    @Lob
    private String content;
    
    private String userId;
    
    @Column(name = "category_id", insertable = false, updatable = false)
    private String categoryId;
    
    @ManyToOne
    private CategoryEntity category;

    public String getCategoryId() {
        return category != null ? category.getId() : null;
    }

    public void setCategoryId(String categoryId) {
        if (categoryId == null) {
            if (category != null) {
                category.setId(null);
            }
        } else {
            if (category == null) {
                category = new CategoryEntity();
            }
            category.setId(categoryId);
        }
    }
}
