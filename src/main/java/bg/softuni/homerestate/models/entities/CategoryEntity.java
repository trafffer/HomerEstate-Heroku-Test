package bg.softuni.homerestate.models.entities;

import bg.softuni.homerestate.models.entities.enums.Category;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseEntity{
    private Category name;
    private List<Offer> offers;

    public CategoryEntity() {
        this.offers=new ArrayList<>();
    }

    @Enumerated(EnumType.STRING)
    public Category getName() {
        return name;
    }

    public CategoryEntity setName(Category name) {
        this.name = name;
        return this;
    }

    @OneToMany(mappedBy = "category")
    public List<Offer> getOffers() {
        return offers;
    }

    public CategoryEntity setOffers(List<Offer> offers) {
        this.offers = offers;
        return this;
    }
}
