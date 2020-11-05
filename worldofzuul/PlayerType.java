package worldofzuul;

import java.util.ArrayList;
import java.util.Arrays;

public class PlayerType {

    private String name;
    private String description;
    private double budgetMax;
    private double calorieMin;
    private double calorieGoal;
    private double proteinFactor;
    private double budgetFactor;
    private double pickynessfactor;
    private ArrayList<Item> faveItems;
    private ArrayList<Item> hateItems;
    private ArrayList<Extra> thingsThatMatter;

    public PlayerType(String name, String description) {
        this.name = name;
        this.faveItems = new ArrayList<>();
        this.hateItems = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDescription(){
        return description;
    }

    public double getCalorieMin() {
        return calorieMin;
    }

    public double getBudgetMax() {
        return budgetMax;
    }

    public void setlimitations(int budgetMax, int calorieMin){
        this.budgetMax = budgetMax;
        this.calorieMin = calorieMin;
    }

    //set factors to determin happiness caluclation for playertype
    public void setFactors(int proteinFactor, int budgetFactor, int pickynessFactor){
        int equalizer = 100/(proteinFactor + budgetFactor + pickynessFactor);
        this.proteinFactor = proteinFactor * equalizer;
        this.budgetFactor = budgetFactor * equalizer;
        this.pickynessfactor = pickynessFactor * equalizer;
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
        int faveItemsBought = 0;
        int hateItemsBaught = 0;
        int percentItemsContainingExtras = 0;
        double variety = 0;
        ArrayList<Item> itemTypeList = new ArrayList<>();

        for (Item item : items) {

            // Sums values
            totalPrice += item.getPrice();
            totalCalories += item.getCalories();
            totalProtein += item.getProtein();

            // Calculates satisfaction based on favorite and hated items
            if (faveItems.contains(item)) {
                faveItemsBought += 1;
            }
            if (hateItems.contains(item)) {
                hateItemsBaught += 1;
            }

            //check types of items in inventory
            if(!itemTypeList.contains(item)){
                itemTypeList.add(item);
                variety += 1;
            }

            for(Extra extra : thingsThatMatter){
                percentItemsContainingExtras += (Arrays.asList(item.getExtra()).contains(extra) ? 1 : 0) / thingsThatMatter.size();
            }
        }

        //Calculate budget happiness ( max 80)
        if(totalPrice < budgetMax*0.8){
            happiness += 80 * (1 - totalPrice/budgetMax*0.8);
        }else {
            happiness -= 80 * totalPrice/budgetMax*0.8;
        }

        //Calculate protein happiness (max +80)
        double minimumProtein = calorieGoal * 0.15 / 4;
        double proteinGoal = calorieGoal * 0.15 / 4;
        int tempHappiness = 0;

        if(totalProtein > minimumProtein){
            tempHappiness += 80 * (totalProtein-minimumProtein)/(proteinGoal-minimumProtein);
        }else {
            tempHappiness -= 80 * (1 - (totalProtein)/(minimumProtein));
        }
        happiness += Math.max(tempHappiness, 80);



        //calculate pickyness
        double temp1 = (double) faveItemsBought / (double) faveItems.size();
        double temp2 = (double) hateItemsBaught / (double) hateItems.size();
        double temp3 = (temp1+(percentItemsContainingExtras/(double) items.size()))/2;

        happiness += 80 * temp3 * pickynessfactor;
        happiness += 80 * temp2 * pickynessfactor;

        //generic happiness
        //Calorie, variaty
        happiness -= (1 - (totalCalories - calorieMin) / (calorieGoal - calorieMin)) * 10;

        if(variety > 5){
            happiness += Math.max((variety - 5) * 2,10);
        }else {
            happiness -= (variety - 5) * 2;
        }

        return happiness;
    }



}
