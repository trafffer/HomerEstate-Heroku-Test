package bg.softuni.homerestate.services;

import bg.softuni.homerestate.models.entities.CategoryEntity;
import bg.softuni.homerestate.models.entities.enums.Category;

import java.util.List;

public interface CategoryService {
    void initCategories();
    List<String> getAllCategoryNames();
    CategoryEntity findByCategoryName(Category category);
}
