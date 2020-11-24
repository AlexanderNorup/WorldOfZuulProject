package worldofzuul.DomainLayer;


import java.util.ArrayList;

public class Repository {

    private static Repository repository;
    private String sideMenuTitle;
    private String sideMenuBudget;
    private String sideMenuCalorie;
    private String sideMenuProtein;
    private ArrayList<Item> sideMenuItems;

    private Repository(){
        this.sideMenuTitle = "Title";
        this.sideMenuBudget = "Budget value";
        this.sideMenuCalorie = "Calorie value";
        this.sideMenuItems = new ArrayList<>();

    }

    public static Repository getRepository(){
        return repository != null ? repository : (repository = new Repository());
    }


    public ArrayList<Item> getSideMenuItems() {
        return sideMenuItems;
    }

    public void setSideMenuItems(ArrayList<Item> sideMenuItems) {
        this.sideMenuItems = sideMenuItems;
    }

    public String getSideMenuTitle() {
        return sideMenuTitle;
    }

    public void setSideMenuTitle(String sideMenuTitle) {
        this.sideMenuTitle = sideMenuTitle;
    }

    public String getSideMenuBudget() {
        return sideMenuBudget;
    }

    public void setSideMenuBudget(String sideMenuBudget) {
        this.sideMenuBudget = sideMenuBudget;
    }

    public String getSideMenuCalorie() {
        return sideMenuCalorie;
    }

    public void setSideMenuCalorie(String sideMenuCalorie) {
        this.sideMenuCalorie = sideMenuCalorie;
    }

    public String getSideMenuProtein() {
        return sideMenuProtein;
    }

    public void setSideMenuProtein(String sideMenuProtein) {
        this.sideMenuProtein = sideMenuProtein;
    }
}
