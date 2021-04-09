package bg.softuni.homerestate.models.entities;

import bg.softuni.homerestate.models.entities.enums.City;
import bg.softuni.homerestate.models.entities.enums.Type;


import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Entity
@Table(name = "offers")
public class Offer extends BaseEntity{
    private Type type;
    private CategoryEntity category;
    private BigDecimal price;
    private City city;
    private String address;
    private Integer area;
    private Integer floor;
    private LocalDateTime createdOn;
    private Integer rooms;
    private Integer visited;
    private String description;
    private String imgUrl;
    private BigDecimal pricePerSqM;
    private UserEntity author;

    public Offer() {
    }

    @Enumerated(EnumType.STRING)
    public Type getType() {
        return type;
    }

    public Offer setType(Type type) {
        this.type = type;
        return this;
    }

    @ManyToOne
    public CategoryEntity getCategory() {
        return category;
    }

    public Offer setCategory(CategoryEntity category) {
        this.category = category;
        return this;
    }

    @Column(name = "price",nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public Offer setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    @Enumerated(EnumType.ORDINAL)
    public City getCity() {
        return city;
    }

    public Offer setCity(City city) {
        this.city = city;
        return this;
    }

    @Column(name = "address",nullable = false)
    public String getAddress() {
        return address;
    }

    public Offer setAddress(String address) {
        this.address = address;
        return this;
    }

    @Column(name = "area",nullable = false)
    public Integer getArea() {
        return area;
    }

    public Offer setArea(Integer area) {
        this.area = area;
        return this;
    }

    @Column(name = "floor")
    public Integer getFloor() {
        return floor;
    }

    public Offer setFloor(Integer story) {
        this.floor = story;
        return this;
    }

    @Column(name = "created_on",nullable = false)
    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public Offer setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    @Column(name = "visited")
    public Integer getVisited() {
        return visited;
    }

    public Offer setVisited(Integer visited) {
        this.visited = visited;
        return this;
    }

    @Column(name = "description",columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public Offer setDescription(String description) {
        this.description = description;
        return this;
    }

    @Column(name = "image_url",nullable = false,columnDefinition = "TEXT")
    public String getImgUrl() {
        return imgUrl;
    }

    public Offer setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    @ManyToOne
    public UserEntity getAuthor() {
        return author;
    }

    public Offer setAuthor(UserEntity author) {
        this.author = author;
        return this;
    }

    @Column(name = "rooms")
    public Integer getRooms() {
        return rooms;
    }

    public Offer setRooms(Integer rooms) {
        this.rooms = rooms;
        return this;
    }

    @Column(name = "price_per_sqm",nullable = false)
    public BigDecimal getPricePerSqM() {
        return pricePerSqM;
    }

    public Offer setPricePerSqM(BigDecimal number) {
        this.pricePerSqM = number;
        return this;
    }

    public void incrementVisited(){
        Integer newVisited = this.getVisited()+1;
        setVisited(newVisited);
    }
}
