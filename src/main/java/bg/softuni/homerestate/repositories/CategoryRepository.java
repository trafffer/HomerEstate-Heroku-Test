package bg.softuni.homerestate.repositories;

import bg.softuni.homerestate.models.entities.CategoryEntity;
import bg.softuni.homerestate.models.entities.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {
    Optional<CategoryEntity> findByName(Category category);
}
