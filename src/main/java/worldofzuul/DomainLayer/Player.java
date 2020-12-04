package worldofzuul.DomainLayer;

import worldofzuul.DomainLayer.Interfaces.IItem;
import worldofzuul.DomainLayer.Interfaces.IPlayer;
import worldofzuul.DomainLayer.Interfaces.IRoom;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Intended to be instantiated once. keeps various data about the player
 * Responsible for keeping track of inventory
 * This is also the class responsible for communicating with the playerType class
 */
public class Player implements IPlayer {

    private final ArrayList<Item> inventory;
    private PlayerType type;
    private IRoom startingRoom;

    public Player(PlayerType playerType, IRoom startingRoom) {
        this.inventory = new ArrayList<>();
        this.type = playerType;
        this.startingRoom = startingRoom;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    @Override
    public ArrayList<IItem> getInventory() {
        //can't pass ArrayList<Item> when ArrayList<IItem> is needed
        //so this workaround is necessary
        return new ArrayList<>(inventory);
    }

    @Override
    public int getStartingX() {
        return 2;
    }

    @Override
    public int getStartingY() {
        return 4;
    }

    @Override
    public double getInventoryValue() {
        return Item.getListValue(inventory);
    }

    @Override
    public double getBudget() {
        return type.getBudgetMax();
    }

    @Override
    public double getInventoryProtein() {
        double totalProtein = 0.0;

        for (Item item : inventory) {
            totalProtein += item.getProtein();
        }

        return totalProtein;
    }

    public double getInventoryCalories() {
        double totalCalories = 0.0;

        for (Item item : inventory) {
            totalCalories += item.getCalories();
        }

        return totalCalories;
    }

    @Override
    public IRoom getStartingRoom() {
        return this.startingRoom;
    }

    @Override
    public String getDescription() {
        return getPlayerType().getDescription();
    }

    @Override
    public String getSprite() {
        return getPlayerType().getPlayerSprite();
    }

    /**
     * @param string name of the item
     * @return the item with the specified name; returns null if no such item exists
     */
    public Item getItem(String string) {
        for (Item currentItem : inventory) {
            if (currentItem.getName().equalsIgnoreCase(string)) {
                return currentItem;
            }
        }
        return null;
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }

    /**
     * @param itemName
     * @return String containing the items name, price, calories and protein as well as any extras (such as 'organic' or 'contains gluten')
     */
    public String getItemValuesString(String itemName) {
        //We could do this as a lambda. But it's way better.
        //Item item = inventory.stream().filter(itemTemp -> itemTemp.getName().equals(itemName)).findFirst().orElse(null);

        for (Item i : inventory) {
            if (itemName.equalsIgnoreCase(i.getName())) {
                return i.toString();
            }
        }
        return "";
    }

    public void deleteInventory() {
        inventory.clear();
    }

    /**
     * @return "Products in inventory" followed by a list of items each in the format "kr #price#  #item name#
     */
    public String getInventoryString() {
        if (inventory.size() == 0) {  // If the size of the list with items in the inventory is 0,
            return null;          // the 'Available products' string will not be printed
        }

        return "Products in inventory: \n" + Item.getListString(inventory) + "Total value of inventory: kr " + Item.getListValue(inventory);
    }

    /**
     * @return an instance of the GameResult class representing the current game
     */
    public GameResult getGameResult() {
        double totalC02 = 0.0;
        ArrayList<String> itemsBought = new ArrayList<>();
        for (Item item : inventory) {
            totalC02 += item.getCo2();
            itemsBought.add(item.getName());
        }

        return new GameResult(totalC02, type.getHappiness(inventory), type, itemsBought);
    }

    /**
     * @return String of the total price, calories and protein for this game
     */
    public String getSummedValuesString() {
        return "Happiness: " + String.format(Locale.US, "%7.2f",  type.getHappiness(inventory)) + " | Total Price: " + getInventoryValue() + " | " + "Total Calories: " + getInventoryCalories() + " | " + "Total Protein: " + getInventoryProtein();
    }


    public void setPlayerType(PlayerType type) {
        this.type = type;
    }

    public PlayerType getPlayerType() {
        return type;
    }

    public boolean underBudget(){
        double totalPrice = 0.0;

        for (Item item : inventory) {
            totalPrice += item.getPrice();
        }

        return totalPrice <= type.getBudgetMax();
    }

    public boolean overMinCalories(){
        double totalCalories = 0.0;

        for (Item item : inventory) {
            totalCalories += item.getCalories();
        }

        return totalCalories >= type.getCalorieMin();
    }
}
