package worldofzuul.DomainLayer.Interfaces;

public interface IWarp extends IRoomObject{

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
