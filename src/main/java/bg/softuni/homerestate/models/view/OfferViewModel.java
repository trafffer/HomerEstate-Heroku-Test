package bg.softuni.homerestate.models.view;


import java.math.BigDecimal;

public class OfferViewModel {
    private Long id;
    private String type;
    private String category;
    private BigDecimal price;
    private String city;
    private String address;
    private Integer area;
    private String imgUrl;
    private Integer rooms;
    private BigDecimal pricePerSqM;

    public OfferViewModel() {
    }

    public String getCategory() {
        return category;
    }

    public OfferViewModel setCategory(String category) {
        this.category = category;
        return this;
    }

    public Long getId() {
        return id;
    }

    public OfferViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return type;
    }

    public OfferViewModel setType(String type) {
        this.type = type;
        return this;
    }

    public String getCity() {
        return city;
    }

    public OfferViewModel setCity(String city) {
        this.city = city;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public OfferViewModel setAddress(String address) {
        this.address = address;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OfferViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getArea() {
        return area;
    }

    public OfferViewModel setArea(Integer area) {
        this.area = area;
        return this;
    }


    public String getImgUrl() {
        return imgUrl;
    }

    public OfferViewModel setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public Integer getRooms() {
        return rooms;
    }

    public OfferViewModel setRooms(Integer rooms) {
        this.rooms = rooms;
        return this;
    }

    public BigDecimal getPricePerSqM() {
        return pricePerSqM;
    }

    public OfferViewModel setPricePerSqM(BigDecimal pricePerSqM) {
        this.pricePerSqM = pricePerSqM;
        return this;
    }
}
