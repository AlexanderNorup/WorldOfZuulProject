package worldofzuul;

import java.util.ArrayList;
import java.util.Arrays;

public class Item {
    private String name;
    private double price;
    private double co2;
    private double protein;
    private double calories;
    private Extra[] extra;

    public static void main(String[] args) {
        Item beef = new Item("Beef", 19.95, 10, 20, 150, new Extra[]{});
        System.out.println(beef);
    }

    public Item(String name, double price, double co2, double protein, double calories, Extra[] extra) {
        this.name = name;
        this.price = price;
        this.co2 = co2;
        this.protein = protein;
        this.calories = calories;
        this.extra = extra;
    }


    @Override
    public String toString() {
        //TODO should print extras as well
        return name + ": price: " + price + "; calories: " + calories + "; protein: " + protein + ";";
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

    public Extra[] getExtra() {
        return extra;
    }
}
