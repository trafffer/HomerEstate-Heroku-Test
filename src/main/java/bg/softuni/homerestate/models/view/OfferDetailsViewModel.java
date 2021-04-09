package bg.softuni.homerestate.models.view;

import bg.softuni.homerestate.models.entities.enums.Category;
import bg.softuni.homerestate.models.entities.enums.City;
import bg.softuni.homerestate.models.entities.enums.Type;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OfferDetailsViewModel {
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
    private List<String> imgUrl = new ArrayList<>();
    private String authorUsername;
    private BigDecimal pricePerSqM;


    public OfferDetailsViewModel() {
    }


    public Long getId() {
        return id;
    }

    public OfferDetailsViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public Type getType() {
        return type;
    }

    public OfferDetailsViewModel setType(Type type) {
        this.type = type;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public OfferDetailsViewModel setCategory(Category category) {
        this.category = category;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OfferDetailsViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public City getCity() {
        return city;
    }

    public OfferDetailsViewModel setCity(City city) {
        this.city = city;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public OfferDetailsViewModel setAddress(String address) {
        this.address = address;
        return this;
    }

    public Integer getArea() {
        return area;
    }

    public OfferDetailsViewModel setArea(Integer area) {
        this.area = area;
        return this;
    }

    public Integer getFloor() {
        return floor;
    }

    public OfferDetailsViewModel setFloor(Integer floor) {
        this.floor = floor;
        return this;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public OfferDetailsViewModel setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public Integer getRooms() {
        return rooms;
    }

    public OfferDetailsViewModel setRooms(Integer rooms) {
        this.rooms = rooms;
        return this;
    }

    public Integer getVisited() {
        return visited;
    }

    public OfferDetailsViewModel setVisited(Integer visited) {
        this.visited = visited;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OfferDetailsViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<String> getImgUrl() {
        return imgUrl;
    }

    public OfferDetailsViewModel setImgUrl(List<String> imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public OfferDetailsViewModel setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
        return this;
    }

    public BigDecimal getPricePerSqM() {
        return pricePerSqM;
    }

    public OfferDetailsViewModel setPricePerSqM(BigDecimal pricePerSqM) {
        this.pricePerSqM = pricePerSqM;
        return this;
    }
}
