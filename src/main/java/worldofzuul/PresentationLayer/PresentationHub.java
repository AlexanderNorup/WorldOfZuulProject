package worldofzuul.PresentationLayer;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import worldofzuul.DomainLayer.Interfaces.IItem;

public class PresentationHub {

    private Stage primaryStage;
    private Node SideMenu;
    private Node ShelfMenu;
    private ListView<IItem> sideMenuListView;
    private ListView<IItem> shelfMenuListView;
    private Pane textBox;
    private TextArea textBoxTextArea;
    private static PresentationHub hub;

    public static PresentationHub getInstance(){
        return hub != null ? hub : (hub = new PresentationHub());
    }
    private PresentationHub(){}

    public Stage getPrimaryStage() {
        return primaryStage;
    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

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

    public Node getSideMenu() {
        return SideMenu;
    }
    public void setSideMenu(Node sideMenu) {
        SideMenu = sideMenu;
    }

    public Node getShelfMenu() {
        return ShelfMenu;
    }
    public void setShelfMenu(Node shelfMenu) {
        ShelfMenu = shelfMenu;
    }

    public Pane getTextBox() {
        return textBox;
    }
    public void setTextBox(Pane textBox) {
        this.textBox = textBox;
    }

    public TextArea getTextBoxTextArea() {
        return textBoxTextArea;
    }
    public void setTextBoxTextArea(TextArea textBoxTextArea) {
        this.textBoxTextArea = textBoxTextArea;
    }
}
