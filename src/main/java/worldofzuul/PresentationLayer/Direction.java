package worldofzuul.PresentationLayer;

public enum Direction {

    UP("Vegan"),
    DOWN("Contains Lactose"),
    LEFT("Organic"),
    RIGHT("Contains Gluten");

    private final String directionWord;

    Direction(String cirectionWord) {
        this.directionWord = cirectionWord;
    }

    @Override
    public String toString() {
        return directionWord;
    }
}
