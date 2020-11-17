package worldofzuul;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import worldofzuul.Objects.Item;

import java.util.ArrayList;

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

    SideMenuController(){
        Repository repository = Repository.getRepository();
        SideMenuTitleLabel.setText(repository.getSideMenuTitle());
    }

    private ObservableList<Item> observableItems;
    public void setSideMenuListView(ArrayList<Item> items){

        observableItems.addAll(items);
        SideMenuListView.setItems(observableItems);
    }



}
