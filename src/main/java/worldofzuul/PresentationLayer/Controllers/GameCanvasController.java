package worldofzuul.PresentationLayer.Controllers;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import worldofzuul.PresentationLayer.*;

import java.io.IOException;

public class GameCanvasController {

    private Grid g;
    private PlayerObject playerObject;

    @FXML
    Pane root;

    @FXML
    Canvas gameCanvas;
    GraphicsContext gc;

    @FXML
    public void onKeyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getCode()){
            case S:
            case DOWN:
                playerObject.moveDown();
                break;
            case W:
            case UP:
                playerObject.moveUp();
                break;
            case A:
            case LEFT:
                playerObject.moveLeft();
                break;
            case D:
            case RIGHT:
                playerObject.moveRight();
                break;
            case G:
                g.setDrawVisibleGrid(!g.isDrawVisibleGrid());
                break;
            case ESCAPE:
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Quit the game?");
                alert.setHeaderText("Do you want to quit the game?");
                alert.setContentText("You will loose all progress!");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        System.exit(0);
                    }
                });

                break;
        }

    }


    @FXML
    public void initialize(){
        //TODO: Canvas has width and height hardcoded. Do something about that, yes?
        g = new Grid(gameCanvas, 14,9);
        playerObject = new PlayerObject(g, 4,2);
        g.setGridObject(new Dog(), new Position(0,1));
        g.setGridObject(new Dog(), new Position(1,4));

        gameCanvas.setFocusTraversable(true); //Makes onKeyPressed() work.
    }

}
