package worldofzuul.DomainLayer.Interfaces;

import java.util.ArrayList;

/**
 * Interface describing what a gui need to know about a room in world of zuul
 */
public interface IRoom {

    /**
     * @return The rooms width in grid cells
     */
    int getWidth();

    /**
     * @return The rooms height in grid cells
     */
    int getHeight();

    /**
     * @return The shelves in the given room
     */
    ArrayList<IRoomObject> getObjects();

    /**
     * @return a reference to the resource to be used as the rooms background
     */
    String getBackground();
}
