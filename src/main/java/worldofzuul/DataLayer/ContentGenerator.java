package worldofzuul.DataLayer;

import worldofzuul.DomainLayer.*;
import worldofzuul.DomainLayer.Interfaces.ICashier;
import worldofzuul.DomainLayer.Interfaces.IItem;
import worldofzuul.DomainLayer.Interfaces.IShelf;
import worldofzuul.DomainLayer.RoomObjects.Cashier;
import worldofzuul.DomainLayer.RoomObjects.Shelf;
import worldofzuul.DomainLayer.RoomObjects.Wall;
import worldofzuul.DomainLayer.RoomObjects.Warp;
import worldofzuul.PresentationLayer.Position;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Creates the Arraylist of items for each room as well as
 * the different player types
 *
 */
public class ContentGenerator {

    //TODO The playerType part is hard to maintain, and it's complicated to add new types
    public static final String STUDENT_NAME = "Student";
    public static final String BODYBUILDER_NAME = "Bodybuilder";
    public static final String PICKY_NAME = "Picky";
    public static final String RANDOM_TYPE_NAME = "Random";

    // Number with which to multiply price when product is organic
    private static final double organicFactor = 1.2;

    /**
     *
     * @return returns the Arraylist variable shelves with
     * Shelf objects in it. This is a list of butcher items.
     */
    private static ArrayList<IShelf> getButcherItems(){
        ArrayList<IShelf> shelves = new ArrayList<>();

        ArrayList<IItem> meats = new ArrayList<IItem>();
        meats.add(new Item("200g Salmon",35,0.6,32,460,new Extra[]{}));
        meats.add(new Item("100g Salmon",20,0.4,16,230,new Extra[]{}));
        meats.add(new Item("1300g Whole Chicken",50,5,240,2260,new Extra[]{}));
        meats.add(new Item("500g Half Chicken",20,2.1,120,1130,new Extra[]{}));
        meats.add(new Item("250g Lamb Chop",50,5.25,46,500,new Extra[]{}));
        meats.add(new Item("125g Lamb Chop",27,2.8,23,250,new Extra[]{}));
        meats.add(new Item("500g Pork Rib",50,3,92,1.340,new Extra[]{}));
        meats.add(new Item("200g Pork Rib",22,1.8,46,540,new Extra[]{}));

        ArrayList<IItem> coldCuts = new ArrayList<>();
        coldCuts.add(new Item("100g Salami",10,0.7,14,509,new Extra[]{}));
        coldCuts.add(new Item("100g Roast Beef",12,0.7,22,120,new Extra[]{}));
        coldCuts.add(new Item("500g Chicken Breast",30,1.4,100,640,new Extra[]{}));
        coldCuts.add(new Item("250g Chicken Breast",20,0.75,50,320,new Extra[]{}));
        coldCuts.add(new Item("100g Chicken Breast",10,0.45,25,130,new Extra[]{}));
        coldCuts.add(new Item("500g Ground Beef",25,13,100,820,new Extra[]{}));
        coldCuts.add(new Item("400g Ground Beef",22,11,80,660,new Extra[]{}));
        coldCuts.add(new Item("250g Ground Beef",18,7,50,410,new Extra[]{}));


        shelves.add(new Shelf(4,3,coldCuts));
        shelves.add(new Shelf(5,3,coldCuts));
        shelves.add(new Shelf(1,3, createOrganics(coldCuts)));
        shelves.add(new Shelf(2,3, createOrganics(coldCuts)));
        shelves.add(new Shelf(0,1, createOrganics(meats)));
        shelves.add(new Shelf(1,1, createOrganics(meats)));
        shelves.add(new Shelf(2,1, createOrganics(meats)));
        shelves.add(new Shelf(3,1, createOrganics(meats)));
        shelves.add(new Shelf(4,1,meats));
        shelves.add(new Shelf(5,1,meats));
        shelves.add(new Shelf(6,1,meats));
        shelves.add(new Shelf(7,1,meats));

        return shelves;
    }

