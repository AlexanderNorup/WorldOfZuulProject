package worldofzuul.PresentationLayer.GridObjects;

import worldofzuul.DomainLayer.Item;

import java.util.ArrayList;
import java.util.Arrays;

public class Shelf extends GridObject {

    private ArrayList<Item> items;

    public Shelf(ArrayList<Item> items){
        this.items = items;
    }

    public Shelf(){
        this.items = new ArrayList<>();
    }

    public void addItems(Item... items){
        this.items.addAll(Arrays.asList(items));
    }

    public ArrayList<Item> getItems(){
        return this.items;
    }

}
