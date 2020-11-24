package worldofzuul.PresentationLayer.Controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import worldofzuul.PresentationLayer.*;
import worldofzuul.PresentationLayer.GridObjects.Dog;
import worldofzuul.PresentationLayer.GridObjects.PlayerObject;
import worldofzuul.PresentationLayer.GridObjects.Warp;

/**
 * Is the controller for the main GameCanvas.
 * Loading this controller will start the game.
 */
public class GameCanvasController {

    private PlayerObject playerObject;

    @FXML
    Pane root;

    @FXML
    Canvas gameCanvas;

    /**
     * This method runs every time the user pressed any key on their keyboard, while the game
     * is focused.
     * @param keyEvent Represents the key being pressed along with more information.
     */
    @FXML
    public void onKeyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getCode()){
            case S:
            case DOWN:
                //All these are similar. The PlayerObject makes sure it is actually possible
                //to move into the given direction.
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
                //Turns on debug mode.
                //The grid will be shown, alogn with warps.
                playerObject.getActiveGrid().setShowDebug(!playerObject.getActiveGrid().isShowDebug());
                break;
            case ESCAPE:
                //Prompts the user if they want to exit.
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Quit the game?");
                alert.setHeaderText("Do you want to quit the game?");
                alert.setContentText("You will loose all progress!");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        System.exit(0); //0-exit code means "successfull".
                    }
                });

                break;
        }

    }


    /**
     * This method gets called as soon as this Controller is loaded.
     */
    @FXML
    public void initialize(){
        //TODO: Canvas has width and height hardcoded. Do something about that, yes?

        //Makes the first grid.
        Grid activeGrid = new Grid(gameCanvas, 14,9,new Image(MainGUI.class.getResource("/backgrounds/pink.png").toString()));

        //Then pases the grid over to the PlayerObject. That's the thing we'll be moving
        //around. The last 2 arguments here represent the starting-position for the player.
        playerObject = new PlayerObject(activeGrid, new Position(4,2));

        //Then we set some GridObjects. That could be anything that extends the GridObject class.
        //These "Dog"s extend the GridSprite class, which in turn then extends the GridObject.
        //GridSprites are objects that can be drawn to the screen in a tile.
        //Here 2 dogs are created just as a test. They are each given a position on the board.
        activeGrid.setGridObject(new Dog(), new Position(0,1));
        activeGrid.setGridObject(new Dog(), new Position(1,4));

        //Then we make another grid. It is created the excact same way as before.
        //It also has a dog.
        Grid anotherGrid = new Grid(gameCanvas, 9,6, new Image(MainGUI.class.getResource("/backgrounds/orange.png").toString()));
        anotherGrid.setGridObject(new Dog(), new Position(4,5));

        //Now we create a Warp
        //A Warp is also a child of GridObject. It dosn't have a sprite (unless you enable debug-mode).
        //A Warp takes 2 arguments: What grid to teleport to, and where the teleport should place the player.
        //A Warp activates when the player is about to step onto it.
        Warp activeToAnotherWarp = new Warp(activeGrid,new Position(5,6));
        //We add the Warp the same way we add a ordinary GridObject
        anotherGrid.setGridObject(activeToAnotherWarp, new Position(3,4));

        //We create another warp. This one takes the player from the activeGrid to anotherGrid.
        activeGrid.setGridObject(new Warp(anotherGrid,new Position(0,0)), new Position(5,5));


        //Then we set the first grid as "active".
        //This means that grid will be the one on screen.
        activeGrid.setActive(true); //Starts drawing and animations



        gameCanvas.setFocusTraversable(true); //Makes onKeyPressed() work.
    }

}
