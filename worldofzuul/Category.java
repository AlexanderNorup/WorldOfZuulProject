package worldofzuul;

public enum Category {
    MEAT("Meat"), FROZEN("Frozen"), DAIRY("Dairy"), FRUIT("Fruit"),
    TINNED_FOOD("Tinned Food"), VEGETABLE("Vegetable");

    private String categoryWord;

    Category(String categoryWord){
        this.categoryWord = categoryWord;
    }

    @Override
    public String toString() {
        return categoryWord;
    }
}
