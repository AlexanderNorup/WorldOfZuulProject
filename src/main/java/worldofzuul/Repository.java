package worldofzuul;


import worldofzuul.Objects.Item;

import java.util.ArrayList;

public class Repository {

    private static Repository repository;

    private Repository(){}

    public static Repository getRepository(){
        return repository != null ? repository : (repository = new Repository());
    }

    private String SideMenuTitle;
    private String SideMenuBudget;
    private String SideMenuCalorie;
    private String SideMenuProtein;
    private ArrayList<Item> SideMenuItems;

    public ArrayList<Item> getSideMenuItems() {
        return SideMenuItems;
    }

    public void setSideMenuItems(ArrayList<Item> sideMenuItems) {
        SideMenuItems = sideMenuItems;
    }

    public String getSideMenuTitle() {
        return SideMenuTitle;
    }

    public void setSideMenuTitle(String sideMenuTitle) {
        SideMenuTitle = sideMenuTitle;
    }

    public String getSideMenuBudget() {
        return SideMenuBudget;
    }

    public void setSideMenuBudget(String sideMenuBudget) {
        SideMenuBudget = sideMenuBudget;
    }

    public String getSideMenuCalorie() {
        return SideMenuCalorie;
    }

    public void setSideMenuCalorie(String sideMenuCalorie) {
        SideMenuCalorie = sideMenuCalorie;
    }

    public String getSideMenuProtein() {
        return SideMenuProtein;
    }

    public void setSideMenuProtein(String sideMenuProtein) {
        SideMenuProtein = sideMenuProtein;
    }
}
