package worldofzuul.PresentationLayer.Controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import worldofzuul.DomainLayer.Item;
import worldofzuul.DomainLayer.Repository;

public class SideMenuController {

    @FXML
    public Label SideMenuTitleLabel;

    @FXML
    public Label SideMenuBudgetLabel;

    @FXML
    public Label SideMenuCalorieLabel;

    @FXML
    public Label SideMenuProteinLabel;

    @FXML
    public ListView<Item> SideMenuListView;

    private ObservableList<Item> observableItems;
    SideMenuController(){
        Repository repository = Repository.getRepository();
        SideMenuTitleLabel.setText(repository.getSideMenuTitle());
        SideMenuListView.getItems().addAll(repository.getSideMenuItems());
    }
 }
