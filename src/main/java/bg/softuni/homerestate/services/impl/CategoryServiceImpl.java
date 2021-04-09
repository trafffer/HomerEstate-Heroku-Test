package bg.softuni.homerestate.services.impl;

import bg.softuni.homerestate.models.entities.CategoryEntity;
import bg.softuni.homerestate.models.entities.enums.Category;
import bg.softuni.homerestate.repositories.CategoryRepository;
import bg.softuni.homerestate.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class CategoryServiceImpl  implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public void initCategories() {
        if (categoryRepository.count()==0){
            Arrays.stream(Category.values()).forEach(c->{
                CategoryEntity category = new CategoryEntity().setName(c);
                categoryRepository.save(category);
            });
        }
    }

    @Override
    public List<String> getAllCategoryNames() {
        List<String>names = new ArrayList<>();
        categoryRepository.findAll().forEach(c->{
            String name = c.getName().getName();
            names.add(name);
        });
        return names;
    }

    @Override
    public CategoryEntity findByCategoryName(Category category) {
        return this.categoryRepository.findByName(category).
                orElseThrow(()->new IllegalArgumentException("This category doesn't exist"));
    }

}
