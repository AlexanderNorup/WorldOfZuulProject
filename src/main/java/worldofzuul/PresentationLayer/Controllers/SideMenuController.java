package worldofzuul.PresentationLayer.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import worldofzuul.DomainLayer.Extra;
import worldofzuul.DomainLayer.Item;

import java.util.ArrayList;

public class SideMenuController {

    @FXML
    Label budgetLabel;

    @FXML
    Label sideMenuTitleLabel;

    @FXML
    Label sideMenuBudgetLabel;

    @FXML
    Label sideMenuCalorieLabel;

    @FXML
    Label sideMenuProteinLabel;

    @FXML
    ListView<Item> sideMenuListView;

    @FXML
    BorderPane sideMenu;

    ArrayList<Item> items;

    ContextMenu contextMenu;
    MenuItem drop;
    MenuItem inspect;

    @FXML
    public void initialize() {

        //Temporary test items, feel free to remove.
        items = new ArrayList<Item>();
        items.add(new Item("200g Salmon",35,0.6,40,416.6,new Extra[]{}));
        items.add(new Item("1300g Whole Chicken",50,5,350,2400,new Extra[]{}));
        items.add(new Item("250g 2 Lamb Chops",50,5.25,62.5,735,new Extra[]{}));
        items.add(new Item("500g Pork Rib",50,3,135,1200,new Extra[]{}));
        items.add(new Item("100g Salami",10,0.7,22,335,new Extra[]{}));
        items.add(new Item("100g Roast Beef",12,0.7,29,170,new Extra[]{}));

        ObservableList<Item> itemObservableList = FXCollections.observableArrayList();
        itemObservableList.addAll(items);

        sideMenuListView.setItems(itemObservableList);

        //ContextMenu that pops up on pressing enter
        contextMenu = new ContextMenu();
        inspect = new MenuItem("Inspect");
        drop = new MenuItem("Drop");

        inspect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Finds the textArea node
                TextArea textArea = (TextArea) sideMenu.getParent().getScene().lookup("#textBox").lookup("#textArea");

                //Sets textArea's text to currently selected item in listView
                textArea.setText(sideMenuListView.getSelectionModel().getSelectedItem().getDescription());

                //Sets visibility of textBox (parent of textArea) to true
                textArea.getParent().setVisible(true);
            }
        });

        drop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sideMenuListView.getItems().remove(sideMenuListView.getSelectionModel().getSelectedItem());
                System.out.println("Dropped");
            }
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
            case SPACE:
                //"Close" textBox, if textBox is "open". If textBox is not "open", but sideMenu is, "close" sideMenu
                Node textBox = sideMenu.getParent().getScene().lookup("#textBox");
                if (textBox.isVisible()) {
                    textBox.setVisible(false);
                } else if (sideMenu.isVisible()) {
                    sideMenu.setVisible(false);
                    sideMenu.setManaged(false);
                }
                break;
            case ESCAPE:
                //Prompts the user if they want to exit. This is also in this controller,
                // since this should be possible also when sideMenu is open
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Quit the game?");
                alert.setHeaderText("Do you want to quit the game?");
                alert.setContentText("You will loose all progress!");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        System.exit(0); //0-exit code means "successful".
                    }
                });
                break;
        }
    }
}
