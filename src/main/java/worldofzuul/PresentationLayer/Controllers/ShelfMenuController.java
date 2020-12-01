package worldofzuul.PresentationLayer.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import worldofzuul.DomainLayer.Extra;
import worldofzuul.DomainLayer.Interfaces.IItem;
import worldofzuul.DomainLayer.Item;

import java.util.ArrayList;

public class ShelfMenuController {

    @FXML
    ListView<IItem> shelfMenuListView;

    @FXML
    BorderPane shelfMenu;

    ContextMenu contextMenu;
    MenuItem take;
    MenuItem inspect;

    ArrayList<IItem> items;

    public void initialize() {
        //Temporary test items, feel free to remove.
        items = new ArrayList<IItem>();
        items.add(new Item("200g Salmon",35,0.6,40,416.6,new Extra[]{}));
        items.add(new Item("1300g Whole Chicken",50,5,350,2400,new Extra[]{}));
        items.add(new Item("250g 2 Lamb Chops",50,5.25,62.5,735,new Extra[]{}));
        items.add(new Item("500g Pork Rib",50,3,135,1200,new Extra[]{}));
        items.add(new Item("100g Salami",10,0.7,22,335,new Extra[]{}));
        items.add(new Item("100g Roast Beef",12,0.7,29,170,new Extra[]{}));

        ObservableList<IItem> itemObservableList = FXCollections.observableArrayList();
        itemObservableList.addAll(items);

        shelfMenuListView.setItems(itemObservableList);

        contextMenu = new ContextMenu();
        inspect = new MenuItem("Inspect");
        take = new MenuItem("Take");

        inspect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Finds the textArea node
                TextArea textArea = (TextArea) shelfMenu.getParent().getScene().lookup("#textBox").lookup("#textArea");

                //Sets textArea's text to currently selected item in listView
                textArea.setText(shelfMenuListView.getSelectionModel().getSelectedItem().getDescription());

                //Sets visibility of textBox (parent of textArea) to true
                textArea.getParent().setVisible(true);
            }
        });

        take.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        contextMenu.getItems().addAll(inspect, take);
        shelfMenuListView.setContextMenu(contextMenu);
    }

    public void listViewKey(KeyEvent keyEvent) {
        switch(keyEvent.getCode()) {
            case ENTER:
                Bounds bounds = shelfMenuListView.localToScreen(shelfMenuListView.getBoundsInLocal());
                contextMenu.show(shelfMenuListView, bounds.getMaxX() - 50, bounds.getMinY());
                break;
            case ESCAPE:
                shelfMenu.setVisible(false);
                shelfMenu.setManaged(false);
        }
    }
}
