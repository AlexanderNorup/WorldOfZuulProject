package worldofzuul.PresentationLayer;

/**
 * Represents a position in 2D space.
 */
public class Position {
    private int x;
    private int y;

    /**
     * Creates a new position in 2D space.
     * Positions are messured from the top-left corner of the window.
     * @param x The x-value.
     * @param y The y-value
     */
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Position("+x+","+y+")";
    }
}