    /**
     *
     * @return returns a Arraylist of non organic item objects and organic.
     * This is a list of produce items.
     */
    private static ArrayList<IShelf> getProduceItems(){
        ArrayList<IShelf> shelves = new ArrayList<>();


        ArrayList<IItem> imported = new ArrayList<>();
        imported.add(new Item("500g Potatoes Imported",8,0.1,10,385,new Extra[]{Extra.VEGAN}));
        imported.add(new Item("400g Cucumber Imported",6,0.56,2.8,48,new Extra[]{Extra.VEGAN}));
        imported.add(new Item("250g Salad Imported",10,1,3.25,14,new Extra[]{Extra.VEGAN}));
        imported.add(new Item("200g Mango",12,2.2,1,134,new Extra[]{Extra.VEGAN}));
        imported.add(new Item("100g Blueberries",25,0.1,0.7,52,new Extra[]{Extra.VEGAN}));
        imported.add(new Item("400g Tomatoes Imported",10,2,2.8,80,new Extra[]{Extra.VEGAN}));
        imported.add(new Item("1000g Cabbage",4,0.4,12,320,new Extra[]{Extra.VEGAN}));

        ArrayList<IItem> local = new ArrayList<>();
        local.add(new Item("500g Potatoes Danish",12,0.05,10,385,new Extra[]{Extra.VEGAN}));
        local.add(new Item("500g Apples Danish",5,0.1,1.5,275,new Extra[]{Extra.VEGAN}));
        local.add(new Item("400g Cucumber Danish",8,0.4,2.8,48,new Extra[]{Extra.VEGAN}));
        local.add(new Item("250g Salad Danish",12,0.5,3.25,14,new Extra[]{Extra.VEGAN}));
        local.add(new Item("400g Tomatoes Danish",18,1.6,2.8,80,new Extra[]{Extra.VEGAN}));


        shelves.add(new Shelf(2,3,local));
        shelves.add(new Shelf(3,3,local));
        shelves.add(new Shelf(4,3,local));
        shelves.add(new Shelf(5,3,imported));
        shelves.add(new Shelf(6,3,imported));
        shelves.add(new Shelf(7,3,imported));

        shelves.add(new Shelf(2,1, createOrganics(imported)));
        shelves.add(new Shelf(3,1, createOrganics(imported)));
        shelves.add(new Shelf(4,1, createOrganics(imported)));
        shelves.add(new Shelf(5,1, createOrganics(local)));
        shelves.add(new Shelf(6,1, createOrganics(local)));
        shelves.add(new Shelf(7,1, createOrganics(local)));

        return shelves;
    }

