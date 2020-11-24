package worldofzuul.DomainLayer;

import worldofzuul.DomainLayer.Extra;
import worldofzuul.DomainLayer.Item;
import worldofzuul.DomainLayer.PlayerType;

import java.util.ArrayList;
import java.util.Random;

/**
 * Creates the Arraylist of items for each room as well as
 * the differenct player types
 *
 */
public class ContentGenerator {

    //TODO The playerType part is hard to maintain, and it's complicated to add new types
    private static final String STUDENT_NAME = "Student";
    private static final String BODYBUILDER_NAME = "Bodybuilder";
    private static final String PICKY_NAME = "Picky";

    // Number with which to multiply price when product is organic
    private static final double organicFactor = 1.2;

    /**
     *
     * @return returns the Arraylist variable items with
     * item objects in it. This is a list of butcher items.
     */
    public static ArrayList<Item> getButcherItems(){
        ArrayList<Item> items = new ArrayList<>();
        //Not organic
        items.add(new Item("200g Salmon",35,0.6,40,416.6,new Extra[]{}));
        items.add(new Item("1300g Whole Chicken",50,5,350,2400,new Extra[]{}));
        items.add(new Item("250g 2 Lamb Chops",50,5.25,62.5,735,new Extra[]{}));
        items.add(new Item("500g Pork Rib",50,3,135,1200,new Extra[]{}));
        items.add(new Item("100g Salami",10,0.7,22,335,new Extra[]{}));
        items.add(new Item("100g Roast Beef",12,0.7,29,170,new Extra[]{}));
        items.add(new Item("250g Chicken Breast",25,0.75,68,600,new Extra[]{}));
        items.add(new Item("500g Ground Beef",30,13,70,1660,new Extra[]{}));

        //Organic (organic price = non organic price * 1.2)
        items.addAll(createOrganics(items));
        return items;
    }

    /**
     *
     * @return returns a Arraylist of non organic item objects and organic.
     * This is a list of produce items.
     */
    public static ArrayList<Item> getProduceItems(){
        ArrayList<Item> items = new ArrayList<>();
        //Not organic
        items.add(new Item("500g Potatoes Imported",8,0.1,10,385,new Extra[]{Extra.VEGAN}));
        items.add(new Item("500g Potatoes Danish",12,0.05,10,385,new Extra[]{Extra.VEGAN}));
        items.add(new Item("500g Apples Danish",5,0.1,1.5,260,new Extra[]{Extra.VEGAN}));
        items.add(new Item("400g 1 Cucumber Imported",6,0.56,2.5,60,new Extra[]{Extra.VEGAN}));
        items.add(new Item("400g 1 Cucumber Danish",8,0.4,2.5,60,new Extra[]{Extra.VEGAN}));
        items.add(new Item("250g Salad Imported",10,1,2.25,35,new Extra[]{Extra.VEGAN}));
        items.add(new Item("250g Salad Danish",12,0.5,2.25,35,new Extra[]{Extra.VEGAN}));
        items.add(new Item("200g Mango",12,2.2,1.6,120,new Extra[]{Extra.VEGAN}));
        items.add(new Item("100g Blueberries",25,0.1,0.7,58,new Extra[]{Extra.VEGAN}));
        items.add(new Item("400g Tomatoes Imported",10,2,3.6,72,new Extra[]{Extra.VEGAN}));
        items.add(new Item("400g Tomatoes Danish",18,1.6,3.6,72,new Extra[]{Extra.VEGAN}));
        items.add(new Item("1000g Cabbage",4,0.4,13,246,new Extra[]{Extra.VEGAN}));

        //Organic
        items.addAll(createOrganics(items));
        return items;
    }

    /**
     *
     * @return returns a Arraylist of non organic item objects and organic.
     * this is a list of frozen items.
     */
    public static ArrayList<Item> getFrozenItems(){
        ArrayList<Item> items = new ArrayList<>();

        //Non-organic items
        items.add(new Item("200g Edamame",10,0.5,22,242,new Extra[]{Extra.VEGAN}));
        items.add(new Item("Ready-made Meal",39,6,25,473,new Extra[]{Extra.CONTAINS_LACTOSE, Extra.CONTAINS_SOY}));
        items.add(new Item("Frozen Pizza",24,5,42,833,new Extra[]{Extra.CONTAINS_LACTOSE, Extra.CONTAINS_GLUTEN, Extra.CONTAINS_SOY}));
        items.add(new Item("250g Spinach",6,0.3,7,57,new Extra[]{Extra.VEGAN}));
        items.add(new Item("300g frozen berries",14,0.3,2.5,180,new Extra[]{Extra.VEGAN}));
        items.add(new Item("200g Shrimp",35,0.6,48,200,new Extra[]{}));
        items.add(new Item("250g Clam",42,0.7,32,185,new Extra[]{}));

        //Organic items
        items.addAll(createOrganics(items));
        return items;
    }

    public static ArrayList<Item> getDairyItems(){
        ArrayList<Item> items = new ArrayList<>();

        //Non-organic items
        items.add(new Item("1L Milk",9,1,35,380,new Extra[]{Extra.CONTAINS_LACTOSE}));
        items.add(new Item("1L Yogurt",15,1.1,38,630,new Extra[]{Extra.CONTAINS_LACTOSE}));
        items.add(new Item("200g Cheese",26,2,50,660,new Extra[]{Extra.CONTAINS_LACTOSE}));
        items.add(new Item("200g Butter",10,2,1,1450,new Extra[]{Extra.CONTAINS_LACTOSE}));
        items.add(new Item("10 eggs",22,1,68,750,new Extra[]{}));
        items.add(new Item("200g Margarine",8,0.3,0,1450,new Extra[]{Extra.VEGAN}));

        //Organic items
        items.addAll(createOrganics(items));

        return items;
    }

