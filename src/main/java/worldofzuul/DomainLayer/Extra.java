package worldofzuul.DomainLayer;

/**
 * Enum for properties that only apply to relatively few items
 * Each item has a list of "Extras" for example an item can be organic and contain lactose
 */
public enum Extra {
    VEGAN("Vegan"),
    CONTAINS_LACTOSE("Contains Lactose"),
    ORGANIC("Organic"),
    CONTAINS_GLUTEN("Contains Gluten"),
    CONTAINS_SOY("Contains Soy"),
    FROZEN("Frozen"),
    CANNED("Canned");

    private String extraWord;

    Extra(String extraWord) {
        this.extraWord = extraWord;
    }

    @Override
    public String toString() {
        return extraWord;
    }
}
