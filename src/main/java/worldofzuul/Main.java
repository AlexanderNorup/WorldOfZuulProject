package worldofzuul;

import javafx.application.Application;
import worldofzuul.DomainLayer.Game;
import worldofzuul.PresentationLayer.MainGUI;

import java.util.Arrays;

public class Main {
    private static boolean runningAsCLI;
    public static void main(String[] args) {
        if(args.length > 0 && Arrays.asList(args).contains("--cli")) {
            //Launches the CLI-version
            runningAsCLI = true;
            Game game = new Game();
            game.play();
            return;
        }
        //Launches the GUI-version
        runningAsCLI = false;
        Application.launch(MainGUI.class, args);
    }

    public static boolean isRunningAsCLI(){
        return runningAsCLI;
    }

}
