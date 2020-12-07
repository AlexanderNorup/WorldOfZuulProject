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
    Scene s;
    MediaPlayer mediaPlayer;
    public static IGame game;
    public static PresentationHub hub;
    private static HashMap<String, Media> soundCache;

    @Override
    public void start(Stage primaryStage) throws Exception {
        soundCache = new HashMap<>();
        // The path to the resource is relative from /src/main/resources/
        Parent mainMenu = FXMLLoader.load(MainGUI.class.getResource("/fxml/mainmenu.fxml"));
        s = new Scene(mainMenu, 1280,720);
        if(!this.getParameters().getRaw().contains("--mute")) {
            Media media = new Media(MainGUI.class.getResource("/music/tendo.mp3").toString());  //plays music
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            mediaPlayer.setOnEndOfMedia(new Runnable() {
                public void run() {
                    mediaPlayer.seek(Duration.ZERO);
                }
            });
        }

        game = new Game();
        hub = new PresentationHub();

        primaryStage.setScene(s);
        primaryStage.setResizable(false);
        primaryStage.setTitle("World Of Zhopping");
        primaryStage.getIcons().add(new Image(MainGUI.class.getResource("/sprites/logo.png").toString()));
        primaryStage.show();
    }


    public static void playSoundEffect(String sound){
        if(soundCache.containsKey(sound)){
            new MediaPlayer(soundCache.get(sound)).play();
            return;
        }
        URL url = MainGUI.class.getResource("/music/"+sound);
        if(url != null) {
            Media media = new Media(url.toString());  //plays sound effect
            new MediaPlayer(media).play();
            soundCache.put(sound, media);
        }
    }

}
