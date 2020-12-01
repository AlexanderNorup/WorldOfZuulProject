package worldofzuul.DomainLayer.Interfaces;

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

    /**
     * roughly speaking, this lets a gui act as the Parser from the world of zuul game
     * Sending commands to the Game class and getting the appropriate responses
     * @param firstWord String describing the action to be done e.g. "take", "drop", "inspect"
     * @param secondWord String describing details of the action. could be the item to take or drop
     * @return a String which is a response to the action e.g. "you picked up 200g Salami"
     */
    String doAction(String firstWord, String secondWord);

    void setPlayerType(String playerType);
}
