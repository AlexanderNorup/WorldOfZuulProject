package worldofzuul;

public enum Extra {
    VEGAN("Vegan"),
    CONTAINS_LACTOSE("Contains Lactose"),
    ORGANIC("Organic"),
    CONTAINS_GLUTEN("Gluten"),
    CONTAINS_SOY("Contains Soy");

    private String extraWord;

    Extra(String extraWord) {
        this.extraWord = extraWord;
    }

    @Override
    public String toString() {
        return extraWord;
    }
}
