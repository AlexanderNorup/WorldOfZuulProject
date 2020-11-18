package worldofzuul;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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
        playGameLabel.setText("You tried to start the game");
        /*Game game = new Game();
        game.play();*/
    }

    @FXML
    public void quitGame(){
        quitGameLabel.setText("You tried to quit the game");

    }

    public void selectCharacter(){
        selectCharacterLabel.setText("You selected - Student -");
    }

}
