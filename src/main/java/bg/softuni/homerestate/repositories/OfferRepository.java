package bg.softuni.homerestate.repositories;

import bg.softuni.homerestate.models.entities.Offer;
import bg.softuni.homerestate.models.entities.UserEntity;
import bg.softuni.homerestate.models.entities.enums.Category;
import bg.softuni.homerestate.models.entities.enums.City;
import bg.softuni.homerestate.models.entities.enums.Type;
import bg.softuni.homerestate.models.view.OfferViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Long> {
//    @Query(value = "SELECT * FROM homerestatedb.offers  as o ORDER BY o.visited DESC LIMIT 9",nativeQuery = true)
    @Query("select o FROM Offer o ORDER BY o.visited DESC")
    List<Offer> findAllOrderByVisitedDesc();

    List<Offer> findAllByTypeOrderByVisitedDesc(Type type);

    @Query("SELECT o FROM Offer o WHERE o.type= :type AND o.category.name= :category AND o.city= :city AND " +
            "o.price >= :priceMin AND o.price <= :priceMax ORDER BY o.visited DESC")
    List<Offer> searchOffersByCriteria(@Param("type") Type type, @Param("category") Category category,
                                       @Param("city") City city, @Param("priceMin")BigDecimal priceMin,
                                       @Param("priceMax") BigDecimal priceMax);

    List<Offer> findAllByAuthorOrderByVisitedDesc(UserEntity user);
}
