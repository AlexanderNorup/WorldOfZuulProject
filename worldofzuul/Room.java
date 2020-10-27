package worldofzuul;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

 */
public class Room {
    private String description;
    private HashMap<String, Room> exits;
    private ArrayList<Item> items;

    public Room(String description) {
        this.description = description;
        exits = new HashMap<String, Room>();
        items = new ArrayList<>();
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
        return "You are " + description + ".\n" + getExitString();
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
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for (String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }
}

