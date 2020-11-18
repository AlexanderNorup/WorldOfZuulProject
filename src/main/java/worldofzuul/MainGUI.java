package worldofzuul;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;

public class MainGUI extends Application {
    Scene s;
    MediaPlayer mediaPlayer;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Pathen til resourcen er relativt fra /src/main/resources/
        Parent root = FXMLLoader.load(MainGUI.class.getResource("/fxml/mainmenu.fxml"));
        s = new Scene(root, 1197,720);

        Media media = new Media(MainGUI.class.getResource("/music/pilfinger.mp3").toString());  //plays music
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
      
        primaryStage.setScene(s);
        primaryStage.setResizable(false);
        primaryStage.setTitle("WorldOfZuul Test");
        primaryStage.show();
    }
}
