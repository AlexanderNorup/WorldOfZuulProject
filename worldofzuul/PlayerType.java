package worldofzuul;

import java.util.ArrayList;

public class PlayerType {

    private String name;
    private double proteinFactor;
    private double budgetFactor;
    private double proteinMin;
    private double calorieMin;
    private double budgetMax;
    private double pickiness;
    private ArrayList<Item> faveItems;
    private ArrayList<Item> hateItems;
    private ArrayList<Extra> thingsThatMatter;

    public PlayerType(String name, double proteinMin, double calorieMin, double budgetMax) {
        this.name = name;
        this.proteinMin = proteinMin;
        this.calorieMin = calorieMin;
        this.budgetMax = budgetMax;
    }

    public String getName() {
        return name;
    }

    public double getProteinMin() {
        return proteinMin;
    }

    public double getCalorieMin() {
        return calorieMin;
    }

    public double getBudgetMax() {
        return budgetMax;
    }

    public void setProteinFactor(double proteinFactor) {
        this.proteinFactor = proteinFactor;
    }

    public void setBudgetFactor(double budgetFactor) {
        this.budgetFactor = budgetFactor;
    }

    public void setPickiness(double pickiness) {
        this.pickiness = pickiness;
    }

    public void addFaveItems(Item faveItem) {
        faveItems.add(faveItem);
    }

    public void addHateItems(Item hateItem) {
        hateItems.add(hateItem);
    }

    public void addThingsThatMatter(Extra thingThatMatter) {
        thingsThatMatter.add(thingThatMatter);
    }

    //calculate happiness
    //TODO finish
    public double getHappiness(ArrayList<Item> items){
        double happiness = 1;

        double totalPrice = 0.0;
        double totalCalories = 0.0;
        double totalProtein = 0.0;
        int itemsSatisfaction = 0;

        for (Item item : items) {
            totalPrice += item.getPrice();
            totalCalories += item.getCalories();
            totalProtein += item.getProtein();
            if (faveItems.contains(item)){
                itemsSatisfaction -= 1;
            }
            if (hateItems.contains(item)) {
                itemsSatisfaction -= 2;
            }
        }

        if (totalCalories>calorieMin) happiness *= 2;
        if (totalProtein>proteinMin) happiness *=2;

        return 0;
    }



}
