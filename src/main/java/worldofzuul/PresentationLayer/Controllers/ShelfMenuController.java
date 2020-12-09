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
import javafx.scene.layout.Pane;
import worldofzuul.DomainLayer.Interfaces.IItem;
import worldofzuul.PresentationLayer.MainGUI;
import worldofzuul.PresentationLayer.PresentationHub;

public class ShelfMenuController {

    @FXML
    ListView<IItem> shelfMenuListView;

    @FXML
    BorderPane shelfMenu;

    ContextMenu contextMenu;
    MenuItem take;
    MenuItem inspect;

    public void initialize() {
        PresentationHub.getInstance().setShelfMenuListView(shelfMenuListView);

        contextMenu = new ContextMenu();
        inspect = new MenuItem("Inspect");
        take = new MenuItem("Take");

        inspect.setOnAction(event -> {
            //Finds the textArea node
            TextArea textArea = PresentationHub.getInstance().getTextBoxTextArea();

            //Sets textArea's text to currently selected item in listView
            textArea.setText(shelfMenuListView.getSelectionModel().getSelectedItem().getDescription());

            //Sets visibility of textBox (parent of textArea) to true
            textArea.getParent().setVisible(true);
            MainGUI.playSoundEffect("select.wav");
        });

        take.setOnAction(event -> {
            IItem item = shelfMenuListView.getSelectionModel().getSelectedItem();
            boolean underBudget = MainGUI.game.take(item);

            if(underBudget){
                PresentationHub.getInstance().getSideMenuListView().getItems().setAll(MainGUI.game.getPlayer().getInventory());
                MainGUI.playSoundEffect("select.wav");
            }else {
                PresentationHub.getInstance().getTextBox().setVisible(true);
                PresentationHub.getInstance().getTextBoxTextArea().setText("The item is too pricey");
            }
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
                Pane textBox = PresentationHub.getInstance().getTextBox();
                if (textBox.isVisible()) {
                    textBox.setVisible(false);
                }else{
                    Node sideMenu = PresentationHub.getInstance().getSideMenu();
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
