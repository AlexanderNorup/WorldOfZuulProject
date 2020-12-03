package worldofzuul.DomainLayer;

import worldofzuul.DomainLayer.Interfaces.*;
import worldofzuul.DomainLayer.RoomObjects.Cashier;
import worldofzuul.DomainLayer.RoomObjects.Warp;

import java.util.*;

/**
 * Has a description of itself and keeps track of the items in the room (if any) as well as which rooms
 * can be accessed in which direction (North, South, East, West) from this room
 */
public class Room implements IRoom {

    private final String description;
    private final HashMap<String, Room> exits;

    private final ArrayList<IRoomObject> roomObjects;

    private final int roomWidth,roomHeight;
    private final String background;
    public Room(String description, int roomWidth, int roomHeight, String background) {
        this.description = description;
        this.roomObjects = new ArrayList<>();
        this.exits = new HashMap<>();

        this.roomHeight = roomHeight;
        this.roomWidth = roomWidth;
        this.background = background;
    }

    public void addRoomObject(ArrayList<IRoomObject> objects){
        roomObjects.addAll(objects);
    }
    public void addRoomObject(IRoomObject... objects){
        roomObjects.addAll(Arrays.asList(objects));
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public boolean canCheckout(){
        for(IRoomObject object : roomObjects){
            if(object instanceof ICashier){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<>();
        for(IRoomObject object : this.roomObjects){
            if(object instanceof IShelf){
                for(IItem i : ((IShelf) object).getItems()){
                    if(i instanceof Item){
                        items.add((Item) i);
                    }
                }
            }
        }
        return items;
    }

    /**
     * @return String "You are #name of room#" followed by a list of items
     */
    public String getDescription() {
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
        for(IRoomObject object : roomObjects){
            if(object instanceof IShelf){
                return "Available products: \n" + Item.getListString(this.getItems());
            }
        }
        return null;
    }

    //The interfaces.
    @Override
    public int getWidth() {
        return this.roomWidth;
    }

    @Override
    public int getHeight() {
        return this.roomHeight;
    }

    @Override
    public ArrayList<IRoomObject> getObjects() {
        return new ArrayList<>(this.roomObjects);
    }


    @Override
    public String getBackground() {
        return this.background;
    }
}
