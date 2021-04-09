package bg.softuni.homerestate.services.impl;

import bg.softuni.homerestate.models.entities.CategoryEntity;
import bg.softuni.homerestate.models.entities.enums.Category;
import bg.softuni.homerestate.repositories.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.GeneratedValue;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository mockCategoryRepository;


    @BeforeEach
    public void setUp(){
        categoryService = new CategoryServiceImpl(mockCategoryRepository);
    }

    @Test
   public void testGetAllCategoryNames(){
//Arrange

       Category category = Category.HOUSE;
       CategoryEntity categoryEntity = new CategoryEntity();
       categoryEntity.setName(category);
       mockCategoryRepository.save(categoryEntity);

       when(mockCategoryRepository.findAll())
               .thenReturn(List.of(categoryEntity));
        List<String>categoryEntities = categoryService.getAllCategoryNames();

        Assertions.assertEquals(1,categoryEntities.size());
        String actual = categoryEntities.get(0);
        Assertions.assertEquals(categoryEntity.getName().getName(),actual);
   }

    @Test
    public void testInitCategories(){
       long categoriesNumber = Category.values().length;
       Deque<Category> categories = Arrays.stream(Category.values())
               .collect(Collectors.toCollection(ArrayDeque::new));
       List<String>savers = new ArrayList<>();
        when(mockCategoryRepository.save(any())).then(i -> {
            savers.add(categories.pollFirst().getName());
            return new CategoryEntity();
        });
        categoryService.initCategories();
        Assertions.assertEquals(savers.size(),categoriesNumber);
   }

   @Test
   public void findByCategoryName(){
        CategoryEntity categoryEntity = new CategoryEntity().setName(Category.HOUSE);
       Mockito.when(mockCategoryRepository.findByName(categoryEntity.getName()))
               .thenReturn(Optional.of(categoryEntity));
       CategoryEntity actual =categoryService.findByCategoryName(Category.HOUSE);
       Assertions.assertEquals(categoryEntity.getName(),actual.getName());
    }

}
