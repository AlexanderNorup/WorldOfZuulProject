package worldofzuul;

import java.util.ArrayList;

public class Player {

    private final ArrayList<Item> inventory;
    private PlayerType type;

    public Player(PlayerType playerType) {
        this.inventory = new ArrayList<>();
        this.type = playerType;
    }

    public void addItem(Item item){
        inventory.add(item);
        //TODO: Discuss whether or not this method should return a boolean.
    }

    public Item getItem(String string){
        Item item = null;
        for(Item currentItem : inventory){
            if (currentItem.getName().equalsIgnoreCase(string)){

                item = currentItem;
            }
        }
        return item;
    }

    public void removeItem(Item item){
        inventory.remove(item);
    }

    public String getItemValuesString(String itemName){
        //We could do this as a lambda. But it's way better.
        //Item item = inventory.stream().filter(itemTemp -> itemTemp.getName().equals(itemName)).findFirst().orElse(null);

        Item item = null;
        for(Item i: inventory){
            if(itemName.equalsIgnoreCase(i.getName())){
                item=i;
                break;
            }
        }

        return item != null ? item.toString() : "";
    }

    public void deleteInventory(){
       inventory.clear();

    }

    public String getInventoryString(){
        if(inventory.size() == 0){  // If the size of the list with items in the inventory is 0,
            return null;          // the 'Available products' string will not be printed
        }
        StringBuilder itemsString = new StringBuilder();

        itemsString.append("Available products: ");
        for(Item item : inventory){
            itemsString.append("- ").append(item.getName()).append("\n");
        }
        return itemsString.toString();
    }

    public GameResult getGameResult(){
        double totalC02 = 0.0;
        for(Item item: inventory) {
            totalC02 += item.getCo2();
        }

        return new GameResult(totalC02, type.getHappiness(inventory), type);
    }

    public String getSummedValues(){
        double totalPrice = 0.0;
        double totalCalories = 0.0;
        double totalProtein = 0.0;

        for(Item item: inventory){
            totalPrice += item.getPrice();
            totalCalories += item.getCalories();
            totalProtein += item.getProtein();
        }

        return "Total Price: " + totalPrice + " | " + "Total Calories: " + totalCalories + " | " + "Total Protein: " + totalProtein;
    }

    public void setPlayerType(PlayerType type){
            this.type = type;
    }

    public PlayerType getPlayerType(){
        return this.type;
    }

}
