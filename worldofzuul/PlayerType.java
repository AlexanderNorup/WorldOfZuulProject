package worldofzuul;

import java.util.ArrayList;

/**
 * Keeps all the properties for a playerType (Student, Bodybuilder etc)
 * Calculates the players happiness based on the bought items
 */
public class PlayerType {

    private String name;
    private double proteinGoal;
    private double calorieMin;
    private double calorieGoal;
    private double budgetMax;
    private double budgetFactor;
    private double pickiness;
    private ArrayList<Item> faveItems;
    private ArrayList<Item> hateItems;
    private ArrayList<Extra> thingsThatMatter;

    public PlayerType(String name) {
        this.name = name;
        this.faveItems = new ArrayList<>();
        this.hateItems = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public double getCalorieMin() {
        return calorieMin;
    }

    public double getBudgetMax() {
        return budgetMax;
    }

    public void setProteinFactor(double proteinGoal) { this.proteinGoal = proteinGoal; }

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
        double happiness = 0;

        double totalPrice = 0.0;
        double totalCalories = 0.0;
        double totalProtein = 0.0;
        int itemsSatisfaction = 0;
        ArrayList<Item> varietyList = new ArrayList<>();
        double variety = 10;

        for (Item item : items) {

            // Sums values
            totalPrice += item.getPrice();
            totalCalories += item.getCalories();
            totalProtein += item.getProtein();

            // Calculates satisfaction based on favorite and hated items
            if (faveItems.contains(item)) {
                itemsSatisfaction -= 1;
            }
            if (hateItems.contains(item)) {
                itemsSatisfaction -= 2;
            }

            // calculates variety
            if (varietyList.contains(item)) {
                variety /= 1.5;
            } else {
                varietyList.add(item);
            }
        }

        // will add happiness if over proteinMin and subtract happiness if under proteinMin
        happiness += ((totalProtein/proteinGoal)-1)*30;
        happiness += ((totalCalories/calorieGoal)-1)*30;
        happiness += itemsSatisfaction*pickiness;
        happiness += (variety-5);

        return 0;
    }



}
