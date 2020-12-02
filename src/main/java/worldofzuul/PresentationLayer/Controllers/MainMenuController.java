package worldofzuul.PresentationLayer.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import worldofzuul.DomainLayer.Interfaces.IPlayer;
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

    private final String[] playerTypes = new String[] {"Student", "Bodybuilder", "Picky", "Random", "Mystery"};
    private int playerTypeIndex = 3; // Random playerType
    private IPlayer player;

    @FXML
    public void playGame(){
        playGameLabel.setText("The game is loading!");

        Stage stage = (Stage) playGameLabel.getScene().getWindow();

        try {
            Parent game = FXMLLoader.load(MainGUI.class.getResource("/fxml/GameCanvas.fxml"));
            stage.setScene(new Scene(game, 1280,720));
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

    @FXML
    public void selectCharacter(MouseEvent mouseEvent){
        if(mouseEvent.isControlDown()){
            playerTypeIndex = 4;
            selectCharacterLabel.setText("OINK OINK");
            selectCharacterLabel.setRotate(15);
            selectCharacterLabel.setTextFill(Color.DEEPPINK);
            selectCharacterLabel.setFont(new Font(30));
            MediaPlayer mediaPlayer;
            Media media = new Media(MainGUI.class.getResource("/music/Oink.mp3").toString());  //plays sound effect
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        }
        else {
            playerTypeIndex++;
            if (playerTypeIndex > 3) {
                playerTypeIndex = 0;
            }
            selectCharacterLabel.setText("You selected: " + playerTypes[playerTypeIndex]);
            selectCharacterLabel.setRotate(0);
            selectCharacterLabel.setTextFill(Color.WHITE);
            selectCharacterLabel.setFont(new Font(12));
        }
        MainGUI.game.setPlayerType(playerTypes[playerTypeIndex]);
    }

}
