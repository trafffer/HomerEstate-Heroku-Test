package bg.softuni.homerestate.models.binding;

import bg.softuni.homerestate.models.entities.enums.Category;
import bg.softuni.homerestate.models.entities.enums.City;
import bg.softuni.homerestate.models.entities.enums.Type;

import java.math.BigDecimal;

public class SearchBindingModel {
    private Type type;
    private Category category;
    private City city;
    private BigDecimal priceLow;
    private BigDecimal priceHigh;

    public SearchBindingModel() {
    }

    public Type getType() {
        return type;
    }

    public SearchBindingModel setType(Type type) {
        this.type = type;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public SearchBindingModel setCategory(Category category) {
        this.category = category;
        return this;
    }

    public City getCity() {
        return city;
    }

    public SearchBindingModel setCity(City city) {
        this.city = city;
        return this;
    }

    public BigDecimal getPriceLow() {
        return priceLow;
    }

    public SearchBindingModel setPriceLow(BigDecimal priceLow) {
        this.priceLow = priceLow;
        return this;
    }

    public BigDecimal getPriceHigh() {
        return priceHigh;
    }

    public SearchBindingModel setPriceHigh(BigDecimal priceHigh) {
        this.priceHigh = priceHigh;
        return this;
    }

    @Override
    public String toString() {
        return "SearchBindingModel{" +
                "type=" + type +
                ", category=" + category +
                ", city=" + city +
                ", priceLow=" + priceLow +
                ", priceHigh=" + priceHigh +
                '}';
    }
}
