package worldofzuul;

import java.util.ArrayList;
import java.util.Random;

public class ItemGenerator {

    public static ArrayList<Item> getButcherItems(){
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("200gr Ground Beef",20,2.4,20,332,new Extra[]{}));

        return items;
    }

    public static ArrayList<Item> getProduceItems(){
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("200gr Broccoli",3,0.2,20,35,new Extra[]{Extra.VEGAN}));

        return items;
    }

    public static ArrayList<Item> getFrozenItems(){
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("200gr Edamame",10,0.5,22,242,new Extra[]{Extra.VEGAN}));
        items.add(new Item("Ready-made Meal",39,6,25,473,new Extra[]{Extra.CONTAINS_LACTOSE, Extra.CONTAINS_SOY}));
        items.add(new Item("Frozen Pizza",24,5,42,833,new Extra[]{Extra.CONTAINS_LACTOSE, Extra.CONTAINS_GLUTEN, Extra.CONTAINS_SOY}));
        items.add(new Item("250g Spinach",6,0.3,7,57,new Extra[]{Extra.VEGAN}));
        items.add(new Item("300g frozen berries",14,0.3,2.5,180,new Extra[]{Extra.VEGAN}));
        items.add(new Item("200g Shrimp",35,0.6,48,200,new Extra[]{}));
        items.add(new Item("250g Clam",42,0.7,32,185,new Extra[]{}));

        return items;
    }

    public static ArrayList<Item> getDairyItems(){
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("1L Milk",9,1,35,380,new Extra[]{Extra.CONTAINS_LACTOSE}));
        items.add(new Item("1L Yogurt",15,1.1,38,630,new Extra[]{Extra.CONTAINS_LACTOSE}));
        items.add(new Item("200g Cheese",26,2,50,660,new Extra[]{Extra.CONTAINS_LACTOSE}));
        items.add(new Item("200g Butter",10,2,1,1450,new Extra[]{Extra.CONTAINS_LACTOSE}));
        items.add(new Item("10 eggs",22,1,68,750,new Extra[]{}));
        items.add(new Item("200g Margarine",8,0.3,0,1450,new Extra[]{Extra.VEGAN}));

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
        items.add(new Item("250g Chick Peas",10,0.2,12.5,400,new Extra[]{Extra.VEGAN}));
        items.add(new Item("250g Kidney Beans",5,0.2,20,250,new Extra[]{Extra.VEGAN}));
        items.add(new Item("500g Rice",10,1,29,1800,new Extra[]{Extra.VEGAN, Extra.CONTAINS_GLUTEN}));
        items.add(new Item("500g Pasta",12,0.4,25,640,new Extra[]{Extra.VEGAN, Extra.CONTAINS_GLUTEN}));
        items.add(new Item("100g Canned Tuna",15,0.3,25,112,new Extra[]{}));
        items.add(new Item("100g Almonds",15,0.2,21,600,new Extra[]{Extra.VEGAN}));
        items.add(new Item("500g Oatmeal",5,0.3,65,1850,new Extra[]{Extra.VEGAN}));
        items.add(new Item("250g Canned Corn",5,1.5,8,250,new Extra[]{Extra.VEGAN}));

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

    public static PlayerType RandomPlayerType(){
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
