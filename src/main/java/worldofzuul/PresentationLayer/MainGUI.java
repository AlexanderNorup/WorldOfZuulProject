package worldofzuul.PresentationLayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import worldofzuul.DomainLayer.Game;
import worldofzuul.DomainLayer.Interfaces.IGame;
import worldofzuul.PresentationLayer.Controllers.GameCanvasController;

import java.net.URL;
import java.util.HashMap;


public class MainGUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // The path to the resource is relative from /src/main/resources/
        Parent mainMenu = FXMLLoader.load(MainGUI.class.getResource("/fxml/mainmenu.fxml"));
        Scene s = new Scene(mainMenu, 1280,720);
        if(!this.getParameters().getRaw().contains("--mute")) initiateBackgroundMusic();

        PresentationHub.getInstance().setPrimaryStage(primaryStage);

        primaryStage.setScene(s);
        primaryStage.setResizable(false);
        primaryStage.setTitle("World Of Zhopping");
        primaryStage.getIcons().add(new Image(MainGUI.class.getResource("/sprites/logo.png").toString()));
        primaryStage.show();
    }

    private void initiateBackgroundMusic(){
        Media media = new Media(MainGUI.class.getResource("/music/tendo.mp3").toString());  //plays music
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
    }

}
