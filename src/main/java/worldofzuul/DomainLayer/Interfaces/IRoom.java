package worldofzuul.DomainLayer.Interfaces;

import java.net.URI;
import java.util.ArrayList;

/**
 * Interface describing what a gui need to know about a room in world of zuul
 */
public interface IRoom {
    /**
     * @return The shelves in the given room
     */
    ArrayList<Shelf> getShelves();

    /**
     * @return The rooms width in grid cells
     */
    int getWidth();

    /**
     * @return The rooms height in grid cells
     */
    int getHeight();

    /**
     * @return Returns a list of the given rooms warps to other rooms
     */
    ArrayList<Warp> getWarps();

    /**
     * @return a reference to the resource to be used as the rooms background
     */
    String getBackground();
}
