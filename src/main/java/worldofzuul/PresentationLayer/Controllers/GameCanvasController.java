package worldofzuul.PresentationLayer.Controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import worldofzuul.PresentationLayer.*;

public class GameCanvasController {

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
                playerObject.getActiveGrid().setDrawVisibleGrid(!playerObject.getActiveGrid().isDrawVisibleGrid());
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
        Grid activeGrid = new Grid(gameCanvas, 14,9,new Image(MainGUI.class.getResource("/backgrounds/pink.png").toString()));
        playerObject = new PlayerObject(activeGrid, 4,2);
        activeGrid.setGridObject(new Dog(), new Position(0,1));
        activeGrid.setGridObject(new Dog(), new Position(1,4));



        Grid anotherGrid = new Grid(gameCanvas, 9,6, new Image(MainGUI.class.getResource("/backgrounds/orange.png").toString()));
        anotherGrid.setGridObject(new Dog(), new Position(4,5));
        anotherGrid.setGridObject(new Warp(activeGrid,new Position(5,6)), new Position(3,4));
        activeGrid.setGridObject(new Warp(anotherGrid,new Position(0,0)), new Position(5,5));
        activeGrid.setActive(true); //Starts drawing and animations



        gameCanvas.setFocusTraversable(true); //Makes onKeyPressed() work.
    }

}
