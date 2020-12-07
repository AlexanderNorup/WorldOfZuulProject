package worldofzuul.DomainLayer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Keeps all the properties for a playerType (Student, Bodybuilder etc)
 * Calculates the players happiness based on the bought items
 */
public class PlayerType {

    private final String name;
    private final String description;
    private String playerSprite;
    private double budgetMax;
    private double calorieMin;
    private double calorieGoal;
    private double proteinFactor;
    private double budgetFactor;
    private double pickynessFactor;
    private final ArrayList<String> faveItemTypes;
    private final ArrayList<String> hateItemTypes;
    private final ArrayList<Extra> positiveExtra;
    private final ArrayList<Extra> negativeExtra;
    private ArrayList<String> tempFaveItemTypes;
    private ArrayList<String> tempHateItemTypes;

    public PlayerType(String name, String description) {
        this.name = name;
        this.description = description;
        this.faveItemTypes = new ArrayList<>();
        this.hateItemTypes = new ArrayList<>();
        this.positiveExtra = new ArrayList<>();
        this.negativeExtra = new ArrayList<>();
        this.tempFaveItemTypes = new ArrayList<>();
        this.tempHateItemTypes = new ArrayList<>();

    }

    public void randomizeFaveHateItems(){
        tempFaveItemTypes = getRandomItemsFromList(faveItemTypes, 3);
        tempHateItemTypes = getRandomItemsFromList(hateItemTypes, 3);
    }

    public String getName() {
        return name;
    }

