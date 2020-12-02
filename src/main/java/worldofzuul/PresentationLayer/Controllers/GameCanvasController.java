package worldofzuul.PresentationLayer.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import worldofzuul.DomainLayer.Interfaces.*;
import worldofzuul.DomainLayer.Item;
import worldofzuul.PresentationLayer.Grid;
import worldofzuul.PresentationLayer.GridObjects.*;
import worldofzuul.PresentationLayer.MainGUI;
import worldofzuul.PresentationLayer.Position;

import java.util.ArrayList;

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

    @FXML
    Pane sideMenu;

    @FXML
    Pane textBox;

    @FXML
    Pane shelfMenu;

    /**
     * This method runs every time the user pressed any key on their keyboard, while the game
     * is focused.
     * @param keyEvent Represents the key being pressed along with more information.
     */
    @FXML
    public void onKeyPressed(KeyEvent keyEvent) {
        //currently, when spacebar, enter, arrowkeys, esc, etc., are hit, and sidemenu is open,
        // these keyEvents goes to the sidemenu controller. Therefore a switchcase with some similar instructions
        // are implemented in the sidemenu controller.
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
            case I:
                if (sideMenu.isVisible()) {
                    sideMenu.setVisible(false);
                    sideMenu.setManaged(false);
                } else {
                    sideMenu.setVisible(true);
                    sideMenu.setManaged(true);
                    Scene sideScene = sideMenu.getScene();
                    ListView<Item> sideMenuListView = (ListView<Item>) sideScene.lookup("#sideMenuListView");
                    sideMenuListView.requestFocus();
                }
                break;
            case SPACE:
                if (textBox.isVisible()) {
                    textBox.setVisible(false);
                }
                break;
            case ENTER:
                // TODO check whether the player is standing in front of a shelf
                if (playerObject.getActiveGrid().getGridObject(new Position(playerObject.getPlayerPos().getX(), playerObject.getPlayerPos().getY()-1)) instanceof Shelf) {
                    shelfMenu.setVisible(true);
                    shelfMenu.setManaged(true);
                    Scene shelfScene = shelfMenu.getScene();
                    ListView<Item> shelfMenuListView = (ListView<Item>) shelfScene.lookup("#shelfMenuListView");
                    shelfMenuListView.requestFocus();
                }
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

        ArrayList<IRoom> rooms = MainGUI.game.getRooms();
        ArrayList<Grid> grids = new ArrayList<>();
        IPlayer player = MainGUI.game.getPlayer();

        IRoom startingRoom = player.getStartingRoom();



        for(IRoom iRoom : rooms){
            Grid grid = new Grid(gameCanvas, iRoom.getWidth(), iRoom.getHeight(),new Image(iRoom.getBackground()));
            for(IShelf shelf : iRoom.getShelves()){
                grid.setGridObject(new Shelf(shelf.getItems()),new Position(shelf.getX(),shelf.getY()));
            }

            for(ICashier iCashier : iRoom.getCashiers()){
                Cashier cashier = new Cashier();
                grid.setGridObject(cashier, new Position(iCashier.getXPosition(), iCashier.getYPosition()));
            }

            grids.add(grid);
        }

        for(IRoom iRoom : rooms){
            for(IWarp iWarp : iRoom.getWarps()){
                Warp warp = new Warp(grids.get(rooms.indexOf(iWarp.getDestination())),new Position(iWarp.getDestX(), iWarp.getDestY()));
                grids.get(rooms.indexOf(iRoom)).setGridObject(warp,new Position(iWarp.getX(), iWarp.getY()));
            }
        }

        Grid startingGrid = grids.get(rooms.indexOf(startingRoom));
        startingGrid.setActive(true);
        startingGrid.setGridObject(new Wall(), new Position(2,4));
        startingGrid.setGridObject(new Wall(), new Position(1,4));
        startingGrid.setActive(true);
        startingGrid.setGridObject(new Wall(), new Position(0,3)); //2,4
        startingGrid.setGridObject(new Wall(), new Position(1,3)); //1,4
        startingGrid.setGridObject(new Wall(), new Position(2,3));
        startingGrid.setGridObject(new Wall(), new Position(5,3));
        startingGrid.setGridObject(new Wall(), new Position(6,3));
        startingGrid.setGridObject(new Wall(), new Position(7,3));






        //Makes the first grid.
        Grid activeGrid = new Grid(gameCanvas, 4,6,new Image(MainGUI.class.getResource("/backgrounds/aisle_bakery_dairy.png").toString()));

        //Then passes the grid over to the PlayerObject. That's the thing we'll be moving
        //around. The last 2 arguments here represent the starting-position for the player.
        playerObject = new PlayerObject(startingGrid, new Position(2,4));
        playerObject.setAvatarImg(new Image (player.getSprite()));


        //Then we set some GridObjects. That could be anything that extends the GridObject class.
        //These "Dog"s extend the GridSprite class, which in turn then extends the GridObject.
        //GridSprites are objects that can be drawn to the screen in a tile.
        //Here 2 dogs are created just as a test. They are each given a position on the board.
        activeGrid.setGridObject(new Dog(), new Position(0,5));
        activeGrid.setGridObject(new Dog(), new Position(3,5));

        //Then we make another grid. It is created the excact same way as before.
        //It also has a dog.
        Grid anotherGrid = new Grid(gameCanvas, 9,6, new Image(MainGUI.class.getResource("/backgrounds/orange.png").toString()));
        anotherGrid.setGridObject(new Dog(), new Position(4,5));

        //Now we create a Warp
        //A Warp is also a child of GridObject. It dosn't have a sprite (unless you enable debug-mode).
        //A Warp takes 2 arguments: What grid to teleport to, and where the teleport should place the player.
        //A Warp activates when the player is about to step onto it.
        Warp activeToAnotherWarp = new Warp(activeGrid,new Position(3,3));
        //We add the Warp the same way we add a ordinary GridObject
        anotherGrid.setGridObject(activeToAnotherWarp, new Position(3,4));

        //We create another warp. This one takes the player from the activeGrid to anotherGrid.
        activeGrid.setGridObject(new Warp(anotherGrid,new Position(0,0)), new Position(3,2));


        //Then we set the first grid as "active".
        //This means that grid will be the one on screen.
        //activeGrid.setActive(true); //Starts drawing and animations

        root.setFocusTraversable(true); //Makes onKeyPressed() work.
    }
}
