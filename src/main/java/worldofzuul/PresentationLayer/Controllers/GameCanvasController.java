package worldofzuul.PresentationLayer.Controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import worldofzuul.DomainLayer.Interfaces.*;
import worldofzuul.DomainLayer.Item;
import worldofzuul.PresentationLayer.*;
import worldofzuul.PresentationLayer.GridObjects.*;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * Is the controller for the main GameCanvas.
 * Loading this controller will start the game.
 */
public class GameCanvasController {

    public MenuButton checkoutmenu; //et alternativ til checkout, synes det ser mere in-game ud end en alertbox
    public MenuItem yesButton;
    public MenuItem noButton;
    private PlayerObject playerObject;
    private HashMap<IRoom, Grid> gridMap;
    private HashMap<Grid, IRoom> iRoomMap;
    private Transition transitionScreen;
    private boolean locked;


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
     * This method gets called as soon as this Controller is loaded.
     */
    @FXML
    public void initialize(){
        //TODO: Canvas has width and height hardcoded. Do something about that, yes?

        gridMap = new HashMap<>();
        iRoomMap = new HashMap<>();
        IPlayer player = MainGUI.game.getPlayer();

        IRoom startingRoom = player.getStartingRoom();

        //Make hashMap of rooms and grids
        for(IRoom iRoom : MainGUI.game.getRooms()){
            Grid grid = new Grid(gameCanvas, iRoom.getWidth(), iRoom.getHeight(),new Image(iRoom.getBackground()));

            for(IRoomObject object : iRoom.getObjects()){
                if(object instanceof IShelf){
                    grid.setGridObject(new Shelf(((IShelf) object).getItems()),new Position(object.getXPosition(),object.getYPosition()));
                }
            }

            for(IRoomObject object : iRoom.getObjects()){
                if(object instanceof ICashier){
                    grid.setGridObject(new Cashier(), new Position(object.getXPosition(), object.getYPosition()));
                }
            }

            gridMap.put(iRoom,grid);
            iRoomMap.put(grid,iRoom);
        }


        for(IRoom iRoom : gridMap.keySet()){
            for(IRoomObject object : iRoom.getObjects()){
                if(object instanceof IWarp){
                    IWarp iWarp = (IWarp) object;
                    Warp warp = new Warp(gridMap.get(iWarp.getDestination()),new Position(iWarp.getDestX(), iWarp.getDestY()));
                    gridMap.get(iRoom).setGridObject(warp,new Position(iWarp.getXPosition(), iWarp.getYPosition()));
                }
            }
        }

        Grid startingGrid = gridMap.get(startingRoom);
        startingGrid.setGridObject(new Wall(), new Position(2,4));
        startingGrid.setGridObject(new Wall(), new Position(1,4));
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



        //Transition Work!
        this.transitionScreen = new Transition(gameCanvas);
        this.transitionScreen.setDoneHandler(new AnimationDoneHandler() {
            @Override
            public void animationDone() {
                startingGrid.setActive(true);
            }
        });

        this.transitionScreen.addLine("Welcome to WorldOfZhopping!");
        this.transitionScreen.addLine("In this game you are going shopping\nas a given character.\n\nEach character has it's own needs that you\nneed to fulfill.");
        this.transitionScreen.addLine("You are playing as a <PlayerType>.\nThis <PlayerType> needs to at least get 500 calories,\nand you hate <food-types>\nYour budget is DKK 150.");
        this.transitionScreen.addLine("Move around using the WASD or Arrow keys.\nInteract with things using the ENTER key.\nYou can use ESCAPE to quit the game.\n\nHave fun!");
        this.transitionScreen.setActive(true);

        root.setFocusTraversable(true); //Makes onKeyPressed() work.
    }

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
        //All these are similar. The PlayerObject makes sure it is actually possible
        //to move into the given direction.
        //Turns on debug mode.
        //The grid will be shown, alogn with warps.
        switch (keyEvent.getCode()) {
            case S, DOWN -> tryMove(Direction.DOWN);
            case W, UP -> tryMove(Direction.UP);
            case A, LEFT -> tryMove(Direction.LEFT);
            case D, RIGHT -> tryMove(Direction.RIGHT);
            case G -> playerObject.getActiveGrid().setShowDebug(!playerObject.getActiveGrid().isShowDebug());
            case I -> toggleSideMenu();
            case C -> closeShelfMenu();
            case SPACE -> toggleTextBox();
            case ENTER -> interact();
            case ESCAPE -> quit();
            case E -> {
                if(this.transitionScreen.isActive()){
                    this.transitionScreen.advanceAnimationState();
                }
            }
        }

    }

    /**
     * Tries to move the player to a new position.
     * If the new position is a Warp, then the player changes the active Grid, and moves to the Warp's destination
     * @param direction the direction the player should go.
     */
    private void tryMove(Direction direction) {
        if (!locked) {
            Grid currentGrid = playerObject.getActiveGrid();
            Position currentPosition = playerObject.getPlayerPos();
            Position newPosition = currentPosition;
            switch (direction) {
                case UP -> newPosition = new Position(currentPosition.getX(), currentPosition.getY() - 1);
                case DOWN -> newPosition = new Position(currentPosition.getX(), currentPosition.getY() + 1);
                case LEFT -> newPosition = new Position(currentPosition.getX() - 1, currentPosition.getY());
                case RIGHT -> newPosition = new Position(currentPosition.getX() + 1, currentPosition.getY());
            }
            GridObject gridObjectAtNewPosition = currentGrid.getGridObject(newPosition);
            if (gridObjectAtNewPosition instanceof Warp) {
                playerObject.setAnimating(true);
                Warp warp = (Warp) gridObjectAtNewPosition;
                currentGrid.setGridObject(null, currentPosition); //Remove the player from the current grid
                currentGrid.setActive(false); //Stop animating the current grid
                playerObject.setPlayerPos(warp.getPlayerPos()); //Get the player position that the warp sends the player to
                warp.getGrid().setGridObject(playerObject, warp.getPlayerPos()); //Add the player to the new grid
                playerObject.setActiveGrid(warp.getGrid()); //Get the new grid that is being opened
                playerObject.getActiveGrid().setActive(true);//Start animating the new grid.
                playerObject.setAnimating(false);
                return;
            }


            if (!playerObject.isAnimating() && playerObject.getActiveGrid().moveObject(playerObject.getPlayerPos(), newPosition)) {
                playerObject.setPlayerPos(newPosition);
                //If not moving onto the warp, then we just move by calling the grid.
            }
        }
    }

    private void toggleSideMenu(){
        if (!shelfMenu.isVisible()) {
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
        }
    }

    private void toggleTextBox(){
        if (textBox.isVisible()) {
            textBox.setVisible(false);
        }
        shelfMenu.setVisible(false);
        shelfMenu.setManaged(false);
    }

    public void toggleShelfMenu(){
        textBox.setVisible(false);
    }

    public void closeShelfMenu() {
        if (shelfMenu.isVisible()){
            shelfMenu.setVisible(false);
            shelfMenu.setManaged(false);
            sideMenu.setDisable(false);
            sideMenu.setVisible(false);
            textBox.setVisible(false);
            locked = false;
        }
    }

    private void interact(){
        GridObject objectAbovePlayer = playerObject.getActiveGrid().getGridObject(new Position(playerObject.getPlayerPos().getX(), playerObject.getPlayerPos().getY()-1));
        // TODO check whether the player is standing in front of a shelf
        if (objectAbovePlayer instanceof Shelf) {
            Shelf currentShelf = (Shelf) objectAbovePlayer;
            Scene shelfScene = shelfMenu.getScene();
            ListView<IItem> shelfMenuListView = (ListView<IItem>) shelfScene.lookup("#shelfMenuListView");
            shelfMenuListView.getItems().setAll(currentShelf.getItems());

            System.out.println(Arrays.toString(Collections.singletonList(currentShelf.getItems()).toArray()));

            sideMenu.setVisible(true);
            sideMenu.setDisable(true);
            shelfMenu.setVisible(true);
            shelfMenu.setManaged(true);
            shelfMenuListView.requestFocus();
            locked = true;
        }else if(objectAbovePlayer instanceof Cashier){
            //TODO checkout
            System.out.println("CASHIER");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("CHECKOUT");
            alert.setHeaderText("do you want to checkout?");
            /*alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {

                    System.out.println(MainGUI.game.doAction(CommandWord.CHECKOUT.toString(),null));
                }
            });*/
            checkoutmenu.setVisible(true);
            checkoutmenu.lookup(".arrow").setStyle("-fx-background-color: red;");
            checkoutmenu.fire();
            checkoutmenu.lookup( ".arrow" ).setStyle( "-fx-background-insets: 0; -fx-padding: 0; -fx-shape: null;" );
            locked = true;
        }
    }

    private void quit(){
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
    }

    public void checkoutButtonHandle(ActionEvent actionEvent) {
        if(actionEvent.getSource()==yesButton){
            checkoutmenu.setText("Thank you, come again!");

            //set timer for message.
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(1.2), event -> close() );
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(keyFrame);

            timeline.play();

            String result = MainGUI.game.canCheckout();
            if(result == null){
                ArrayList<String> resultArray = MainGUI.game.Checkout();
                this.transitionScreen.reset();

                this.transitionScreen.addText(resultArray);
                this.transitionScreen.addLine(MainGUI.game.getPlayer().getPlayerType().getDescription());
                this.transitionScreen.addLine("Happy shopping!");
                playerObject.getActiveGrid().setActive(false);
                this.transitionScreen.setActive(true);
            }
        }
        else if(actionEvent.getSource() == noButton){
            close();
        }
    }

    void close(){
        checkoutmenu.setVisible(false);
        locked = false;
    }
}

