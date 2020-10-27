package worldofzuul;

public enum Extra {
    VEGAN("Vegan"), LACTOSE_FREE("Lactose Free"), CONTAINS_PEANUTS("Contains Peanuts"),
    ORGANIC("Organic"), GLUTEN("Gluten"), CONTAINS_SOY("Contains Soy");

    private String extraWord;

    Extra(String extraWord) {
        this.extraWord = extraWord;
    }

    @Override
    public String toString() {
        return extraWord;
    }
}
