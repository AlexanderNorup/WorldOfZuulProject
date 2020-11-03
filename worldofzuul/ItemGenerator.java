package worldofzuul;

import java.util.ArrayList;
import java.util.Random;

public class ItemGenerator {

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
        items.add(new Item("200g Salmon",35 * 1.2,0.6,40,416.6,new Extra[]{Extra.ORGANIC}));
        items.add(new Item("1300g Whole Chicken",50 * 1.2,5,350,2400,new Extra[]{Extra.ORGANIC}));
        items.add(new Item("250g 2 Lamb Chops",50 * 1.2,5.25,62.5,735,new Extra[]{Extra.ORGANIC}));
        items.add(new Item("500g Pork Rib",50 * 1.2,3,135,1200,new Extra[]{Extra.ORGANIC}));
        items.add(new Item("100g Salami",10 * 1.2,0.7,22,335,new Extra[]{Extra.ORGANIC}));
        items.add(new Item("100g Roast Beef",12 * 1.2,0.7,29,170,new Extra[]{Extra.ORGANIC}));
        items.add(new Item("250g Chicken Breast",25 * 1.2,0.75,68,600,new Extra[]{Extra.ORGANIC}));
        items.add(new Item("500g Ground Beef",30 * 1.2,13,70,1660,new Extra[]{Extra.ORGANIC}));

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
        items.add(new Item("500g Potatoes Imported",8 * 1.2,0.1,10,385,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));
        items.add(new Item("500g Potatoes Danish",12 * 1.2,0.05,10,385,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));
        items.add(new Item("500g Apples Danish",5 * 1.2,0.1,1.5,260,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));
        items.add(new Item("400g 1 Cucumber Imported",6 * 1.2,0.56,2.5,60,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));
        items.add(new Item("400g 1 Cucumber Danish",8 * 1.2,0.4,2.5,60,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));
        items.add(new Item("250g Salad Imported",10 * 1.2,1,2.25,35,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));
        items.add(new Item("250g Salad Danish",12 * 1.2,0.5,2.25,35,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));
        items.add(new Item("200g Mango",12 * 1.2,2.2,1.6,120,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));
        items.add(new Item("100g Blueberries",25 * 1.2,0.1,0.7,58,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));
        items.add(new Item("400g Tomatoes Imported",10 * 1.2,2,3.6,72,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));
        items.add(new Item("400g Tomatoes Danish",18 * 1.2,1.6,3.6,72,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));
        items.add(new Item("1000g Cabbage",4 * 1.2,0.4,13,246,new Extra[]{Extra.VEGAN, Extra.ORGANIC}));

        return items;
    }

    public static ArrayList<Item> getFrozenItems(){
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("200gr Edamame",12,0.9,11,122,new Extra[]{Extra.VEGAN, Extra.CONTAINS_SOY}));

        return items;
    }

    public static ArrayList<Item> getDairyItems(){
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("200gr Yogurt",6,2,3.5,61,new Extra[]{Extra.CONTAINS_LACTOSE}));

        return items;
    }

    public static ArrayList<Item> getBakeryItems(){
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("200gr Ground Beef",13,1.5,8.8,274,new Extra[]{Extra.GLUTEN, Extra.CONTAINS_LACTOSE}));

        return items;
    }

    public static ArrayList<Item> getTinnedGoodsItems(){
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("200gr Chick Peas",12,0.5,10,364,new Extra[]{Extra.VEGAN, }));

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
