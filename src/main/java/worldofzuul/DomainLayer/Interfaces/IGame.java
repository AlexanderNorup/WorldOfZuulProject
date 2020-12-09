package worldofzuul.DomainLayer.Interfaces;

import worldofzuul.DomainLayer.CheckoutReturnObject;
import worldofzuul.DomainLayer.PlayerType;

import java.util.ArrayList;

/**
 * Main interface to connect a presentation layer to the world of zuul game
 */
public interface IGame {
    /**
     * Returns a list of all the rooms in the world og zuul game. These have a height and width,
     * they contain shelves with items, and they are connected with warp objects
     * @return the rooms in the world of zuul game
     */
    ArrayList<IRoom> getRooms();

    /**
     * Returns an object of a class that implements the IPlayer interface
     * This has an inventory, and can return summes values from the inventory
     * (price, calories, protein). It also holds a reference to the starting room
     * and the players initial position in this room
     * @return An object that implements IPlayer
     */
    IPlayer getPlayer();

    boolean take(IItem item);

    void drop(IItem item);

    CheckoutReturnObject Checkout();

    void printWelcome();

    String getPlayerDescription();

    void deleteSaveFile();

    /**
     * Allows us to set the playerType, so the IPlayers getSprite() method returns the correct sprite for that playerType.
     * @param playerType String of the playerType, i.e. Student
     */
    void setPlayerType(String playerType);

    PlayerType getPlayerType();
}
