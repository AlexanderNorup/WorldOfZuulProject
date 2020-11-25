package worldofzuul.DomainLayer.Interfaces;

import java.util.ArrayList;

public interface IGame {
    ArrayList<IRoom> getRooms();

    IPlayer getPlayer();

    String doAction(String firstWord, String secondWord);
}
