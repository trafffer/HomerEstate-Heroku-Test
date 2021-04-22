package bg.softuni.homerestate.models.binding;

import bg.softuni.homerestate.models.entities.enums.Category;
import bg.softuni.homerestate.models.entities.enums.City;
import bg.softuni.homerestate.models.entities.enums.Type;
import org.aspectj.lang.annotation.DeclareError;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.websocket.OnError;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class OfferAddBindingModel {
    @NotNull(message = "The type cannot be empty")
    private Type type;
    @NotNull(message = "The category cannot be empty")
    private Category category;
    @NotNull(message = "The price cannot be null")
    @Positive(message = "The price must be positive number")
    private BigDecimal price;
    @NotNull(message = "The city cannot be null")
    private City city;
    @NotBlank(message = "The address cannot be empty")
    @Size(min = 5, max = 80, message = "The address should be between 5 and 80 characters long")
    private String address;
    @NotNull(message = "The area cannot be null")
    @Positive(message = "The area must be a positive number")
    private Integer area;
    @Positive(message = "The floor must be a positive number")
    private Integer floor;
    @Positive(message = "The number of rooms must be positive")
    private Integer rooms;
    @NotBlank(message = "The description cannot be blank")
    @Size(min = 5, message = "Description must be at least 5 characters long")
    private String description;
    @NotNull(message = "You must select images!")
    private List<String> imgUrl = new ArrayList<>();


    public OfferAddBindingModel() {
    }

    public Type getType() {
        return type;
    }

    public OfferAddBindingModel setType(Type type) {
        this.type = type;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public OfferAddBindingModel setCategory(Category category) {
        this.category = category;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OfferAddBindingModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public City getCity() {
        return city;
    }

    public OfferAddBindingModel setCity(City city) {
        this.city = city;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public OfferAddBindingModel setAddress(String address) {
        this.address = address;
        return this;
    }

    public Integer getArea() {
        return area;
    }

    public OfferAddBindingModel setArea(Integer area) {
        this.area = area;
        return this;
    }

    public Integer getFloor() {
        return floor;
    }

    public OfferAddBindingModel setFloor(Integer floor) {
        this.floor = floor;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OfferAddBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<String> getImgUrl() {
        return imgUrl;
    }

    public OfferAddBindingModel setImgUrl(List<String> imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public Integer getRooms() {
        return rooms;
    }

    public OfferAddBindingModel setRooms(Integer rooms) {
        this.rooms = rooms;
        return this;
    }
}