    /**
     *
     * @return returns a Arraylist of non organic item objects and organic.
     * this is a list of frozen items.
     */
    private static ArrayList<IShelf> getFrozenItems(){
        ArrayList<IShelf> shelves = new ArrayList<>();


        ArrayList<IItem> frozenGreens = new ArrayList<>();
        frozenGreens.add(new Item("200g Edamame",10,0.5,22,242,new Extra[]{Extra.VEGAN,Extra.FROZEN}));
        frozenGreens.add(new Item("250g Spinach",12,0.3,5.25,65,new Extra[]{Extra.VEGAN,Extra.FROZEN}));
        frozenGreens.add(new Item("100g Spinach",8,0.2,2.1,79.5,new Extra[]{Extra.VEGAN,Extra.FROZEN}));
        frozenGreens.add(new Item("300g Frozen Berries",14,0.3,1.2,180,new Extra[]{Extra.VEGAN,Extra.FROZEN}));
        frozenGreens.add(new Item("150g Frozen Berries",8,0.2,0.6,90,new Extra[]{Extra.VEGAN,Extra.FROZEN}));

        ArrayList<IItem> frozenMeat = new ArrayList<>();
        frozenMeat.add(new Item("500g Shrimp",90,1.4,67.5,345,new Extra[]{Extra.FROZEN}));
        frozenMeat.add(new Item("200g Shrimp",35,0.6,30.6,138,new Extra[]{Extra.FROZEN}));
        frozenMeat.add(new Item("100g Shrimp",20,0.4,15.3,69,new Extra[]{Extra.FROZEN}));
        frozenMeat.add(new Item("250g Clam",42,0.7,32,185,new Extra[]{Extra.FROZEN}));
        frozenMeat.add(new Item("100g Clam",20,0.4,11.9,83,new Extra[]{Extra.FROZEN}));

        ArrayList<IItem> frozenMeals = new ArrayList<>();
        frozenMeals.add(new Item("Ready-made Meal",39,6,25,710,new Extra[]{Extra.CONTAINS_LACTOSE, Extra.CONTAINS_SOY,Extra.FROZEN}));
        frozenMeals.add(new Item("Frozen Pizza",24,5,39.9,966,new Extra[]{Extra.CONTAINS_LACTOSE, Extra.CONTAINS_GLUTEN, Extra.CONTAINS_SOY,Extra.FROZEN}));
        frozenMeals.add(new Item("2-Pack Frozen Pizza",40,9,79.8,1932,new Extra[]{Extra.CONTAINS_LACTOSE, Extra.CONTAINS_GLUTEN, Extra.CONTAINS_SOY,Extra.FROZEN}));
        frozenMeals.add(new Item("3-Pack Frozen Pizza",55,16,119.7,2898,new Extra[]{Extra.CONTAINS_LACTOSE, Extra.CONTAINS_GLUTEN, Extra.CONTAINS_SOY,Extra.FROZEN}));

        shelves.add(new Shelf(2,1, createOrganics(frozenGreens)));
        shelves.add(new Shelf(3,1, createOrganics(frozenGreens)));
        shelves.add(new Shelf(4,1, createOrganics(frozenMeat)));
        shelves.add(new Shelf(5,1, createOrganics(frozenMeat)));
        shelves.add(new Shelf(6,1, createOrganics(frozenMeals)));
        shelves.add(new Shelf(7,1, createOrganics(frozenMeals)));

        shelves.add(new Shelf(2,3,frozenGreens));
        shelves.add(new Shelf(3,3,frozenGreens));
        shelves.add(new Shelf(4,3,frozenMeat));
        shelves.add(new Shelf(5,3,frozenMeat));
        shelves.add(new Shelf(6,3,frozenMeals));
        shelves.add(new Shelf(7,3,frozenMeals));

        return shelves;
    }

    private static ArrayList<IShelf> getDairyItems(){
        ArrayList<IShelf> shelves = new ArrayList<>();

        //Non-organic items
        ArrayList<IItem> liquids = new ArrayList<>();
        liquids.add(new Item("1L Milk",9,1,34,480,new Extra[]{Extra.CONTAINS_LACTOSE}));
        liquids.add(new Item("1L Oat milk",16,1,6,500,new Extra[]{}));
        liquids.add(new Item("1L Yogurt",15,1.1,40,500,new Extra[]{Extra.CONTAINS_LACTOSE}));
        liquids.add(new Item("0.2L Yogurt",6,0.3,8,100,new Extra[]{Extra.CONTAINS_LACTOSE}));

        ArrayList<IItem> nonLiquids = new ArrayList<>();
        nonLiquids.add(new Item("200g Cheese",26,2,48.4,670,new Extra[]{Extra.CONTAINS_LACTOSE}));
        nonLiquids.add(new Item("200g Butter",6,2,0.4,1486,new Extra[]{Extra.CONTAINS_LACTOSE}));
        nonLiquids.add(new Item("500g Butter",25,4.8,1,3715,new Extra[]{Extra.CONTAINS_LACTOSE}));
        nonLiquids.add(new Item("200g Margarine",7,0.3,0,1484,new Extra[]{Extra.VEGAN}));
        nonLiquids.add(new Item("500g Margarine",16,0.7,0,3710,new Extra[]{Extra.VEGAN}));
        nonLiquids.add(new Item("10 eggs",22,1,60,685,new Extra[]{}));
        nonLiquids.add(new Item("6 eggs",14,0.6,36,411,new Extra[]{}));

        shelves.add(new Shelf(0,1,nonLiquids));
        shelves.add(new Shelf(1,1,nonLiquids));
        shelves.add(new Shelf(2,1,liquids));
        shelves.add(new Shelf(3,1,liquids));
        shelves.add(new Shelf(4,1, createOrganics(nonLiquids)));
        shelves.add(new Shelf(5,1, createOrganics(nonLiquids)));
        shelves.add(new Shelf(6,1, createOrganics(liquids)));
        shelves.add(new Shelf(7,1, createOrganics(liquids)));

        return shelves;
    }

