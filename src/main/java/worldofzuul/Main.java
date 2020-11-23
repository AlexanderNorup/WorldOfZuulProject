package worldofzuul;

import javafx.application.Application;
import worldofzuul.PresentationLayer.MainGUI;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        if(args.length > 0 && Arrays.asList(args).contains("--cli")) {
            //Launches the CLI-version
            Game game = new Game();
            game.play();
            return;
        }
        //Launches the GUI-version
        Application.launch(MainGUI.class, args);
    }

}
