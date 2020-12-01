package worldofzuul.DomainLayer.Interfaces;

/**
 * A warp describes a grid cell in the gui which "teleports" the player to another room
 */
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

    /**
     * @return x value of the warp position in the room
     */
    public int getX() {
        return x;
    }

    /**
     * @return y value of the warp position in the room
     */
    public int getY() {
        return y;
    }

    /**
     * @return The room the player is warped to when moving into the warp
     */
    public IRoom getDestination() {
        return destination;
    }

    /**
     * @return x value of the destination grid cell in the room that the player is warped to
     */
    public int getDestX() {
        return destX;
    }

    /**
     * @return y value of destination the grid cell in the room that the player is warped to
     */
    public int getDestY() {
        return destY;
    }
}
