package worldofzuul.PresentationLayer;

public enum Direction {

    UP("Vegan"),
    DOWN("Contains Lactose"),
    LEFT("Organic"),
    RIGHT("Contains Gluten");

    private final String directionWord;

    Direction(String directionWord) {
        this.directionWord = directionWord;
    }

    @Override
    public String toString() {
        return directionWord;
    }
}
