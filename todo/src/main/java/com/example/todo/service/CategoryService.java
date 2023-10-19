import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todo.model.CategoryEntity;
import com.example.todo.persistence.CategoryRepository;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryEntity> createCategory(CategoryEntity entity, String userId) {
        entity.setUserId(userId);
        validate(entity);
        categoryRepository.save(entity);
        return categoryRepository.findByUserId(userId);
    }

    public List<CategoryEntity> retrieveCategories(String userId) {
        return categoryRepository.findByUserId(userId);
    }

    public List<CategoryEntity> updateCategory(CategoryEntity entity, String userId) {
        entity.setUserId(userId);
        validate(entity);
        if (categoryRepository.existsById(entity.getId())) {
            categoryRepository.save(entity);
        } else {
            throw new RuntimeException("Unknown id");
        }
        return categoryRepository.findByUserId(userId);
    }

    public List<CategoryEntity> deleteCategory(CategoryEntity entity, String userId) {
        entity.setUserId(userId);
        if (categoryRepository.existsById(entity.getId())) {
            categoryRepository.deleteById(entity.getId());
        } else {
            throw new RuntimeException("id does not exist");
        }
        return categoryRepository.findByUserId(userId);
    }

    private void validate(CategoryEntity entity) {
        if (entity == null) {
            throw new RuntimeException("Entity cannot be null.");
        }
        if (entity.getUserId() == null) {
            throw new RuntimeException("Unknown user.");
        }
    }
}
