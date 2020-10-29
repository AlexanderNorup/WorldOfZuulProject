package worldofzuul;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;


public class Room {
    private final String description;
    private final ArrayList<Item> items;
    private final HashMap<String, Room> exits = new HashMap<>();

    public Room(String description, ArrayList<Item> items) {
        this.description = description;
        this.items = items;
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public String getShortDescription() {
        return description;
    }

    public String getLongDescription() {
        return "You are " + description + ".\n" + getItemsString() + ".\n" + getExitString() + ".\n";
    }

    private String getExitString() {
        StringBuilder returnString = new StringBuilder("Exits:");
        Set<String> keys = exits.keySet();
        for (String exit : keys) {
            returnString.append(" "+exit);
        }
        return returnString.toString();
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    public Item getItem(String name){
        Item item = null;
        for(Item currentItem : items){
            if(currentItem.getName().equals(name)){
                item = currentItem;
            }
        }
        return item;
    }

    public void setItem(Item item){
        items.add(item);
    }

    public void removeItem(Item item){items.remove(item);}

    public String getItemsString(){
        StringBuilder itemsString = new StringBuilder();
        itemsString.append("Available products: ");
        for(Item item : items){
            itemsString.append(item.getName());
            itemsString.append(", ");
        }
        return itemsString.toString();
    }
}
