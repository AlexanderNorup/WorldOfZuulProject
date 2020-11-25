package worldofzuul.DomainLayer.Interfaces;

public class Warp {
    private int x;
    private int y;
    private IRoom destination;
    private int destX;
    private int destY;

    public Warp(int x, int y, IRoom destination, int destX, int destY) {
        this.x = x;
        this.y = y;
        this.destination = destination;
        this.destX = destX;
        this.destY = destY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public IRoom getDestination() {
        return destination;
    }

    public int getDestX() {
        return destX;
    }

    public int getDestY() {
        return destY;
    }
}
