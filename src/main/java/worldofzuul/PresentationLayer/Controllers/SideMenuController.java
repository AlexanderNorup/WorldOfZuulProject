package worldofzuul.PresentationLayer.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import worldofzuul.DomainLayer.Interfaces.IItem;
import worldofzuul.PresentationLayer.MainGUI;

public class SideMenuController {
    @FXML
    Label moneyGoal;

    @FXML
    Label moneySpent;

    @FXML
    ProgressBar moneyBar;

    @FXML
    Label sideMenuTitleLabel;

    @FXML
    Label sideMenuBudgetLabel;

    @FXML
    Label sideMenuCalorieLabel;

    @FXML
    Label sideMenuProteinLabel;

    @FXML
    ListView<IItem> sideMenuListView;

    @FXML
    BorderPane sideMenu;

    ContextMenu contextMenu;
    MenuItem drop;
    MenuItem inspect;

    @FXML
    public void initialize() {
        //ContextMenu that pops up on pressing enter
        contextMenu = new ContextMenu();
        inspect = new MenuItem("Inspect");
        drop = new MenuItem("Drop");

        ObservableList<IItem> listViewList = FXCollections.observableArrayList();
        listViewList.addAll(MainGUI.game.getPlayer().getInventory());
        sideMenuListView.setItems(listViewList);

        inspect.setOnAction(event -> {
            //Finds the textArea node
            TextArea textArea = (TextArea) sideMenu.getParent().getScene().lookup("#textBox").lookup("#textArea");

            //Sets textArea's text to currently selected item in listView
            textArea.setText(sideMenuListView.getSelectionModel().getSelectedItem().getDescription());

            //Sets visibility of textBox (parent of textArea) to true
            textArea.getParent().setVisible(true);
        });

        drop.setOnAction(event -> {
            IItem item = sideMenuListView.getSelectionModel().getSelectedItem();
            System.out.println("Dropped");
            MainGUI.game.drop(item);
            listViewList.clear();
            listViewList.addAll(MainGUI.game.getPlayer().getInventory());
        });

        contextMenu.getItems().addAll(inspect, drop);
        sideMenuListView.setContextMenu(contextMenu);
    }

    public void listViewKey(KeyEvent keyEvent) {
        switch(keyEvent.getCode()){
            case ENTER:
                Bounds bounds = sideMenuListView.localToScreen(sideMenuListView.getBoundsInLocal());
                contextMenu.show(sideMenuListView, bounds.getMinX() - 50, bounds.getMinY());
                break;
            case ESCAPE:
                //"Close" textBox, if textBox is "open". If textBox is not "open", but sideMenu is, "close" sideMenu
                Node textBox = sideMenu.getParent().getScene().lookup("#textBox");
                if (textBox.isVisible()) {
                    textBox.setVisible(false);
                }
                break;
        }
    }
}