    /**
     *
     * @return returns a Arraylist of item objects.
     * this is a list of bakery items.
     */
    private static ArrayList<IShelf> getBakeryItems(){
        ArrayList<IShelf> shelves = new ArrayList<>();

        ArrayList<IItem> breads = new ArrayList<>();
        ArrayList<IItem> cakes = new ArrayList<>();

        breads.add(new Item("500g White Bread",18,0.3,45,1370,new Extra[]{Extra.CONTAINS_LACTOSE, Extra.CONTAINS_GLUTEN}));
        breads.add(new Item("200g White Bread",9,0.18,18,548,new Extra[]{Extra.CONTAINS_LACTOSE, Extra.CONTAINS_GLUTEN}));
        breads.add(new Item("200g Gluten Free White Bread",13,0.18,18,548,new Extra[]{Extra.CONTAINS_LACTOSE}));
        breads.add(new Item("500g Wholegrain Bread",24,0.3,53.5,1080,new Extra[]{Extra.CONTAINS_LACTOSE, Extra.CONTAINS_GLUTEN}));
        breads.add(new Item("6 Bread Rolls",18,0.3,31.8,882,new Extra[]{Extra.CONTAINS_LACTOSE, Extra.CONTAINS_GLUTEN}));
        breads.add(new Item("4 Bread Rolls",14,0.22,21.2,588,new Extra[]{Extra.CONTAINS_LACTOSE, Extra.CONTAINS_GLUTEN}));
        cakes.add(new Item("200g Pastry",15,0.35,10.4,818,new Extra[]{Extra.CONTAINS_LACTOSE, Extra.CONTAINS_GLUTEN}));
        cakes.add(new Item("200g Gluten Free Pastry",19,0.35,10.4,818,new Extra[]{Extra.CONTAINS_LACTOSE}));
        cakes.add(new Item("400g Pastry",36,0.6,20.8,1636,new Extra[]{Extra.CONTAINS_LACTOSE, Extra.CONTAINS_GLUTEN}));

        shelves.add(new Shelf(0,1, createOrganics(breads)));
        shelves.add(new Shelf(1,1, createOrganics(breads)));
        shelves.add(new Shelf(2,1, createOrganics(cakes)));
        shelves.add(new Shelf(3,1, createOrganics(cakes)));

        shelves.add(new Shelf(4,1,breads));
        shelves.add(new Shelf(5,1,breads));
        shelves.add(new Shelf(6,1,cakes));
        shelves.add(new Shelf(7,1,cakes));

        return shelves;
    }
    /**
     *
     * @return returns a Arraylist of non organic item objects and organic.
     * this is a list of tinned good items.
     */
    private static ArrayList<IShelf> getTinnedGoodsItems(){
        ArrayList<IShelf> shelves = new ArrayList<>();

        // Non-organic products
        ArrayList<IItem> notActuallyTinnedGoods = new ArrayList<>();
        notActuallyTinnedGoods.add(new Item("500g Rice",15,1,41,1790,new Extra[]{Extra.VEGAN, Extra.CONTAINS_GLUTEN}));
        notActuallyTinnedGoods.add(new Item("300g Rice",10,0.8,24.6,1074,new Extra[]{Extra.VEGAN, Extra.CONTAINS_GLUTEN}));
        notActuallyTinnedGoods.add(new Item("100g Rice",5,0.6,13.5,361,new Extra[]{Extra.VEGAN, Extra.CONTAINS_GLUTEN}));
        notActuallyTinnedGoods.add(new Item("500g Pasta",18,0.4,67.5,1805,new Extra[]{Extra.VEGAN, Extra.CONTAINS_GLUTEN}));
        notActuallyTinnedGoods.add(new Item("300g Pasta",12,0.35,40.5,1083,new Extra[]{Extra.VEGAN, Extra.CONTAINS_GLUTEN}));
        notActuallyTinnedGoods.add(new Item("100g Pasta",8,0.25,13.5,361,new Extra[]{Extra.VEGAN, Extra.CONTAINS_GLUTEN}));
        notActuallyTinnedGoods.add(new Item("500g Oats",12,0.3,71,1830,new Extra[]{Extra.VEGAN}));
        notActuallyTinnedGoods.add(new Item("300g Oats",8,0.25,42.6,1098,new Extra[]{Extra.VEGAN}));
        notActuallyTinnedGoods.add(new Item("100g Oats",6,0.15,14.2,366,new Extra[]{Extra.VEGAN}));
        notActuallyTinnedGoods.add(new Item("100g Almonds",15,0.2,25.6,606,new Extra[]{Extra.VEGAN}));

        ArrayList<IItem> actuallyTinnedGoods = new ArrayList<>();
        actuallyTinnedGoods.add(new Item("250g Chick Peas",10,0.2,17,305,new Extra[]{Extra.VEGAN,Extra.CANNED}));
        actuallyTinnedGoods.add(new Item("250g Kidney Beans",8,0.2,18.75,275,new Extra[]{Extra.VEGAN,Extra.CANNED}));
        actuallyTinnedGoods.add(new Item("100g Kidney Beans",5,0.15,7.5,110,new Extra[]{Extra.VEGAN,Extra.CANNED}));
        actuallyTinnedGoods.add(new Item("100g Canned Tuna",15,0.3,23.9,107,new Extra[]{Extra.CANNED}));
        actuallyTinnedGoods.add(new Item("50g Canned Tuna",10,0.2,11.95,53.5,new Extra[]{Extra.CANNED}));
        actuallyTinnedGoods.add(new Item("250g Canned Corn",9,1.5,8,250,new Extra[]{Extra.VEGAN,Extra.CANNED}));
        actuallyTinnedGoods.add(new Item("100g Canned Corn",5,1.2,3.1,101,new Extra[]{Extra.VEGAN,Extra.CANNED}));

        shelves.add(new Shelf(2,1, createOrganics(notActuallyTinnedGoods)));
        shelves.add(new Shelf(3,1, createOrganics(notActuallyTinnedGoods)));
        shelves.add(new Shelf(4,1,notActuallyTinnedGoods));
        shelves.add(new Shelf(5,1,notActuallyTinnedGoods));

        shelves.add(new Shelf(0,1, createOrganics(actuallyTinnedGoods)));
        shelves.add(new Shelf(1,1, createOrganics(actuallyTinnedGoods)));
        shelves.add(new Shelf(6,1,actuallyTinnedGoods));
        shelves.add(new Shelf(7,1,actuallyTinnedGoods));

        shelves.add(new Shelf(1,3, createOrganics(actuallyTinnedGoods)));
        shelves.add(new Shelf(2,3, createOrganics(actuallyTinnedGoods)));
        shelves.add(new Shelf(4,3,actuallyTinnedGoods));
        shelves.add(new Shelf(5,3,actuallyTinnedGoods));

        return shelves;
    }

