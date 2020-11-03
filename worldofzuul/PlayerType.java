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
    public double getHappiness(ArrayList<Item> items){

        return 0;
    }



}
