package worldofzuul.PresentationLayer.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import worldofzuul.PresentationLayer.MainGUI;

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

        Parent game = null;
        try {
            game = FXMLLoader.load(MainGUI.class.getResource("/fxml/GameCanvas.fxml"));
            stage.setScene(new Scene(game, 1197,720));
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
