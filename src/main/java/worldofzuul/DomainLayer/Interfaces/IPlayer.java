package worldofzuul.DomainLayer.Interfaces;

import java.util.ArrayList;

/**
 * An interface describing what a gui needs to know about the player in world of zuul
 */
public interface IPlayer {
    /**
     * WARNING: for technical reasons this is not necessarily a reference to the inventory of the
     * underlying player object. Don't expect the inventory of the Player class to reflect changes
     * made to the object returned by this function
     * @return
     */
    ArrayList<IItem> getInventory();

    /**
     * @return x-value of the grid cell where the player should start the game
     */
    int getStartingX();
    /**
     * @return y-value of the grid cell where the player should start the game
     */
    int getStartingY();

    double getInventoryValue();
    double getBudget();
    double getInventoryProtein();
    double getInventoryCalories();

    /**
     * @return reference to the room in which the player should start the game
     */
    IRoom getStartingRoom();
}