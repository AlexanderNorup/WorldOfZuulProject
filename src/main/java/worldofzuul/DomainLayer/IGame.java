package worldofzuul.DomainLayer;

import javafx.scene.layout.Background;

import java.net.URI;
import java.util.ArrayList;

public interface IGame {

    /**
     *
     * @return the list of rooms for the presentationLayer.
     */
    public ArrayList<Room> getRooms();

    /**
     *
     * @return the start room when the game is loaded. Which is
     * outside.
     */
    public Room roomStart();








}
