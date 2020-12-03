package worldofzuul.PresentationLayer.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import worldofzuul.DomainLayer.Commandhandling.CommandWord;
import worldofzuul.DomainLayer.Interfaces.IItem;
import worldofzuul.Main;
import worldofzuul.PresentationLayer.MainGUI;

import java.util.ArrayList;

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

        moneyBar.setProgress(0);
        moneySpent.setText(Double.toString(0));
        moneyGoal.setText(Double.toString(MainGUI.game.getPlayer().getBudget()));

        ObservableList<IItem> listViewList = FXCollections.observableArrayList();
        listViewList.addAll(MainGUI.game.getPlayer().getInventory());
        sideMenuListView.setItems(listViewList);
        listViewList.addListener(new ListChangeListener<IItem>() {
            @Override
            public void onChanged(Change<? extends IItem> c) {
                sideMenuCalorieLabel.setText(Double.toString(MainGUI.game.getPlayer().getInventoryCalories()));
                sideMenuProteinLabel.setText(Double.toString(MainGUI.game.getPlayer().getInventoryProtein()));
                moneyBar.setProgress(MainGUI.game.getPlayer().getInventoryValue()/MainGUI.game.getPlayer().getBudget());
                moneySpent.setText(Double.toString(MainGUI.game.getPlayer().getInventoryValue()));
            }
        });

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
            MainGUI.game.doAction(CommandWord.DROP.toString(), item.getName());
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
