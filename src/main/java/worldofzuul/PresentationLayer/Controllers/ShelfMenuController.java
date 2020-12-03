package worldofzuul.PresentationLayer.Controllers;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import worldofzuul.DomainLayer.Interfaces.IItem;
import worldofzuul.PresentationLayer.MainGUI;

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
        contextMenu = new ContextMenu();
        inspect = new MenuItem("Inspect");
        take = new MenuItem("Take");

        inspect.setOnAction(event -> {
            //Finds the textArea node
            TextArea textArea = (TextArea) shelfMenu.getParent().getScene().lookup("#textBox").lookup("#textArea");

            //Sets textArea's text to currently selected item in listView
            textArea.setText(shelfMenuListView.getSelectionModel().getSelectedItem().getDescription());

            //Sets visibility of textBox (parent of textArea) to true
            textArea.getParent().setVisible(true);
        });

        take.setOnAction(event -> {
            IItem item = shelfMenuListView.getSelectionModel().getSelectedItem();
            MainGUI.game.take(item);
            ListView<IItem> sideMenuListView = (ListView<IItem>) shelfMenu.getParent().getScene().lookup("#sideMenu").lookup("#sideMenuListView");
            sideMenuListView.getItems().setAll(MainGUI.game.getPlayer().getInventory());
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
                //"Close" textBox, if textBox is "open". If textBox is not "open", but sideMenu is, "close" sideMenu
                Node textBox = shelfMenu.getParent().getScene().lookup("#textBox");
                if (textBox.isVisible()) {
                    textBox.setVisible(false);
                } else if (shelfMenu.isVisible()){
                    shelfMenu.setVisible(false);
                    shelfMenu.setManaged(false);
                }
        }
    }
}
