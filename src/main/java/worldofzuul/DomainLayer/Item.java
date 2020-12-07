package worldofzuul.DomainLayer;

import worldofzuul.DomainLayer.Interfaces.IItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for creating objects representing an item
 * Contains getters and overridden toString and equals methods
 * Has two static methods which take an ArrayList as parameter for getting a
 * String of the items in the list or getting the total price of the items in the list
 */
public class Item implements IItem {
    private final String name;
    private final double price;
    private final double co2;
    private final double protein;
    private final double calories;
    private final ArrayList<Extra> extra;
    
    public Item(String name, double price, double co2, double protein, double calories, List<Extra> extra) {
        this.name = name;
        this.price = price;
        this.co2 = co2;
        this.protein = protein;
        this.calories = calories;
        this.extra = new ArrayList<>(extra);
    }

    public Item(String name, double price, double co2, double protein, double calories, Extra[] extra) {
        this(name,price,co2,protein,calories,Arrays.asList(extra));
    }

    /**
     * @return item name, price, calories, protein and any extras
     */
    @Override
    public String toString() {
        return String.format("DKK%6.2f %s", price, name);
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

    public String getDescription(){
        StringBuilder extrasString = new StringBuilder();
        for (Extra extra: extra){
            extrasString.append(extra.toString());
            extrasString.append(", ");
        }
        if(extra.size() > 0) {
            extrasString.delete(extrasString.length() - 2, extrasString.length());
        }

        return "This is " + name + ". it costs DKK " + price + ". It contains " + calories + " kcal and " + protein + " g of protein" + "\n" + extrasString.toString();
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

    public ArrayList<Extra> getExtra() {
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
