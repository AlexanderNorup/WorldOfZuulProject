package worldofzuul.DomainLayer.Interfaces;

import java.util.ArrayList;

public interface IShelf extends IRoomObject{

    ArrayList<IItem> getItems();
}
