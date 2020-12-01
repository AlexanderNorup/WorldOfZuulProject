package worldofzuul.DomainLayer.Interfaces;

import java.util.ArrayList;

public interface IShelf {
    int getX();
    int getY();
    ArrayList<IItem> getItems();
}
