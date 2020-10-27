package worldofzuul;

import java.util.ArrayList;

public class Player {

    private ArrayList<Item> inventory;
    private Playertype type;

    public Player(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

    public boolean addItem(Item item){
        inventory.add(item);

    }

    public void removeItem(Item item){
        inventory.remove(item);
    }

    public String getItemValuesString(String itemName){
        if(itemName.equals(Item item.getname))

        System.out.println(item);

    }

    public String getInventoryString(){
        for(int i=0;i<inventory.size(); i++){
            System.out.println(inventory(item.getName));
        }

    }
}
