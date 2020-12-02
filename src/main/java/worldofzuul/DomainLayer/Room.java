package worldofzuul.DomainLayer;

import worldofzuul.DomainLayer.Interfaces.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Has a description of itself and keeps track of the items in the room (if any) as well as which rooms
 * can be accessed in which direction (North, South, East, West) from this room
 */
public class Room implements IRoom {

    private final String description;
    private final HashMap<String, Room> exits;

    private final ArrayList<IShelf> shelves;
    private final ArrayList<IWarp> warps;
    private final ArrayList<ICashier> cashiers;

    private final int roomWidth,roomHeight;
    private final String background;
    public Room(String description, int roomWidth, int roomHeight, String background) {
        this.description = description;
        this.shelves = new ArrayList<>();
        this.warps = new ArrayList<>();
        this.exits = new HashMap<>();
        this.cashiers = new ArrayList<>();

        this.roomHeight = roomHeight;
        this.roomWidth = roomWidth;
        this.background = background;
    }

    public void addShelves(ArrayList<IShelf> shelves){
        this.shelves.addAll(shelves);
    }

    public void addWarp(int startX, int startY, IRoom destination, int destX, int destY){
        this.warps.add(new Warp(startX, startY, destination, destX, destY));
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public void addCashier(int xPosition, int yPosition){
        cashiers.add(new Cashier(xPosition, yPosition));
    }

    public boolean canCheckout(){
        return this.cashiers.size() > 0;
    }

    public ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<>();
        for(IShelf s : this.shelves){
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
    public ArrayList<IShelf> getShelves() {
        return new ArrayList<>(this.shelves);
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
    public ArrayList<IWarp> getWarps() {
        return this.warps;
    }

    @Override
    public ArrayList<ICashier> getCashiers(){
        return this.cashiers;
    }

    @Override
    public String getBackground() {
        return this.background;
    }
}
