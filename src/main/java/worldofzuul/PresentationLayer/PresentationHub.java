package worldofzuul.PresentationLayer;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import worldofzuul.DomainLayer.Game;
import worldofzuul.DomainLayer.Interfaces.IGame;
import worldofzuul.DomainLayer.Interfaces.IItem;

import java.net.URL;
import java.util.HashMap;

public class PresentationHub {

    private IGame game;
    private Stage primaryStage;
    private Node SideMenu;
    private Node ShelfMenu;
    private ListView<IItem> sideMenuListView;
    private ListView<IItem> shelfMenuListView;
    private Pane textBox;
    private TextArea textBoxTextArea;
    private static PresentationHub hub;
    private static HashMap<String, Media> soundCache;

    public static PresentationHub getInstance(){
        return hub != null ? hub : (hub = new PresentationHub());
    }
    private PresentationHub(){
        soundCache = new HashMap<>();
        game = new Game();
    }

    public void playSoundEffect(String sound){
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

    public IGame getGame() {
        return game;
    }
    public void setGame(IGame game) {
        this.game = game;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public ListView<IItem> getSideMenuListView() {
        return sideMenuListView;
    }
    public void setSideMenuListView(ListView<IItem> sideMenuListView) {
        this.sideMenuListView = sideMenuListView;
    }

    public ListView<IItem> getShelfMenuListView() {
        return shelfMenuListView;
    }
    public void setShelfMenuListView(ListView<IItem> shelfMenuListView) {
        this.shelfMenuListView = shelfMenuListView;
    }

    public Node getSideMenu() {
        return SideMenu;
    }
    public void setSideMenu(Node sideMenu) {
        SideMenu = sideMenu;
    }

    public Node getShelfMenu() {
        return ShelfMenu;
    }
    public void setShelfMenu(Node shelfMenu) {
        ShelfMenu = shelfMenu;
    }

    public Pane getTextBox() {
        return textBox;
    }
    public void setTextBox(Pane textBox) {
        this.textBox = textBox;
    }

    public TextArea getTextBoxTextArea() {
        return textBoxTextArea;
    }
    public void setTextBoxTextArea(TextArea textBoxTextArea) {
        this.textBoxTextArea = textBoxTextArea;
    }
}
