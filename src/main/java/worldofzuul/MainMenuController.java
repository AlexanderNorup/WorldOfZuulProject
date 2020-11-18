package worldofzuul;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    @FXML
    public Label playGameLabel;
    public Button playGameButton;
    public Button quitGameButton;
    public Label quitGameLabel;
    public Button selectCharacterButton;
    public Label selectCharacterLabel;


    @FXML
    public void playGame(){
        playGameLabel.setText("The game is loading!");

        Stage stage = (Stage) playGameLabel.getScene().getWindow();

        Parent mainMenu = null;
        try {
            mainMenu = FXMLLoader.load(MainGUI.class.getResource("/fxml/test.fxml"));
            stage.setScene(new Scene(mainMenu, 1197,720));
        } catch (IOException e) {
            playGameLabel.setText("There was en error starting the game!");
            e.printStackTrace();
        }
    }

    @FXML
    public void quitGame(){
        quitGameLabel.setText("Bye!");
        System.exit(0);
    }

    public void selectCharacter(){
        selectCharacterLabel.setText("You selected - Student -");
    }

}
