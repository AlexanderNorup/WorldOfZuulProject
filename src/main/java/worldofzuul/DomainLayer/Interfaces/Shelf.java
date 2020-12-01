package worldofzuul.DomainLayer.Interfaces;

import java.util.ArrayList;

public class Shelf {
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