    /**
     *
     * @param items
     * @return returns a new list with all the same objects as the passed
     * ArrayList, but turned organic.
     */
    private static ArrayList<IItem> createOrganics(ArrayList<IItem> items) {
        ArrayList<IItem> organicItems = new ArrayList<>();
        double organicFactor = 1.2;
        for (IItem item_ : items) {
            if(item_ instanceof Item){
                Item item = (Item) item_;
                ArrayList<Extra> organicExtras = new ArrayList<>(item.getExtra());
                organicExtras.add(Extra.ORGANIC);

                organicItems.add(new Item(
                    item.getName() + " Organic",
                    item.getPrice() * organicFactor,
                    item.getCo2(),
                    item.getProtein(),
                    item.getCalories(),
                    organicExtras));
            }
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
            case RANDOM_TYPE_NAME:
                return getRandomPlayerType();
        }
        throw new IllegalArgumentException("Name should be one of the playerTypes in the ContentGenerator");
    }

    private static PlayerType getStudentPlayerType(){
        PlayerType type = new PlayerType(STUDENT_NAME,
                "The student is poor.\n" +
                "You need to minimize the amount of money you spend.\n" +
                "You need around 2200 calories per day.\n" +
                "The student cares about the environment.\n" +
                "Focus on organic items. \n" +
                "Please try to avoid making the world explode!");
        type.setFactors(1,5,4);
        type.setValues(50,1000,2200);
        type.setPlayerSprite(Game.class.getResource("/sprites/student.png").toString());
        type.addPositiveExtra(Extra.ORGANIC);
        type.addFaveItems("Chicken Breast", "Salmon", "Apples", "Frozen Pizza", "Milk",
                "Yogurt", "White Bread");
        type.addHateItems("Ground Beef", "Clam", "Pastry", "Oat milk", "Ready-made meal",
                "Salad", "Tomatoes", "Oatmeal", "Rice", "Pasta");
        type.setFavhateamount(1);
        type.randomizeFaveHateItems();
        return type;

    }

