package worldofzuul;

import java.util.ArrayList;

public class ItemGenerator {

    public ArrayList<Item> getButcherItems(){
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("200gr Ground Beef",20,2.4,20,332,new Extra[]{}));

        return items;
    }

    public ArrayList<Item> getProduceItems(){
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("200gr Broccoli",3,0.2,20,35,new Extra[]{Extra.VEGAN}));

        return items;
    }

    public ArrayList<Item> getFrozenItems(){
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("200gr Edamame",12,0.9,11,122,new Extra[]{Extra.VEGAN, Extra.CONTAINS_SOY}));

        return items;
    }

    public ArrayList<Item> getDairyItems(){
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("200gr Yogurt",6,2,3.5,61,new Extra[]{Extra.CONTAINS_LACTOSE}));

        return items;
    }

    public ArrayList<Item> getBakeryItems(){
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("200gr Ground Beef",13,1.5,8.8,274,new Extra[]{Extra.GLUTEN, Extra.CONTAINS_LACTOSE}));

        return items;
    }

    public ArrayList<Item> getTinnedGoodsItems(){
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("200gr Chick Peas",12,0.5,10,364,new Extra[]{Extra.VEGAN, }));

        return items;
    }

    public PlayerType getStudentPlayerType(){
        return new PlayerType("Student",45,2200,400);
    }

    public PlayerType getBodybuilderPlayerType(){
        return new PlayerType("Bodybuilder",200,3000,600);
    }

    public PlayerType getPickyPlayerType(){
        return new PlayerType("Picky",45,2400,600);
    }

    public PlayerType getSnobPlayerType(){
        return new PlayerType("Snob",45,2400,1000);
    }
}
