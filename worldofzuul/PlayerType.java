package worldofzuul;

import java.util.ArrayList;
import java.util.Arrays;

public class PlayerType {

    private final String name;
    private final String description;
    private double budgetMax;
    private double calorieMin;
    private double calorieGoal;
    private double proteinFactor;
    private double budgetFactor;
    private double pickynessfactor;
    private final ArrayList<Item> faveItemTypes;
    private final ArrayList<Item> hateItemTypes;
    private final ArrayList<Extra> thingsThatMatter;

    public PlayerType(String name, String description) {
        this.name = name;
        this.description = description;
        this.faveItemTypes = new ArrayList<>();
        this.hateItemTypes = new ArrayList<>();
        thingsThatMatter = new ArrayList<>();
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

    //set concrete values, for limitations and goals
    public void setValues(int budgetMax, int calorieMin, int calorieGoal){
        this.budgetMax = budgetMax;
        this.calorieMin = calorieMin;
        this.calorieGoal = calorieGoal;
    }

    //set factors to determin happiness caluclation for playertype
    public void setFactors(int proteinFactor, int budgetFactor, int pickynessFactor){
        int equalizer = 100/(proteinFactor + budgetFactor + pickynessFactor);
        this.proteinFactor = proteinFactor * equalizer;
        this.budgetFactor = budgetFactor * equalizer;
        this.pickynessfactor = pickynessFactor * equalizer;
    }

    public void addFaveItems(Item faveItem) {
        faveItemTypes.add(faveItem);
    }

    public void addHateItems(Item hateItem) {
        hateItemTypes.add(hateItem);
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
        int faveItemsBought = 0;
        int hateItemsBought = 0;
        int itemsContainingExtras = 0;
        double variety = 0;
        ArrayList<Item> itemTypeList = new ArrayList<>();


        for (Item item : items) {

            // Sums values
            totalPrice += item.getPrice();
            totalCalories += item.getCalories();
            totalProtein += item.getProtein();

            //Count number of faveItemTypes bought
            if (faveItemTypes.contains(item) && !itemTypeList.contains(item)) {
                faveItemsBought += 1;
            }

            //Count number of hateItemTypes bought
            if (hateItemTypes.contains(item) && !itemTypeList.contains(item)) {
                hateItemsBought += 1;
            }

            //check number of different types of items in inventory
            if(!itemTypeList.contains(item)){
                itemTypeList.add(item);
                variety += 1;
            }

            //Count items containing thingsThatMatter (Extras)
            for(Extra extra : thingsThatMatter){
                itemsContainingExtras += (Arrays.asList(item.getExtra()).contains(extra) ? 1 : 0) / thingsThatMatter.size();
            }
        }

        //BUDGET
        //If happiness is below 80% of max budget, add happiness (max 80).
        //the lower the budget is, and the higher the budget factor, the more happiness added.
        if(totalPrice < budgetMax*0.8){
            happiness += 80 * (1 - totalPrice/budgetMax*0.8) * budgetFactor;
        }
        //If happiness is above 80% of max budget, subtract happiness (max 80).
        //the higher the budget usm abd the higher the budget factor, the more happiness subtracted
        else {
            happiness -= 80 * totalPrice/budgetMax*0.8 * budgetFactor;
        }


        //PROTEIN
        //Calculate minimum protein as 15% of minimum energy requirements
        //Calculate protein goal as 30% of goal energy requirements
        double proteinMin = calorieMin * 0.15 / 4;
        double proteinGoal = calorieGoal * 0.30 / 4;
        int tempHappiness = 0;

        //if protein above protein goal, add 80 points times protein/proteinGoal times protein factor
        //if protein below protein goal, subtract 80 points times (calculation) times protein factor
        //if protein below minimum protein, subtract 80 points times protein factor
        if(totalProtein > proteinGoal){
            tempHappiness += 80 * totalProtein/proteinGoal;
        }else if (totalProtein < proteinGoal){
            tempHappiness -= 80 * (1 - (totalProtein-proteinMin)/(proteinGoal-proteinMin));
        }else {
            tempHappiness -= 80;
        }
        //returns the smallest number, ensuring that
        happiness += Math.min(tempHappiness, 80) * proteinFactor;



        //PICKINESS
        double percentageFaveItemTypesBought = (double) faveItemsBought / (double) faveItemTypes.size();
        double percentageHateItemTypesBought = (double) hateItemsBought / (double) hateItemTypes.size();
        double percentItemsContainingExtras = (double) itemsContainingExtras / (double) items.size();

        //add the positives (faveItems and containingExtras) and subtract the negative (hateItems)
        double preferencesFollowed = (percentageFaveItemTypesBought+percentItemsContainingExtras-percentageHateItemTypesBought)/2;
        //if result is 0, subtract 80 from happiness, if return is 100, add 80 to happiness
        happiness += 80 * ((preferencesFollowed - 50) * 2)/100 * pickynessfactor;

        //GENERIC
        //if calories are at calorie min, subtract 10, if calories are at or above calorie goal, subtract 0.
        happiness -= Math.max((1 - (totalCalories - calorieMin) / (calorieGoal - calorieMin)) * 10,0);

        //if variaty is 1, subtract 20 points from happiness, if variaty is 12, add 20 points to happiness
        happiness += Math.min(20 * ((variety-6)/6),20);

        return happiness;
    }
}
