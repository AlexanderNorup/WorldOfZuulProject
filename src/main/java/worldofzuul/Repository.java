package worldofzuul;


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
