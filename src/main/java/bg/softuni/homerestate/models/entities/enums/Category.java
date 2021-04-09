package bg.softuni.homerestate.models.entities.enums;

public enum Category {
    VACANT_LOT("Vacant lot"),
    HOUSE("House"),
    ONE_BEDROOM_APARTMENT("One-bedroom apartment"),
    TWO_BEDROOM_APARTMENT("Two-bedroom apartment"),
    THREE_BEDROOM_APARTMENT("Three-bedroom apartment"),
    STUDIO("Studio"),
    VILLA("Villa"),
    OTHER("Other");

    private final String name;

    Category(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