    public String getDescription(){
        StringBuilder faveItemsString = new StringBuilder("\n\nFavorite Items:\n");
        StringBuilder hateItemsString = new StringBuilder("Hated Items:\n");

        for(String item : tempFaveItemTypes){
            faveItemsString.append(item).append(", ");
        }
        //Removes the last ', ' from the end of the string.
        if(faveItemsString.length() > 0) {
            faveItemsString.delete(faveItemsString.length() - 2, faveItemsString.length());
        }
        for(String item : tempHateItemTypes){
            hateItemsString.append(item).append(", ");
        }
        if(hateItemsString.length() > 0) {
            hateItemsString.delete(hateItemsString.length() - 2, hateItemsString.length());
        }

        faveItemsString.append("\n");
        hateItemsString.append("\n");

        if (tempFaveItemTypes.size() == 0 && tempHateItemTypes.size() == 0 ){
            return description;
        }
        else if (tempFaveItemTypes.size() == 0){
            return description + hateItemsString;
        }
        else if (tempHateItemTypes.size() == 0){
            return description + faveItemsString;
        }
        return description + faveItemsString + hateItemsString;
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
    public void setPlayerSprite(String path){
        this.playerSprite = path;
    }

    public String getPlayerSprite(){
        return playerSprite;
    }

    public void setOther(int calorieGoal){
        this.calorieGoal = calorieGoal;
    }

    //set factors to determin happiness caluclation for playertype
    public void setFactors(int proteinFactor, int budgetFactor, int pickynessFactor){
        double equalizer = 1/(double) (proteinFactor + budgetFactor + pickynessFactor);
        this.proteinFactor = new BigDecimal(proteinFactor * equalizer).setScale(2, RoundingMode.HALF_UP).doubleValue();
        this.budgetFactor = new BigDecimal(budgetFactor * equalizer).setScale(2, RoundingMode.HALF_UP).doubleValue();
        this.pickynessFactor = new BigDecimal(pickynessFactor * equalizer).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public void addFaveItems(String... itemName) {
        faveItemTypes.addAll(Arrays.asList(itemName));
    }

    public void addHateItems(String... itemName) {
        hateItemTypes.addAll(Arrays.asList(itemName));
    }

    public void addPositiveExtra(Extra thingThatMatter) {
        positiveExtra.add(thingThatMatter);
    }

    public void addNegativeExtra(Extra thingThatMatter) {
        negativeExtra.add(thingThatMatter);
    }

    //calculate happiness
    //TODO finish
    public double getHappiness(ArrayList<Item> items, String[][] previousItems){
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
            for(String faveItemName : tempFaveItemTypes){
                if (item.getName().contains(faveItemName) && !itemTypeList.contains(item)) {
                    faveItemsBought += 1;
                }
            }

            //Count number of hateItemTypes bought
            for(String hateItemName : tempHateItemTypes){
                if (item.getName().contains(hateItemName) && !itemTypeList.contains(item)) {
                    faveItemsBought += 1;
                }
            }

            //check number of different types of items in inventory
            if(!itemTypeList.contains(item)){
                itemTypeList.add(item);
                variety += 1;
            }

            //Count items containing thingsThatMatter (Extras)
            //If items doens't contain negativ extra, calculate percent of positive extras contained in item
            boolean containsNegativeExtra = false;

            for(Extra extra : item.getExtra()){
                if (negativeExtra.contains(extra)) {
                    containsNegativeExtra = true;
                    break;
                }
            }

            if(!containsNegativeExtra){
                for(Extra extra : positiveExtra){
                    itemsContainingExtras += (item.getExtra().contains(extra) ? 1 : 0) / positiveExtra.size();
                }
            }
        }

        //BUDGET
        //If totalPrice is below 80% of max budget, add happiness (max 80).
        //the lower the budget is, and the higher the budget factor, the more happiness added.
        if(totalPrice < budgetMax*0.8){
            happiness += 80 * (1 - totalPrice/budgetMax*0.8) * budgetFactor;
        }
        //If happiness is above 80% of max budget, subtract happiness (max 80).
        //the higher the budget usm abd the higher the budget factor, the more happiness subtracted

        else {
            happiness -= 80 * (totalPrice-budgetMax*0.8)/(budgetMax*0.2) * budgetFactor;
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
        if(totalProtein >= proteinGoal){
            tempHappiness += 80 * (totalProtein-proteinGoal)/proteinGoal;
        }else if (proteinMin < totalProtein && totalProtein < proteinGoal){
            tempHappiness -= 80 * (1 - (totalProtein-proteinMin)/(proteinGoal-proteinMin));
        }else {
            tempHappiness -= 80;
        }
        //returns the smallest number, ensuring that happiness points don't exceed 80.

        happiness += Math.min(tempHappiness, 80) * proteinFactor;



        //PICKINESS
        int allExtras = positiveExtra.size() + negativeExtra.size();
        double percentageFaveItemTypesBought = tempFaveItemTypes.size() != 0 ? (double) faveItemsBought / (double) tempFaveItemTypes.size() : 0;
        double percentageHateItemTypesBought = tempHateItemTypes.size() != 0 ? (double) hateItemsBought / (double) tempHateItemTypes.size() : 0;
        double percentItemsContainingExtras = allExtras != 0 ? (double) itemsContainingExtras / (double) items.size() : 0.5;

        happiness += 40 * percentageFaveItemTypesBought * pickynessFactor;
        happiness += 60 * ((percentItemsContainingExtras - 0.5) * 2) * pickynessFactor;
        happiness -= 40 * percentageHateItemTypesBought * pickynessFactor;

        //GENERIC
        //if calories are at calorie min, subtract 10, if calories are at or above calorie goal, subtract 0.
        happiness -= Math.max((1 - (totalCalories - calorieMin) / (calorieGoal - calorieMin)) * 10,0);


        //if variaty is 1, subtract 20 points from happiness, if variaty is 12, add 20 points to happiness
        happiness += Math.min(((variety-6) * 4),20);



        //GETTING TIRED OF EATING THE SAME THINGS-punishment
        double eatingSamePunishment = 0;
        double eatingTheSameFactor = 2;
        for(int day = 0; day < Math.min(previousItems.length,4); day++){ //Look at up to the 5 last days.
            for(int i = 0; i < previousItems[day].length; i++){
                for(Item item : items) {
                    //Remove reference to Organic and Weight
                    String itemName = item.getName().replace("Organic", "").replaceAll("\\d","");
                    if (itemName.equalsIgnoreCase(previousItems[day][i])) {
                        //The player bought something they did in the last few days.
                        //The player is punished more if they bought it more recently.
                        eatingSamePunishment += Math.log10(0.2 * (day + 1));
                    }
                }
            }
        }

        happiness -= eatingSamePunishment * eatingTheSameFactor;



        System.out.println("\n\n\n");
        System.out.println("HAPPINESS CALCULATION:");
        System.out.println("budgetFactor: " + budgetFactor);
        System.out.println("proteinFactor: " + proteinFactor);
        System.out.println("PickinessFactor: " + pickynessFactor);
        System.out.println("Price: " + totalPrice);
        System.out.println("calories: " + totalCalories);
        System.out.println("protein: " + totalProtein);
        System.out.println("faveItems: " + faveItemsBought);
        System.out.println("hateItems: " + hateItemsBought);
        System.out.println("extras: " + itemsContainingExtras);
        System.out.println("proteinMin: " + proteinMin);
        System.out.println("proteinGoal: " + proteinGoal);
        System.out.println("BudgetHappiness: " + (totalPrice < budgetMax*0.8 ? 80 * (1 - totalPrice/budgetMax*0.8) * budgetFactor : -80 * (totalPrice-budgetMax*0.8)/(budgetMax*0.2) * budgetFactor));
        System.out.println("protein tempHappiness: " + tempHappiness);
        System.out.println("protein happiness: " + Math.min(tempHappiness, 80) * proteinFactor);
        System.out.println("FaveItemPoints: " + 40 * percentageFaveItemTypesBought * pickynessFactor);
        System.out.println("ExtraItemPoints: " + 60 * ((percentItemsContainingExtras - 0.5) * 2) * pickynessFactor);
        System.out.println("HateItemPoints: " + -40 * percentageHateItemTypesBought * pickynessFactor);
        System.out.println("Calorie happiness: " + Math.max((1 - (totalCalories - calorieMin) / (calorieGoal - calorieMin)) * 10,0));
        System.out.println("Variety happiness: " + Math.min(20 * ((variety-6)/5),20));
        System.out.println("Eating the same things punishment: " + eatingSamePunishment);
        System.out.println("Happiness: " +happiness);
        System.out.println("\n\n\n");

        return happiness;
    }

    /**
     * @param itemList the list from which you whish random items
     * @param amount the amount of random items you wish
     * @return list of random items from provided list
     */
    private ArrayList<String> getRandomItemsFromList(ArrayList<String> itemList, int amount){
        ArrayList<String> startingPoint = new ArrayList<>(itemList);
        ArrayList<String> result = new ArrayList<>();

        if(amount > startingPoint.size()){
            System.out.println("getRandomItemsFromList - amount longer than list");
        }else {
            Random random = new Random();
            for(int x = 0 ; x < amount ; x++){
                int randomInt = random.nextInt(startingPoint.size());
                result.add(startingPoint.get(new Random().nextInt(startingPoint.size())));
                startingPoint.remove(randomInt);
            }
        }
        return result;
    }

}
