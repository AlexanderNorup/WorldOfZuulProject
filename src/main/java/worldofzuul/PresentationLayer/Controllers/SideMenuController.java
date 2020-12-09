package worldofzuul.PresentationLayer.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import worldofzuul.DomainLayer.Interfaces.IItem;
import worldofzuul.Main;
import worldofzuul.PresentationLayer.MainGUI;
import worldofzuul.PresentationLayer.PresentationHub;

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

    private PresentationHub hub;
    private ContextMenu contextMenu;
    private MenuItem drop;
    private MenuItem inspect;

    @FXML
    public void initialize() {
        hub = PresentationHub.getInstance();
        System.out.println("SideMenuController - initialize");
        PresentationHub.getInstance().setSideMenuListView(sideMenuListView);

        //ContextMenu that pops up on pressing enter
        contextMenu = new ContextMenu();
        inspect = new MenuItem("Inspect");
        drop = new MenuItem("Drop");

        moneyBar.setProgress(0);
        moneySpent.setText(Double.toString(0));
        moneyGoal.setText(String.format("%4.2f kr.", hub.getGame().getPlayer().getBudget()));

        ObservableList<IItem> listViewList = FXCollections.observableArrayList();
        listViewList.addAll(hub.getGame().getPlayer().getInventory());

        sideMenuListView.setItems(listViewList);
        sideMenuListView.getItems().addListener(new ListChangeListener<IItem>() {
            @Override
            public void onChanged(Change<? extends IItem> c) {
                sideMenuCalorieLabel.setText(Integer.toString((int)(hub.getGame().getPlayer().getInventoryCalories())));
                sideMenuProteinLabel.setText((int) (hub.getGame().getPlayer().getInventoryProtein()) + " g");
                moneyBar.setProgress(hub.getGame().getPlayer().getInventoryValue()/hub.getGame().getPlayer().getBudget());
                moneySpent.setText(String.format("%4.2f kr.", hub.getGame().getPlayer().getInventoryValue()));
                System.out.println("listViewList - item update");
            }
        });

        listViewList.addListener((ListChangeListener<IItem>) c -> System.out.println("listViewList - item update"));

        inspect.setOnAction(event -> {
            //Finds the textArea node
            TextArea textArea = PresentationHub.getInstance().getTextBoxTextArea();

            //Sets textArea's text to currently selected item in listView
            textArea.setText(sideMenuListView.getSelectionModel().getSelectedItem().getDescription());

            //Sets visibility of textBox (parent of textArea) to true
            textArea.getParent().setVisible(true);
        });

        drop.setOnAction(event -> {
            IItem item = sideMenuListView.getSelectionModel().getSelectedItem();
            System.out.println("Dropped");
            hub.getGame().drop(item);
            sideMenuListView.getItems().setAll(hub.getGame().getPlayer().getInventory());
        });

        contextMenu.getItems().addAll(inspect, drop);
        sideMenuListView.setContextMenu(contextMenu);
        sideMenuListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            hub.playSoundEffect("select.wav");
        });
    }



    public void listViewKey(KeyEvent keyEvent) {
        switch(keyEvent.getCode()){
            case ENTER:
                Bounds bounds = sideMenuListView.localToScreen(sideMenuListView.getBoundsInLocal());
                contextMenu.show(sideMenuListView, bounds.getMinX() - 50, bounds.getMinY());
                break;
            case ESCAPE:
                //"Close" textBox, if textBox is "open". If textBox is not "open", but sideMenu is, "close" sideMenu
                Node textBox = PresentationHub.getInstance().getTextBox();
                if (textBox.isVisible()) {
                    textBox.setVisible(false);
                }else {
                    sideMenu.setVisible(false);
                }
                break;

        }
    }
}
