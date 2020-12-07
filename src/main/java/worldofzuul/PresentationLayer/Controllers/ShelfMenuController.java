package worldofzuul.PresentationLayer.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import worldofzuul.PresentationLayer.PresentationHub;

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
        MainGUI.hub.setShelfMenuListView(shelfMenuListView);

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
            MainGUI.playSoundEffect("select.wav");
        });

        take.setOnAction(event -> {
            IItem item = shelfMenuListView.getSelectionModel().getSelectedItem();
            MainGUI.game.take(item);
            ListView<IItem> sideMenuListView = (ListView<IItem>) shelfMenu.getParent().getScene().lookup("#sideMenu").lookup("#sideMenuListView");
            sideMenuListView.getItems().setAll(MainGUI.game.getPlayer().getInventory());
            MainGUI.playSoundEffect("select.wav");
        });

        contextMenu.getItems().addAll(inspect, take);
        shelfMenuListView.setContextMenu(contextMenu);

        shelfMenuListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            MainGUI.playSoundEffect("select.wav");
        });

    }

    public void listViewKey(KeyEvent keyEvent) {
        switch(keyEvent.getCode()) {
            case ENTER:
                Bounds bounds = shelfMenuListView.localToScreen(shelfMenuListView.getBoundsInLocal());
                contextMenu.show(shelfMenuListView, bounds.getMaxX() - 50, bounds.getMinY());
                MainGUI.playSoundEffect("select.wav");
                break;
            case ESCAPE:
                //"Close" textBox, if textBox is "open". If textBox is not "open", but sideMenu is, "close" sideMenu
                Node textBox = shelfMenu.getParent().getScene().lookup("#textBox");
                if (textBox.isVisible()) {
                    textBox.setVisible(false);
                }else{
                    Node sideMenu = shelfMenu.getParent().getScene().lookup("#sideMenu");
                    shelfMenu.setVisible(false);
                    shelfMenu.setManaged(false);
                    sideMenu.setDisable(false);
                    sideMenu.setVisible(false);
                    textBox.setVisible(false);
                    GameCanvasController.setLocked(false);
                }
                MainGUI.playSoundEffect("select.wav");
                break;
        }
    }
}
