package worldofzuul.PresentationLayer.Controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import worldofzuul.DomainLayer.Interfaces.*;
import worldofzuul.DomainLayer.Item;
import worldofzuul.PresentationLayer.*;
import worldofzuul.PresentationLayer.GridObjects.*;

import java.net.URL;
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
    private Grid startingGrid;


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

            for(IRoomObject object : iRoom.getObjects()){
                if(object instanceof IWall){
                    grid.setGridObject(new Wall(), new Position(object.getXPosition(), object.getYPosition()));
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

        startingGrid = gridMap.get(startingRoom);


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
                locked = false;
            }
        });

        this.transitionScreen.addLine("Welcome to World Of Zhopping!");
        this.transitionScreen.addLine("In this game you are going shopping\nas a given character.\n\nEach character has it's own needs that you\nneed to fulfill.");
        this.transitionScreen.addLine(MainGUI.game.getPlayerDescription());
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
        if (!locked && this.transitionScreen.isAnimationDone()) {
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
                MainGUI.playSoundEffect("door.wav");
                return;
            }


            if (!playerObject.isAnimating() && playerObject.getActiveGrid().moveObject(playerObject.getPlayerPos(), newPosition)) {
                playerObject.setPlayerPos(newPosition);
                //If not moving onto the warp, then we just move by calling the grid.
            }else{
                MainGUI.playSoundEffect("block.wav");
            }
        }else{
            MainGUI.playSoundEffect("select.wav");
        }
    }

    private void toggleSideMenu(){
        if (!shelfMenu.isVisible()) {
            MainGUI.playSoundEffect("inventory.wav");
            if (sideMenu.isVisible()) {
                sideMenu.setVisible(false);
                sideMenu.setManaged(false);
            } else {
                sideMenu.setVisible(true);
                sideMenu.setManaged(true);
                Scene sideScene = sideMenu.getScene();
                ListView<IItem> sideMenuListView = (ListView<IItem>) sideScene.lookup("#sideMenuListView");
                sideMenuListView.requestFocus();
                ObservableList<IItem> listViewList = FXCollections.observableArrayList();
                listViewList.addAll(MainGUI.game.getPlayer().getInventory());
                sideMenuListView.setItems(listViewList);

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
            MainGUI.playSoundEffect("select.wav");
            locked = false;
        }
    }

    private void interact(){
        if(locked){return;}
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
            MainGUI.playSoundEffect("select.wav");
            locked = true;
        }else if(objectAbovePlayer instanceof Cashier){
            //TODO checkout

            System.out.println("CASHIER");
            checkoutmenu.setText("Do you wanna checkout?");
            checkoutmenu.setVisible(true);
            checkoutmenu.lookup(".arrow").setStyle("-fx-background-color: red;");
            checkoutmenu.fire();
            checkoutmenu.lookup( ".arrow" ).setStyle( "-fx-background-insets: 0; -fx-padding: 0; -fx-shape: null;" );
            MainGUI.playSoundEffect("select.wav");
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
            String result= MainGUI.game.canCheckout();

            if(result == null) {

                checkoutmenu.setText("Thank you, come again!");
                this.locked = true;
                //set timer for message.
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(1.2), event -> transition());
                Timeline timeline = new Timeline();
                timeline.getKeyFrames().add(keyFrame);

                timeline.play();
            }
            else {
                checkoutmenu.setText(result);
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(1.2),event -> close()
                );
                Timeline timeline = new Timeline();
                timeline.getKeyFrames().add(keyFrame);
                timeline.play();

            }


            //}
        }
        else if(actionEvent.getSource() == noButton){
            close();
        }
        MainGUI.playSoundEffect("select.wav");
    }

    void close(){
        checkoutmenu.setVisible(false);
        locked = false;
    }

    void transition(){
       close();

        String result = MainGUI.game.canCheckout();
        //if(result == null){
        ArrayList<String> resultArray = MainGUI.game.Checkout();
        this.transitionScreen.reset();
        transitionScreen.setDoneHandler(new AnimationDoneHandler() {
            @Override
            public void animationDone() {

                playerObject.getActiveGrid().setActive(false);
                playerObject.getActiveGrid().setGridObject(null, playerObject.getPlayerPos());
                startingGrid.setActive(true);
                playerObject.setActiveGrid(startingGrid);
                playerObject.setPlayerPos(new Position(MainGUI.game.getPlayer().getStartingX(), MainGUI.game.getPlayer().getStartingY()));
                startingGrid.setGridObject(playerObject, new Position(MainGUI.game.getPlayer().getStartingX(), MainGUI.game.getPlayer().getStartingY()));
                locked = false;
            }
        });
        this.locked = true;
        this.transitionScreen.addText(resultArray);
        this.transitionScreen.addLine(MainGUI.game.getPlayer().getPlayerType().getDescription());
        this.transitionScreen.addLine("Happy shopping!\n\nYour game has been saved!");
        playerObject.getActiveGrid().setActive(false);
        this.transitionScreen.setActive(true);

    }
}