    private static PlayerType getBodybuilderPlayerType(){
        PlayerType type = new PlayerType(BODYBUILDER_NAME,
                "The bodybuilder cares about meeting his protein goal!\n" +
                "You should aim to get at least 3000 calories per day.\n " +
                "Try to avoid soy as it reduces his gains.\n" +
                "Please try to avoid making the world explode!");
        type.setFactors(6,1,3);
        type.setValues(75,1200,3000);
        type.setPlayerSprite(Game.class.getResource("/sprites/BodyBuilderTight.png").toString());
        type.addNegativeExtra(Extra.CONTAINS_SOY);
        type.addFaveItems("Chicken Breast", "Chicken",
                "Apples", "eggs" , "Yogurt", "Oatmeal", "Almonds", "Salmon", "Lamb Chop");
        type.addHateItems("White Bread", "Clam", "Pastry", "Margarine", "Frozen Pizza",
                "Shrimp", "Ground Beef");
        type.setFavhateamount(1);
        type.randomizeFaveHateItems();
        return type;
    }

    private static PlayerType getPickyPlayerType(){

        PlayerType type = new PlayerType(PICKY_NAME,
                "The picky person has a lot of money to spend.\n" +
                        "However, he is very picky!\n" +
                        "He doesn't like soy, gluten or lactose. \n" +
                        "He also doesn't like frozen or canned food, \n " +
                        "but loves organic food!\n" +
                        "His calorie goal is very specific. \n" +
                        "Please try to avoid making the world explode!");
        type.setFactors(1,2,7);
        type.setValues(100,1800,2000);
        type.addNegativeExtra(Extra.CONTAINS_GLUTEN);
        type.addNegativeExtra(Extra.CONTAINS_LACTOSE);
        type.addNegativeExtra(Extra.CONTAINS_SOY);
        type.addNegativeExtra(Extra.FROZEN);
        type.addNegativeExtra(Extra.CANNED);
        type.addPositiveExtra(Extra.ORGANIC);
        type.addFaveItems("White Bread", "Pastry",
                "Salmon","Lamb Chop", "Salami","Almonds",
                "Blueberries","Mango","Pasta");
        type.addHateItems("Margarine","Pork Rib","Ground Beef","Oats","Rice","Chicken","Roast Beef",
                "Potatoes","Oat milk");
        type.setFavhateamount(3);
        type.randomizeFaveHateItems();



        type.setPlayerSprite(Game.class.getResource("/sprites/Picky.png").toString());
        return type;
    }

    /**
     *
     * @return returns a random playerType with the use of switch
     * statements
     */
    private static PlayerType getRandomPlayerType(){
        PlayerType type = null;
        switch (new Random().nextInt(3)){
            case 0 -> type = getStudentPlayerType();
            case 1 -> type = getBodybuilderPlayerType();
            case 2 -> type = getPickyPlayerType();
        }
        return type;
    }

    private static IItem getItemByName(ArrayList<IShelf> itemList, String name){
        for(IShelf shelf : itemList){
            for(IItem item : shelf.getItems()){
                if(item.getName().equalsIgnoreCase(name)){
                    return item;
                }
            }
        }
        System.out.println("getItemByName - No items by name");
        return null;
    }

    /*

    ______ _____  ________  ___ _____
    | ___ \  _  ||  _  |  \/  |/  ___|
    | |_/ / | | || | | | .  . |\ `--.
    |    /| | | || | | | |\/| | `--. \
    | |\ \\ \_/ /\ \_/ / |  | |/\__/ /
    \_| \_|\___/  \___/\_|  |_/\____/


     */

