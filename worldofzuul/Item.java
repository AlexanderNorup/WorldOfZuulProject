package worldofzuul;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class for creating objects representing an item
 * Contains getters and overridden toString and equals methods
 * Has two static methods which take an ArrayList as parameter for getting a
 * String of the items in the list or getting the total price of the items in the list
 */
public class Item {
    private String name;
    private double price;
    private double co2;
    private double protein;
    private double calories;
    private Extra[] extra;
    
    public Item(String name, double price, double co2, double protein, double calories, Extra[] extra) {
        this.name = name;
        this.price = price;
        this.co2 = co2;
        this.protein = protein;
        this.calories = calories;
        this.extra = extra;
    }

    /**
     * @return item name, price, calories, protein and any extras
     */
    @Override
    public String toString() {
        StringBuilder extrasString = new StringBuilder();
        for (Extra extra: extra){
            extrasString.append(extra.toString());
            extrasString.append(", ");
        }
        extrasString.delete(extrasString.length()-2, extrasString.length());

        return name + ": price: kr " + price + "; calories: " + calories + " kcal; protein: " + protein + " g;" + "\n" + extrasString.toString();
    }

    /**
     * @param obj
     * @return True if the items has the same name; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Item){
            return this.name.equals(((Item) obj).getName());
        }else {
            return false;
        }
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getCo2() {
        return co2;
    }

    public double getProtein() {
        return protein;
    }

    public double getCalories() {
        return calories;
    }

    public Extra[] getExtra() {
        return extra;
    }

    /**
     * @param list List of items
     * @return string representation of the item list. including prices and names
     */
    public static String getListString(ArrayList<Item> list){
        StringBuilder itemsString = new StringBuilder();
        for (Item item : list) {
            itemsString.append("- ")
                    .append("kr ")
                    .append(String.format("%6.2f", item.getPrice()))
                    .append("   ")
                    .append(item.getName())
                    .append("\n");
        }
        return itemsString.toString();
    }

    /**
     * @param list list of items
     * @return summed prices of all the items
     */
    public static Double getListValue(ArrayList<Item> list) {
        double totalValue = 0.0;
        for (Item item : list) {
            totalValue += item.getPrice();
        }
        return totalValue;
    }
}
