package worldofzuul.PresentationLayer.GridObjects;

import worldofzuul.DomainLayer.Interfaces.IItem;
import worldofzuul.DomainLayer.Item;

import java.util.ArrayList;
import java.util.Arrays;

public class Shelf extends GridObject {

    private ArrayList<IItem> items;

    public Shelf(ArrayList<IItem> items){
        this.items = items;
    }

    public Shelf(){
        this.items = new ArrayList<>();
    }

    public void addItems(IItem... items){
        this.items.addAll(Arrays.asList(items));
    }

    public ArrayList<IItem> getItems(){
        return this.items;
    }

}
