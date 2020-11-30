package worldofzuul.PresentationLayer.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import worldofzuul.DomainLayer.Extra;
import worldofzuul.DomainLayer.Item;
import worldofzuul.DomainLayer.Repository;

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

        //Repository repository = Repository.getRepository();
        //sideMenuTitleLabel.setText(repository.getSideMenuTitle());
        //sideMenuListView.getItems().addAll(repository.getSideMenuItems());

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

        contextMenu = new ContextMenu();
        inspect = new MenuItem("Inspect");
        drop = new MenuItem("Drop");

        inspect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Inspected");
            }
        });

        drop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Dropped");
            }
        });

        contextMenu.getItems().addAll(inspect, drop);
    }

    public void listViewKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Bounds bounds = sideMenuListView.localToScreen(sideMenuListView.getBoundsInLocal());
            contextMenu.show(sideMenuListView, bounds.getMinX() - 50, bounds.getMinY());
        }
    }
}
