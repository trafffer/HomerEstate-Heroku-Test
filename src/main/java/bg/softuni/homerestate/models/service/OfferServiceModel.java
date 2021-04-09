package bg.softuni.homerestate.models.service;

import bg.softuni.homerestate.models.entities.UserEntity;
import bg.softuni.homerestate.models.entities.enums.Category;
import bg.softuni.homerestate.models.entities.enums.City;
import bg.softuni.homerestate.models.entities.enums.Type;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OfferServiceModel {
    private Long id;
    private Type type;
    private Category category;
    private BigDecimal price;
    private City city;
    private String address;
    private Integer area;
    private Integer floor;
    private LocalDateTime createdOn;
    private Integer rooms;
    private Integer visited;
    private String description;
    private List<String> imgUrl=new ArrayList<>();
    private UserEntity author;
    private BigDecimal pricePerSqM;

    public OfferServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public OfferServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public Type getType() {
        return type;
    }

    public OfferServiceModel setType(Type type) {
        this.type = type;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public OfferServiceModel setCategory(Category category) {
        this.category = category;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OfferServiceModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public City getCity() {
        return city;
    }

    public OfferServiceModel setCity(City city) {
        this.city = city;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public OfferServiceModel setAddress(String address) {
        this.address = address;
        return this;
    }

    public Integer getArea() {
        return area;
    }

    public OfferServiceModel setArea(Integer area) {
        this.area = area;
        return this;
    }

    public Integer getFloor() {
        return floor;
    }

    public OfferServiceModel setFloor(Integer floor) {
        this.floor = floor;
        return this;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public OfferServiceModel setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public Integer getVisited() {
        return visited;
    }

    public OfferServiceModel setVisited(Integer visited) {
        this.visited = visited;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OfferServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<String> getImgUrl() {
        return imgUrl;
    }

    public OfferServiceModel setImgUrl(List<String> imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public OfferServiceModel setAuthor(UserEntity author) {
        this.author = author;
        return this;
    }

    public Integer getRooms() {
        return rooms;
    }

    public OfferServiceModel setRooms(Integer rooms) {
        this.rooms = rooms;
        return this;
    }

    public BigDecimal getPricePerSqM() {
        return pricePerSqM;
    }

    public OfferServiceModel setPricePerSqM(BigDecimal pricePerSqM) {
        this.pricePerSqM = pricePerSqM;
        return this;
    }
}
