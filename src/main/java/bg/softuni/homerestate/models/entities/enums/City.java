package bg.softuni.homerestate.models.entities.enums;

public enum City {
    SOFIA("Sofia"),
    PLOVDIV("Plovdiv"),
    VARNA("Varna"),
    BURGAS("Burgas"),
    STARA_ZAGORA("Stara Zagora"),
    VIDIN("Vidin"),
    VELIKO_TURNOVO("Veliko Turnovo"),
    RUSE("Ruse"),
    PLEVEN("Pleven"),
    BLAGOEVGRAD("Blagoevgrad");

    private final String name;

    City(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }
}
