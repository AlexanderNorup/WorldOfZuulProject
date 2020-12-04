package worldofzuul.DomainLayer;

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
    private final ArrayList<Item> faveItemTypes;
    private final ArrayList<Item> hateItemTypes;
    private final ArrayList<Extra> positiveExtra;
    private final ArrayList<Extra> negativeExtra;
    private ArrayList<Item> tempFaveItemTypes;
    private ArrayList<Item> tempHateItemTypes;

    public PlayerType(String name, String description) {
        this.name = name;
        this.description = description;
        this.faveItemTypes = new ArrayList<>();
        this.hateItemTypes = new ArrayList<>();
        this.positiveExtra = new ArrayList<>();
        this.negativeExtra = new ArrayList<>();

        tempFaveItemTypes = getRandomItemsFromList(faveItemTypes, 3);
        tempHateItemTypes = getRandomItemsFromList(hateItemTypes, 3);

    }

    public String getName() {
        return name;
    }

    public String getDescription(){
        String faveItemsString = "\nFavorite Items: " + Arrays.toString(tempFaveItemTypes.stream().map(Item::getName).toArray()) + "\n";
        String hateItemsString = "\nHated Items: " + Arrays.toString(tempHateItemTypes.stream().map(Item::getName).toArray()) + "\n";
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
        this.proteinFactor = proteinFactor * equalizer;
        this.budgetFactor = budgetFactor * equalizer;
        this.pickynessFactor = pickynessFactor * equalizer;
    }

    public void addFaveItems(Item... items) {
        faveItemTypes.addAll(Arrays.asList(items));
    }

    public void addHateItems(Item... items) {
        hateItemTypes.addAll(Arrays.asList(items));
    }

    public void addPositiveExtra(Extra thingThatMatter) {
        positiveExtra.add(thingThatMatter);
    }

    public void addNegativeExtra(Extra thingThatMatter) {
        negativeExtra.add(thingThatMatter);
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
            if (tempFaveItemTypes.contains(item) && !itemTypeList.contains(item)) {
                faveItemsBought += 1;
            }

            //Count number of hateItemTypes bought
            if (tempHateItemTypes.contains(item) && !itemTypeList.contains(item)) {
                hateItemsBought += 1;
            }

            //check number of different types of items in inventory
            if(!itemTypeList.contains(item)){
                itemTypeList.add(item);
                variety += 1;
            }

            //Count items containing thingsThatMatter (Extras)
            int allExtras = positiveExtra.size() + negativeExtra.size();
            for(Extra extra : positiveExtra){
                itemsContainingExtras += (item.getExtra().contains(extra) ? 1 : 0) / allExtras;
            }

            for(Extra extra : negativeExtra){
                itemsContainingExtras += (!item.getExtra().contains(extra) ? 1 : 0) / allExtras;
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
        double percentageHateItemTypesBought = tempHateItemTypes.size() != 0 ? (double) hateItemsBought / (double) tempHateItemTypes.size() : 0.5;
        double percentItemsContainingExtras = allExtras != 0 ? (double) itemsContainingExtras / (double) items.size() : 0;

        happiness += 40 * percentageFaveItemTypesBought * pickynessFactor;
        happiness += 40 * ((percentItemsContainingExtras - 0.5) * 2) * pickynessFactor;
        happiness -= 40 * percentageHateItemTypesBought * pickynessFactor;

        //GENERIC
        //if calories are at calorie min, subtract 10, if calories are at or above calorie goal, subtract 0.
        happiness -= Math.max((1 - (totalCalories - calorieMin) / (calorieGoal - calorieMin)) * 10,0);


        //if variaty is 1, subtract 20 points from happiness, if variaty is 12, add 20 points to happiness
        happiness += Math.min(((variety-6) * 4),20);



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
        System.out.println("protein tempHappiness: " + tempHappiness);
        System.out.println("protein happiness: " + Math.min(tempHappiness, 80) * proteinFactor);
        System.out.println("percentageFaveItemTypesBought: " + 40 * ((percentageFaveItemTypesBought - 0.5) * 2) * pickynessFactor);
        System.out.println("percentItemsContainingExtras: " + 40 * ((percentItemsContainingExtras - 0.5) * 2) * pickynessFactor);
        System.out.println("percentageHateItemTypesBought: " + -80 * ((percentageHateItemTypesBought - 0.5) * 2) * pickynessFactor);
        System.out.println("Calorie happiness: " + Math.max((1 - (totalCalories - calorieMin) / (calorieGoal - calorieMin)) * 10,0));
        System.out.println("variaty happiness: " + Math.min(20 * ((variety-6)/5),20));
        System.out.println("Happiness: " +happiness);
        System.out.println("\n\n\n");

        return happiness;
    }

    /**
     * @param itemList the list from which you whish random items
     * @param amount the amount of random items you wish
     * @return list of random items from provided list
     */
    private ArrayList<Item> getRandomItemsFromList(ArrayList<Item> itemList, int amount){
        ArrayList<Item> Return = new ArrayList<>();
        if(amount > itemList.size()){
            System.out.println("getRandomItemsFromList - amount longer than list");
        }else {
            Random random = new Random();
            for(int x = 0 ; x < amount ; x++){
                int randomInt = random.nextInt(itemList.size());
                Return.add(itemList.get(new Random().nextInt(itemList.size())));
                itemList.remove(randomInt);
            }
        }
        return Return;
    }

}
