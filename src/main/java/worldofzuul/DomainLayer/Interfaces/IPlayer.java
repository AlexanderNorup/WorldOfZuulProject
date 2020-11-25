package worldofzuul.DomainLayer.Interfaces;

import java.util.ArrayList;

public interface IPlayer {
    ArrayList<IItem> getInventory();

    int getStartingX();
    int getStartingY();

    double getInventoryValue();
    double getBudget();
    double getInventoryProtein();
    double getInventoryCalories();

    IRoom getStartingRoom();
}