    /**
     * Util-method for getRooms();
     * Used to load a URL to an image from /resources/backgrounds/
     * Will make sure the background exists before trying to load the URL
     * @param background A String representing the filename <b>INCLUDING</b> the extension!
     * @return A String URL to the background for an Image to load.
     */
    private static String getBackground(String background){
        URL res = Game.class.getResource("/backgrounds/"+background);
        if(res != null){
            return res.toString();
        }
        return "";
    }

    public static ArrayList<Room> getRooms(){
        Room outside, aisle1, aisle2, aisle3, cashier, butcher, produce, frozen, dairy, bakery, tinnedGoods;
        outside = new Room("outside the main entrance of the store\nThe entrance is to your south", 8,5, getBackground("zuupermarket.png") );
        aisle1 = new Room("in the 1st aisle. \nTo your east is the dairy section, to your west is the bakery, " +
                "to your south is the 2nd aisle", 4,6, getBackground("aisle_butcher_produce.png"));
        aisle2 = new Room("in the 2nd aisle. \nTo your east is the frozen section, to your west is the " +
                "Tinned goods section, to your north is the 1st aisle, to your south is the 2nd aisle", 4,6, getBackground("aisle_dried_frozen.png"));
        aisle3 = new Room("in the 3rd aisle. \nTo your east is the produce section, to your west is the " +
                "butcher, to your north is the 2nd aisle, to your south is the cashier", 4,6, getBackground("aisle_bakery_dairy.png"));
        dairy = new Room("in the dairy section\nTo your west is the 1st aisle", 8,5, getBackground("dairy.png"));// ContentGenerator.getDairyItems());
        bakery = new Room("at the bakery\nTo your east is the 1st aisle", 8,5, getBackground("bakery.png"));//, ContentGenerator.getBakeryItems());
        frozen = new Room("in the frozen section. \nTo your west is aisle 2", 8,5, getBackground("frost.png"));//, ContentGenerator.getFrozenItems());
        tinnedGoods = new Room("in the tinned goods section. \nTo your east is aisle 2", 8,5, getBackground("dried.png"));//, ContentGenerator.getTinnedGoodsItems());
        produce = new Room("at the produce section. \nTo your west is the 3. aisle", 8,5, getBackground("produce.png"));//, ContentGenerator.getProduceItems());
        butcher = new Room("at the butcher. \nTo your east is the 3. aisle", 8,5, getBackground("butcher.png"));//, ContentGenerator.getButcherItems());
        cashier = new Room("at the cashier.\nUse command 'checkout' to checkout and finish the game ", 4,4, getBackground("cashier.png"));

        cashier.addRoomObject(new Cashier(1,0));
        cashier.addRoomObject(new Cashier(2,0));

        //Now add all the items;
        butcher.addRoomObject(new ArrayList<>(getButcherItems()));
        dairy.addRoomObject(new ArrayList<>(getDairyItems()));
        bakery.addRoomObject(new ArrayList<>(getBakeryItems()));
        frozen.addRoomObject(new ArrayList<>(getFrozenItems()));
        produce.addRoomObject(new ArrayList<>(getProduceItems()));
        tinnedGoods.addRoomObject(new ArrayList<>(getTinnedGoodsItems()));

        //Sets the exits for the CLI-version
        outside.setExit("south", aisle1);
        aisle1.setExit("east", dairy);
        dairy.setExit("west", aisle1);
        aisle1.setExit("west", bakery);
        bakery.setExit("east", aisle1);
        aisle1.setExit("south", aisle2);

        aisle2.setExit("north", aisle1);
        aisle2.setExit("east", frozen);
        frozen.setExit("west", aisle2);
        aisle2.setExit("west", tinnedGoods);
        tinnedGoods.setExit("east", aisle2);
        aisle2.setExit("south", aisle3);

        aisle3.setExit("north", aisle2);
        aisle3.setExit("east", produce);
        produce.setExit("west", aisle3);
        aisle3.setExit("west", butcher);
        butcher.setExit("east", aisle3);
        aisle3.setExit("south", cashier);

        cashier.setExit("north", aisle3);

        //Sets the exits for the non-CLI-version
        outside.addRoomObject(new Warp(3,3,aisle1,1,4));//1,2
        outside.addRoomObject(new Warp(4,3,aisle1,2,4));//2,2

        aisle1.addRoomObject(new Warp(0,2,butcher,6,2));
        aisle1.addRoomObject(new Warp(3,2,produce,1,2));
        aisle1.addRoomObject(new Warp(0,0,aisle2,0,4));
        aisle1.addRoomObject(new Warp(1,0,aisle2,1,4));
        aisle1.addRoomObject(new Warp(2,0,aisle2,2,4));
        aisle1.addRoomObject(new Warp(3,0,aisle2,3,4));
        aisle1.addRoomObject(new Warp(0,5,outside,3,4));//1,1
        aisle1.addRoomObject(new Warp(1,5,outside,3,4));//1,1
        aisle1.addRoomObject(new Warp(2,5,outside,4,4));//2,1
        aisle1.addRoomObject(new Warp(3,5,outside,4,4));//2,1

        aisle2.addRoomObject(new Warp(0,2,tinnedGoods,6,2));
        aisle2.addRoomObject(new Warp(3,2,frozen,1,2));
        aisle2.addRoomObject(new Warp(0,0,aisle3,0,4));
        aisle2.addRoomObject(new Warp(1,0,aisle3,1,4));
        aisle2.addRoomObject(new Warp(2,0,aisle3,2,4));
        aisle2.addRoomObject(new Warp(3,0,aisle3,3,4));
        aisle2.addRoomObject(new Warp(0,5,aisle1,1,1));
        aisle2.addRoomObject(new Warp(1,5,aisle1,1,1));
        aisle2.addRoomObject(new Warp(2,5,aisle1,2,1));
        aisle2.addRoomObject(new Warp(3,5,aisle1,2,1));

        aisle3.addRoomObject(new Warp(0,2,bakery,6,2));
        aisle3.addRoomObject(new Warp(3,2,dairy,1,2));
        aisle3.addRoomObject(new Warp(0,0,cashier,0,2));
        aisle3.addRoomObject(new Warp(1,0,cashier,1,2));
        aisle3.addRoomObject(new Warp(2,0,cashier,2,2));
        aisle3.addRoomObject(new Warp(3,0,cashier,3,2));
        aisle3.addRoomObject(new Warp(0,5,aisle2,1,2));
        aisle3.addRoomObject(new Warp(1,5,aisle2,1,1));
        aisle3.addRoomObject(new Warp(2,5,aisle2,2,1));
        aisle3.addRoomObject(new Warp(3,5,aisle2,2,1));

        cashier.addRoomObject(new Warp(0,3,aisle3,1,1));
        cashier.addRoomObject(new Warp(1,3,aisle3,1,1));
        cashier.addRoomObject(new Warp(2,3,aisle3,2,1));
        cashier.addRoomObject(new Warp(3,3,aisle3,2,1));

        butcher.addRoomObject(new Warp(7,2,aisle1,1,2));
        tinnedGoods.addRoomObject(new Warp(7,2,aisle2,1,2));
        bakery.addRoomObject(new Warp(7,2,aisle3,1,2));

        produce.addRoomObject(new Warp(0,2,aisle1,2,2));
        frozen.addRoomObject(new Warp(0,2,aisle2,2,2));
        dairy.addRoomObject(new Warp(0,2,aisle3,2,2));

        //Walls
        outside.addRoomObject( new Wall(0,3)); //2,4
        outside.addRoomObject( new Wall(1,3)); //1,4
        outside.addRoomObject( new Wall(2,3));
        outside.addRoomObject( new Wall(5,3));
        outside.addRoomObject( new Wall(6,3));
        outside.addRoomObject( new Wall(7,3));
        outside.addRoomObject( new Wall(7,4));

        aisle1.addRoomObject(new Wall(0,1));
        aisle1.addRoomObject(new Wall(3,1));
        aisle2.addRoomObject(new Wall(0,1));
        aisle2.addRoomObject(new Wall(3,1));
        aisle3.addRoomObject(new Wall(0,1));
        aisle3.addRoomObject(new Wall(3,1));


        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(outside);
        rooms.add(aisle1);
        rooms.add(aisle2);
        rooms.add(aisle3);
        rooms.add(dairy);
        rooms.add(bakery);
        rooms.add(frozen);
        rooms.add(tinnedGoods);
        rooms.add(produce);
        rooms.add(butcher);
        rooms.add(cashier);
        return rooms;
    }
}
