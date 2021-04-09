package bg.softuni.homerestate.models.entities.enums;

public enum Type {
    SALE("sale"), RENT("rent");

    private final String name;
    Type(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
