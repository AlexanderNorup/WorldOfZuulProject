package worldofzuul.PresentationLayer.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import worldofzuul.DomainLayer.Game;
import worldofzuul.PresentationLayer.MainGUI;
import worldofzuul.PresentationLayer.PresentationHub;

import java.io.IOException;

public class MainMenuController {
    @FXML
    public Label playGameLabel;
    public Button playGameButton;
    public Button quitGameButton;
    public Label quitGameLabel;
    public Button selectCharacterButton;
    public Label selectCharacterLabel;
    public ImageView characterImageView;

    private PresentationHub hub;


    private final String[] playerTypes = new String[] {"Student", "Bodybuilder", "Picky", "Random", "Mystery"};
    private int playerTypeIndex = 3; // Random playerType

    @FXML
    public void initialize(){
        hub = PresentationHub.getInstance();
        characterImageView.setImage(new Image(Game.class.getResource("/sprites/RandomPlayer.png").toString()));
    }

    @FXML
    public void playGame(){
        playGameLabel.setText("The game is loading!");

        try {
            Parent game = FXMLLoader.load(MainGUI.class.getResource("/fxml/GameCanvas.fxml"));
            hub.getPrimaryStage().setScene(new Scene(game, 1280,720));
        } catch (IOException e) {
            playGameLabel.setText("There was en error starting the game!");
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteSaveFile() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete saveFile");
        alert.setHeaderText("Do you want to delete your saved game?");
        alert.setContentText("You will lose all progress!");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                hub.getGame().deleteSaveFile();
            }
        });

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
            Media media1 = new Media(MainGUI.class.getResource("/music/gurlie.mp3").toString());  //plays sound effect
            characterImageView.setImage(new Image(Game.class.getResource("/sprites/gurli.png").toString()));
            mediaPlayer = new MediaPlayer(media1);
            mediaPlayer.play();
        }
        else {
            playerTypeIndex++;
            if (playerTypeIndex > 3) {
                playerTypeIndex = 0;
            }
            selectCharacterLabel.setText("You selected: " + playerTypes[playerTypeIndex]);
            selectCharacterLabel.setRotate(0);
            switch (playerTypeIndex) {
                case 0 -> characterImageView.setImage(new Image(Game.class.getResource("/sprites/student.png").toString()));
                case 1 -> characterImageView.setImage(new Image(Game.class.getResource("/sprites/BodyBuilderTight.png").toString()));
                case 2 -> characterImageView.setImage(new Image(Game.class.getResource("/sprites/Picky.png").toString()));
                case 3 -> characterImageView.setImage(new Image(Game.class.getResource("/sprites/RandomPlayer.png").toString()));
            }
            selectCharacterLabel.setTextFill(Color.WHITE);
            selectCharacterLabel.setFont(new Font(12));
        }
        hub.getGame().setPlayerType(playerTypes[playerTypeIndex]);
    }

}
