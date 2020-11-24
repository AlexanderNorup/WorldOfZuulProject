package worldofzuul.PresentationLayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;



public class MainGUI extends Application {
    Scene s;
    MediaPlayer mediaPlayer;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Pathen til resourcen er relativt fra /src/main/resources/
        Parent mainMenu = FXMLLoader.load(MainGUI.class.getResource("/fxml/mainmenu.fxml"));
        s = new Scene(mainMenu, 1280,720);

        Media media = new Media(MainGUI.class.getResource("/music/tendo.mp3").toString());  //plays music
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

        primaryStage.setScene(s);
        primaryStage.setResizable(false);
        primaryStage.setTitle("World Of Zhopping");
        primaryStage.getIcons().add(new Image(MainGUI.class.getResource("/sprites/avatar.png").toString()));
        primaryStage.show();
    }
}
