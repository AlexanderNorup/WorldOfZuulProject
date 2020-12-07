package worldofzuul.PresentationLayer;

import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import worldofzuul.DomainLayer.Interfaces.IItem;

public class PresentationHub {

    private ListView<IItem> sideMenuListView;
    private ListView<IItem> shelfMenuListView;
    private Pane textBox;
    private TextArea textBoxTextArea;
    private Stage primaryStage;

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

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
