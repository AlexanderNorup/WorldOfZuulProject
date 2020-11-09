package worldofzuul;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Item {
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
        this.extra = (ArrayList<Extra>) extra;
    }

    public Item(String name, double price, double co2, double protein, double calories, Extra[] extra) {
        this(name,price,co2,protein,calories,Arrays.asList(extra));
    }


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

    public ArrayList<Extra> getExtra() {
        return extra;
    }

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

    public static Double getListValue(ArrayList<Item> list) {
        double totalValue = 0.0;
        for (Item item : list) {
            totalValue += item.getPrice();
        }
        return totalValue;
    }
}
