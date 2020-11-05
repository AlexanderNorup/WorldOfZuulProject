package worldofzuul;

import java.util.ArrayList;

/**
 * Intended to be instantiated once. keeps various data about the player
 * Responsible for keeping track of inventory
 * This is also the class responsible for communicating with the playerType class
 */
public class Player {

    private final ArrayList<Item> inventory;
    private PlayerType type;

    public Player(PlayerType playerType) {
        this.inventory = new ArrayList<>();
        this.type = playerType;
    }

    public void addItem(Item item) {
        inventory.add(item);
        //TODO: Discuss whether or not this method should return a boolean.
    }

    /**
     * @param string name of the item
     * @return the item with the specified name; returns null if no such item exists
     */
    public Item getItem(String string) {
        Item item = null;
        for (Item currentItem : inventory) {
            if (currentItem.getName().equalsIgnoreCase(string)) {

                item = currentItem;
            }
        }
        return item;
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

        Item item = null;
        for (Item i : inventory) {
            if (itemName.equalsIgnoreCase(i.getName())) {
                item = i;
                break;
            }
        }

        return item != null ? item.toString() : "";
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
        for (Item item : inventory) {
            totalC02 += item.getCo2();
        }

        return new GameResult(totalC02, type.getHappiness(inventory), type);
    }

    /**
     * @return String of the total price, calories and protein for this game
     */
    public String getSummedValuesString() {
        double totalPrice = 0.0;
        double totalCalories = 0.0;
        double totalProtein = 0.0;

        for (Item item : inventory) {
            totalPrice += item.getPrice();
            totalCalories += item.getCalories();
            totalProtein += item.getProtein();
        }

        return "Happiness: " + type.getHappiness(inventory) + "Total Price: " + totalPrice + " | " + "Total Calories: " + totalCalories + " | " + "Total Protein: " + totalProtein;
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
