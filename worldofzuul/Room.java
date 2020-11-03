package worldofzuul;

import java.util.*;

public class Room {

    private final String description;
    private final ArrayList<Item> items;
    private final HashMap<String, Room> exits;
    private final boolean canCheckout;

    public Room(String description, boolean canCheckout) {
        this.description = description;
        this.items = new ArrayList<>();
        this.exits = new HashMap<>();
        this.canCheckout = canCheckout;
    }
    
    public Room(String description, boolean canCheckout,ArrayList<Item> items) {
        this.description = description;
        this.items = items;
        this.exits = new HashMap<>();
        this.canCheckout = canCheckout;
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public boolean canCheckout(){ //boolean to verify if it's possible to checkout in the current room.
        return canCheckout;
    }

    public void setItems(Item[] items) {
        this.items.clear();
        this.items.addAll(Arrays.asList(items));
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

    private String getExitString() {
        StringBuilder returnString = new StringBuilder("Exits:");
        Set<String> keys = exits.keySet();
        for (String exit : keys) {
            returnString.append(" ").append(exit);
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
        if(items.size() == 0){  // If the size of the list with items in the current room is 0,
            return null;          // the 'Available products' string will not be printed
        }
        StringBuilder itemsString = new StringBuilder();
        
        itemsString.append("Available products: ");
        for(Item item : items){
            itemsString.append("- ").append(item.getName()).append("\n");
        }
        return itemsString.toString();
    }
}
