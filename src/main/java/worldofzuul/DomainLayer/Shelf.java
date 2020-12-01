package worldofzuul.DomainLayer;

import worldofzuul.DomainLayer.Interfaces.IItem;
import worldofzuul.DomainLayer.Interfaces.IShelf;

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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ArrayList<IItem> getItems() {
        return items;
    }
}
