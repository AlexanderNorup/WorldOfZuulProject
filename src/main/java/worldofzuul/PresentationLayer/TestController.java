package worldofzuul.PresentationLayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import worldofzuul.GUI.Dog;
import worldofzuul.GUI.Grid;
import worldofzuul.GUI.PlayerObject;
import worldofzuul.GUI.Position;

import java.util.Date;

public class TestController {
    Grid grid;
    PlayerObject playerObject;

    @FXML
    public AnchorPane root;


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
        }
        this.grid.drawGrid();
    }


    @FXML
    public void initialize(){

        //Some grid stuff

        int windowHeight = 720;
        int windowWidth = 1196;
        int gridHeight = 6;
        int gridWidth = 10;

        grid = new Grid(root, gridWidth, gridHeight, windowHeight, windowWidth);
        playerObject = new PlayerObject(grid, 4,3);

        grid.setGridObject(new Dog(), new Position(2,4));
        grid.setGridObject(new Dog(), new Position(3,4));

        grid.drawGrid();


        //grid[0][0] = new Player();
        //Set default background

        Image image = new Image(getClass().getResource("/backgrounds/default.png").toString());

        BackgroundImage bi = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        root.setBackground(new Background(bi));

        root.setFocusTraversable(true);

    }



}
