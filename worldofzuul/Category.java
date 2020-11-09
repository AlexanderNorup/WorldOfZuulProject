package worldofzuul;

/**
 * ISN'T IN USE RIGHT NOW
 * Intended for categorizing items
 */
public enum Category {
    MEAT("Meat"), FROZEN("Frozen"), DAIRY("Dairy"), FRUIT("Fruit"),
    TINNED_FOOD("Tinned Food"), VEGETABLE("Vegetable");

    private String categoryWord;

    Category(String categoryWord){
        this.categoryWord = categoryWord;
    }

    /**
     * overrides toString to return String variable categoryWord
     * when used on objects of Category types
     * @return categoryWord
     */
    @Override
    public String toString() {
        return categoryWord;
    }
}
