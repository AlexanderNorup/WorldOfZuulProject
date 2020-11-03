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
