package worldofzuul;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

public class Room {

    private final String description;
    private final ArrayList<Item> items;
    private final HashMap<String, Room> exits;

    public Room(String description) {
        this.description = description;
        this.items = new ArrayList<>();
        this.exits = new HashMap<>();
    }
    
    public Room(String description, ArrayList<Item> items) {
        this.description = description;
        this.items = items;
        this.exits = new HashMap<>();
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public void setItems(Item[] items) {
        this.items.clear();
        for (Item i: items) {
            this.items.add(i);
        }
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public String getShortDescription() {
        return description;
    }

    public String getLongDescription() {
        return "You are " + description + ".\n" + getItemsString() + ".\n" + getExitString() + ".\n";
    }

    public String getItemsString() {
        StringBuilder str = new StringBuilder();
        for (Item i: items) {
            str.append(i.getName());
            str.append(", ");
        }

        //Removes the last comma and space
        str.delete(str.length()-2, str.length());

        return str.toString();
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
            if(currentItem.getName().equalsIgnoreCase(name)){
                item = currentItem;
            }
        }
        return item;
    }

    public void addItem(Item item){
        items.add(item);
    }

    public void removeItem(Item item){items.remove(item);}

    public String getItemsString(){
        StringBuilder itemsString = new StringBuilder();
        
        itemsString.append("Available products: ");
        for(Item item : items){
            itemsString.append("- "+item.getName() + "\n");
        }
        return itemsString.toString();
    }
}
