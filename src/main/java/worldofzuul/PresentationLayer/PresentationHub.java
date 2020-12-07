package worldofzuul.PresentationLayer;

import javafx.scene.control.ListView;
import worldofzuul.DomainLayer.Interfaces.IItem;

public class PresentationHub {

    private ListView<IItem> sideMenuListView;
    private ListView<IItem> shelfMenuListView;

    public PresentationHub(){}

    public ListView<IItem> getSideMenuListView() {
        return sideMenuListView;
    }

    public void setSideMenuListView(ListView<IItem> sideMenuListView) {
        this.sideMenuListView = sideMenuListView;
    }

    public ListView<IItem> getShelfMenuListView() {
        return shelfMenuListView;
    }

    public void setShelfMenuListView(ListView<IItem> shelfMenuListView) {
        this.shelfMenuListView = shelfMenuListView;
    }
}
