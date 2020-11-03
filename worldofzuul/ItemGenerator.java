package worldofzuul;

import java.util.ArrayList;
import java.util.Random;

public class ItemGenerator {

    // Number with which to multiply price when product is organic
    private static final double organicFactor = 1.2;

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
        items.add(new Item("200g Salmon",35 * organicFactor,0.6,40,416.6,new Extra[]{Extra.ORGANIC}));
        items.add(new Item("1300g Whole Chicken",50 * organicFactor,5,350,2400,new Extra[]{Extra.ORGANIC}));
        items.add(new Item("250g 2 Lamb Chops",50 * organicFactor,5.25,62.5,735,new Extra[]{Extra.ORGANIC}));
        items.add(new Item("500g Pork Rib",50 * organicFactor,3,135,1200,new Extra[]{Extra.ORGANIC}));
        items.add(new Item("100g Salami",10 * organicFactor,0.7,22,335,new Extra[]{Extra.ORGANIC}));
        items.add(new Item("100g Roast Beef",12 * organicFactor,0.7,29,170,new Extra[]{Extra.ORGANIC}));
        items.add(new Item("250g Chicken Breast",25 * organicFactor,0.75,68,600,new Extra[]{Extra.ORGANIC}));
        items.add(new Item("500g Ground Beef",30 * organicFactor,13,70,1660,new Extra[]{Extra.ORGANIC}));

