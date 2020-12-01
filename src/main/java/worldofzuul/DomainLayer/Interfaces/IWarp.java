package worldofzuul.DomainLayer.Interfaces;

public interface IWarp {
    /**
     * @return x value of the warp position in the room
     */
    int getX();

    /**
     * @return y value of the warp position in the room
     */
    int getY();

    /**
     * @return The room the player is warped to when moving into the warp
     */
    IRoom getDestination();

    /**
     * @return x value of the destination grid cell in the room that the player is warped to
     */
    int getDestX();

    /**
     * @return y value of destination the grid cell in the room that the player is warped to
     */
    int getDestY();
}
