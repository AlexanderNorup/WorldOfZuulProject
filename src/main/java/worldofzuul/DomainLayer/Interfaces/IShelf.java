package worldofzuul.DomainLayer.Interfaces;

import java.util.ArrayList;

public interface IShelf {

    /**
     * @return x value of the warp position in the room
     */
    int getXPosition();

    /**
     * @return y value of the warp position in the room
     */
    int getYPosition();

    ArrayList<IItem> getItems();
}
