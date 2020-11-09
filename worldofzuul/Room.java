package worldofzuul;

import java.util.*;

/**
 * Has a description of itself and keeps track of the items in the room (if any) as well as which rooms
 * can be accessed in which direction (North, South, East, West) from this room
 */
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

    /**
     * @return String "You are #name of room#" followed by a list of items
     */
    public String getLongDescription() {
        String itemString = getItemsString();
        itemString = itemString == null ? "" : itemString + "\n";
        return "You are " + description + ".\n" + itemString + getExitString() + ".\n";
    }

    /**
     * @return String in the format "Exits: " followed by the directions in which exits exist (eg. east, south, west)
     */
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

    /**
     * @param name name of the item
     * @return the item with the specified name; null if there is no item with the specified name.
     */
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

    /**
     * @return "Available products" followed by a list of items each in the format "kr #price#  #item name#
     */
    public String getItemsString(){
        if(items.size() == 0){  // If the size of the list with items in the current room is 0,
            return null;          // the 'Available products' string will not be printed
        }

        return "Available products: \n" + Item.getListString(items);
    }
}
