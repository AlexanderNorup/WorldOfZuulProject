package worldofzuul;

import java.util.ArrayList;

public class Player {

    private ArrayList<Item> inventory;
    private PlayerType type;

    public Player() {
        this.inventory = new ArrayList<>();
    }

    public boolean addItem(Item item){
        inventory.add(item);
        return true;
        //TODO: Discuss whether or not this method should return a boolean.
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

    public String getInventoryString(){

        StringBuilder inventoryString = new StringBuilder();

        for (Item item: inventory){
           inventoryString.append("- ").append(item.getName()).append("\n");
        }

        return inventoryString.toString();
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

}