    /**
     *
     * @return returns a Arraylist of item objects.
     * this is a list of bakery items.
     */
    public static ArrayList<Item> getBakeryItems(){
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("500g White Bread",18,0.3,45,1323,new Extra[]{Extra.CONTAINS_LACTOSE, Extra.CONTAINS_GLUTEN}));
        items.add(new Item("500g Wholegrain Bread",24,0.3,65,1235,new Extra[]{Extra.CONTAINS_LACTOSE, Extra.CONTAINS_GLUTEN}));
        items.add(new Item("200g Pastry",15,0.3,3,800,new Extra[]{Extra.CONTAINS_LACTOSE, Extra.CONTAINS_GLUTEN}));
        items.add(new Item("6 Bread Rolls",18,0.3,33,600,new Extra[]{Extra.CONTAINS_LACTOSE, Extra.CONTAINS_GLUTEN}));

        return items;
    }
    /**
     *
     * @return returns a Arraylist of non organic item objects and organic.
     * this is a list of tinned good items.
     */
    public static ArrayList<Item> getTinnedGoodsItems(){
        ArrayList<Item> items = new ArrayList<>();

        // Non-organic products
        items.add(new Item("250g Chick Peas",10,0.2,12.5,400,new Extra[]{Extra.VEGAN}));
        items.add(new Item("250g Kidney Beans",5,0.2,20,250,new Extra[]{Extra.VEGAN}));
        items.add(new Item("500g Rice",10,1,29,1800,new Extra[]{Extra.VEGAN, Extra.CONTAINS_GLUTEN}));
        items.add(new Item("500g Pasta",12,0.4,25,640,new Extra[]{Extra.VEGAN, Extra.CONTAINS_GLUTEN}));
        items.add(new Item("100g Canned Tuna",15,0.3,25,112,new Extra[]{}));
        items.add(new Item("100g Almonds",15,0.2,21,600,new Extra[]{Extra.VEGAN}));
        items.add(new Item("500g Oatmeal",5,0.3,65,1850,new Extra[]{Extra.VEGAN}));
        items.add(new Item("250g Canned Corn",5,1.5,8,250,new Extra[]{Extra.VEGAN}));

        //Organic products
        items.addAll(createOrganics(items));

        return items;
    }

    /**
     *
     * @param items
     * @return returns a new list with all the same objects as the passed
     * ArrayList, but turned organic.
     */
    public static ArrayList<Item> createOrganics(ArrayList<Item> items) {
        ArrayList<Item> organicItems = new ArrayList<>();
        double organicFactor = 1.2;
        for (Item item : items) {
            ArrayList<Extra> organicExtras = item.getExtra();
            organicExtras.add(Extra.ORGANIC);

            organicItems.add(new Item(
                    item.getName() + " Organic",
                    item.getPrice() * organicFactor,
                    item.getCo2(),
                    item.getProtein(),
                    item.getCalories(),
                    organicExtras));
        }
        return organicItems;
    }

    public static PlayerType getPlayerTypeByName(String name) throws IllegalArgumentException{
        switch (name) {
            case STUDENT_NAME:
                return getStudentPlayerType();
            case BODYBUILDER_NAME:
                return getBodybuilderPlayerType();
            case PICKY_NAME:
                return getPickyPlayerType();
        }
        throw new IllegalArgumentException("name should be one of the playerTypes in the ContentGenerator");
    }

    public static PlayerType getStudentPlayerType(){
        PlayerType type = new PlayerType(STUDENT_NAME, "The student is poor. You need to minimize the amount of money you use.\n" +
                "You need around 2200 calories per day, and the student cares about the enviroment. Focus on organic items, and please try not to make the world explode.");
        type.setFactors(1,7,2);
        type.setValues(50,1000,2200);
        type.addPositiveExtra(Extra.ORGANIC);
        return type;

    }

    public static PlayerType getBodybuilderPlayerType(){
        PlayerType type = new PlayerType(BODYBUILDER_NAME,"The body builder needs loads of protein!" +
                "You should get 3000 calories per day, and as much protein as possible, but try not to make the world explode");
        type.setFactors(6,1,3);
        type.setValues(75,1200,3000);
        return type;
    }

    public static PlayerType getPickyPlayerType(){
        PlayerType type = new PlayerType(PICKY_NAME,"The picky player type has a lot of money, but he is very Picky and needs the optimal amount of calories.");
        type.setFactors(1,1,8);
        type.setValues(100,1500,2000);
        type.addNegativeExtra(Extra.CONTAINS_GLUTEN);
        type.addNegativeExtra(Extra.CONTAINS_LACTOSE);
        type.addNegativeExtra(Extra.CONTAINS_SOY);
        return type;
    }

    /**
     *
     * @return returns a random playerType with the use of switch
     * statements
     */
    public static PlayerType getRandomPlayerType(){
        PlayerType type = null;
        switch (new Random().nextInt(3)){
            case 0 -> type = getStudentPlayerType();
            case 1 -> type = getBodybuilderPlayerType();
            case 2 -> type = getPickyPlayerType();
        }
        return type;
    }
}