        return items;
    }

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

        //Organic (organic price = non organic price * 1.2)
        items.add(new Item("500g Potatoes Imported",8 * organicFactor,0.1,10,385,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));
        items.add(new Item("500g Potatoes Danish",12 * organicFactor,0.05,10,385,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));
        items.add(new Item("500g Apples Danish",5 * organicFactor,0.1,1.5,260,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));
        items.add(new Item("400g 1 Cucumber Imported",6 * organicFactor,0.56,2.5,60,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));
        items.add(new Item("400g 1 Cucumber Danish",8 * organicFactor,0.4,2.5,60,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));
        items.add(new Item("250g Salad Imported",10 * organicFactor,1,2.25,35,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));
        items.add(new Item("250g Salad Danish",12 * organicFactor,0.5,2.25,35,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));
        items.add(new Item("200g Mango",12 * organicFactor,2.2,1.6,120,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));
        items.add(new Item("100g Blueberries",25 * organicFactor,0.1,0.7,58,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));
        items.add(new Item("400g Tomatoes Imported",10 * organicFactor,2,3.6,72,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));
        items.add(new Item("400g Tomatoes Danish",18 * organicFactor,1.6,3.6,72,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));
        items.add(new Item("1000g Cabbage",4 * organicFactor,0.4,13,246,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));

        return items;
    }

    public static ArrayList<Item> getFrozenItems(){
        ArrayList<Item> items = new ArrayList<>();

        //Non-organic items
        items.add(new Item("200gr Edamame",10,0.5,22,242,new Extra[]{Extra.VEGAN}));
        items.add(new Item("Ready-made Meal",39,6,25,473,new Extra[]{Extra.CONTAINS_LACTOSE, Extra.CONTAINS_SOY}));
        items.add(new Item("Frozen Pizza",24,5,42,833,new Extra[]{Extra.CONTAINS_LACTOSE, Extra.CONTAINS_GLUTEN, Extra.CONTAINS_SOY}));
        items.add(new Item("250g Spinach",6,0.3,7,57,new Extra[]{Extra.VEGAN}));
        items.add(new Item("300g frozen berries",14,0.3,2.5,180,new Extra[]{Extra.VEGAN}));
        items.add(new Item("200g Shrimp",35,0.6,48,200,new Extra[]{}));
        items.add(new Item("250g Clam",42,0.7,32,185,new Extra[]{}));

        //Organic items
        items.add(new Item("200gr Organic Edamame",10 * organicFactor,0.5,22,242,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));
        items.add(new Item("Ready-made Organic Meal",39 * organicFactor,6,25,473,new Extra[]{Extra.CONTAINS_LACTOSE, Extra.CONTAINS_SOY, Extra.ORGANIC}));
        items.add(new Item("Frozen Organic Pizza",24 * organicFactor,5,42,833,new Extra[]{Extra.CONTAINS_LACTOSE, Extra.CONTAINS_GLUTEN, Extra.CONTAINS_SOY, Extra.ORGANIC}));
        items.add(new Item("250g Organic Spinach",6 * organicFactor,0.3,7,57,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));
        items.add(new Item("300g Organic frozen berries",14 * organicFactor,0.3,2.5,180,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));
        items.add(new Item("200g Organic Shrimp",35 * organicFactor,0.6,48,200,new Extra[]{, Extra.ORGANIC}));
        items.add(new Item("250g Organic Clam",42 * organicFactor,0.7,32,185,new Extra[]{, Extra.ORGANIC}));
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
        items.add(new Item("1L Organic Milk",9 * organicFactor,1,35,380,new Extra[]{Extra.CONTAINS_LACTOSE, Extra.ORGANIC}));
        items.add(new Item("1L Organic Yogurt",15 * organicFactor,1.1,38,630,new Extra[]{Extra.CONTAINS_LACTOSE, Extra.ORGANIC}));
        items.add(new Item("200g Organic Cheese",26 * organicFactor,2,50,660,new Extra[]{Extra.CONTAINS_LACTOSE, Extra.ORGANIC}));
        items.add(new Item("200g Organic Butter",10 * organicFactor,2,1,1450,new Extra[]{Extra.CONTAINS_LACTOSE, Extra.ORGANIC}));
        items.add(new Item("10 Organic eggs",22 * organicFactor,1,68,750,new Extra[]{, Extra.ORGANIC}));
        items.add(new Item("200g Organic Margarine",8 * organicFactor,0.3,0,1450,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));

        return items;
    }

    public static ArrayList<Item> getBakeryItems(){
        ArrayList<Item> items = new ArrayList<>();

        items.add(new Item("500g White Bread",18,0.3,45,1323,new Extra[]{Extra.CONTAINS_LACTOSE, Extra.CONTAINS_GLUTEN}));
        items.add(new Item("500g Wholegrain Bread",24,0.3,65,1235,new Extra[]{Extra.CONTAINS_LACTOSE, Extra.CONTAINS_GLUTEN}));
        items.add(new Item("200g Pastry",15,0.3,3,800,new Extra[]{Extra.CONTAINS_LACTOSE, Extra.CONTAINS_GLUTEN}));
        items.add(new Item("6 Bread Rolls",18,0.3,33,600,new Extra[]{Extra.CONTAINS_LACTOSE, Extra.CONTAINS_GLUTEN}));

        return items;
    }

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
        items.add(new Item("250g Organic Chick Peas",10 * organicFactor,0.2,12.5,400,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));
        items.add(new Item("250g Organic Kidney Beans",5 * organicFactor,0.2,20,250,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));
        items.add(new Item("500g Organic Rice",10 * organicFactor,1,29,1800,new Extra[]{Extra.VEGAN, Extra.CONTAINS_GLUTEN, Extra.ORGANIC}));
        items.add(new Item("500g Organic Pasta",12 * organicFactor,0.4,25,640,new Extra[]{Extra.VEGAN, Extra.CONTAINS_GLUTEN, Extra.ORGANIC}));
        items.add(new Item("100g Organic Canned Tuna",15 * organicFactor,0.3,25,112,new Extra[]{, Extra.ORGANIC}));
        items.add(new Item("100g Organic Almonds",15 * organicFactor,0.2,21,600,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));
        items.add(new Item("500g Organic Oatmeal",5 * organicFactor,0.3,65,1850,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));
        items.add(new Item("250g Organic Canned Corn",5 * organicFactor,1.5,8,250,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));

        return items;
    }

    public static PlayerType getStudentPlayerType(){
        return new PlayerType("Student",45,2200,400);
    }

    public static PlayerType getBodybuilderPlayerType(){
        return new PlayerType("Bodybuilder",200,3000,600);
    }

    public static PlayerType getPickyPlayerType(){
        return new PlayerType("Picky",45,2400,600);
    }

    public static PlayerType getSnobPlayerType(){
        return new PlayerType("Snob",45,2400,1000);
    }

    public static PlayerType randomPlayerType(){
        PlayerType type = null;
        switch (new Random().nextInt(4)){
            case 0 -> type = getStudentPlayerType();
            case 1 -> type = getBodybuilderPlayerType();
            case 2 -> type = getPickyPlayerType();
            case 3 -> type = getSnobPlayerType();
        }
        return type;
    }
}
