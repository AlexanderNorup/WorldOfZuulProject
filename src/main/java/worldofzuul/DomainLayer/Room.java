package worldofzuul.DomainLayer;

import worldofzuul.DomainLayer.Interfaces.IItem;
import worldofzuul.DomainLayer.Interfaces.IRoom;
import worldofzuul.DomainLayer.Interfaces.Shelf;
import worldofzuul.DomainLayer.Interfaces.Warp;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

/**
 * Has a description of itself and keeps track of the items in the room (if any) as well as which rooms
 * can be accessed in which direction (North, South, East, West) from this room
 */
public class Room implements IRoom {

    private final String description;
    private final HashMap<String, Room> exits;
    private boolean canCheckout;

    private final ArrayList<Shelf> shelves;
    private final ArrayList<Warp> warps;

    private final int roomWidth,roomHeight;
    private final String background;
    public Room(String description, int roomWidth, int roomHeight, String background) {
        this.description = description;
        this.shelves = new ArrayList<>();
        this.warps = new ArrayList<>();
        this.exits = new HashMap<>();
        this.canCheckout = false;

        this.roomHeight = roomHeight;
        this.roomWidth = roomWidth;
        this.background = background;
    }

    public void addShelves(ArrayList<Shelf> shelves){
        this.shelves.addAll(shelves);
    }

    public void addWarp(int startX, int startY, IRoom destination, int destX, int destY){
        this.warps.add(new Warp(startX, startY, destination, destX, destY));
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public void setCanCheckout(boolean checkout){
        this.canCheckout = checkout;
    }

    public boolean canCheckout(){ //boolean to verify if it's possible to checkout in the current room.
        return canCheckout;
    }


    public ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<>();
        for(Shelf s : this.shelves){
            for(IItem i : s.getItems()){
                if(i instanceof Item){
                    items.add((Item) i);
                }
            }
        }
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
        for(Item currentItem : this.getItems()){
            if(currentItem.getName().equalsIgnoreCase(name)){
                item = currentItem;
            }
        }
        return item;
    }


    /**
     * @return "Available products" followed by a list of items each in the format "kr #price#  #item name#
     */
    public String getItemsString(){
        if(this.shelves.size() == 0){  // If the size of the list with items in the current room is 0,
            return null;          // the 'Available products' string will not be printed
        }

        return "Available products: \n" + Item.getListString(this.getItems());
    }

    //The interfaces.
    @Override
    public ArrayList<Shelf> getShelves() {
        return this.shelves;
    }

    @Override
    public int getWidth() {
        return this.roomWidth;
    }

    @Override
    public int getHeight() {
        return this.roomHeight;
    }

    @Override
    public ArrayList<Warp> getWarps() {
        return this.warps;
    }

    @Override
    public String getBackground() {
        return this.background;
    }
}
