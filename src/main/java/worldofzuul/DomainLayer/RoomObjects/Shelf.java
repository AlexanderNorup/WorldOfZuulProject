package worldofzuul.DomainLayer.RoomObjects;

import worldofzuul.DomainLayer.Interfaces.IItem;
import worldofzuul.DomainLayer.Interfaces.IShelf;
import worldofzuul.DomainLayer.Interfaces.IRoomObject;

import java.util.ArrayList;

public class Shelf implements IShelf {
    private final int x;
    private final int y;
    ArrayList<IItem> items;

    public Shelf(int x, int y, ArrayList<IItem> items) {
        this.x = x;
        this.y = y;
        this.items = items;
    }

    public ArrayList<IItem> getItems() {
        return items;
    }

    @Override
    public int getXPosition() {
        return x;
    }

    @Override
    public int getYPosition() {
        return y;
    }
}
