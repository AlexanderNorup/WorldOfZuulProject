package worldofzuul.PresentationLayer.Controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import worldofzuul.DomainLayer.Interfaces.*;
import worldofzuul.PresentationLayer.*;
import worldofzuul.PresentationLayer.GridObjects.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * Is the controller for the main GameCanvas.
 * Loading this controller will start the game.
 */
public class GameCanvasController {

    public MenuButton checkoutmenu;
    public MenuItem yesButton;
    public MenuItem noButton;
    private PlayerObject playerObject;
    private HashMap<IRoom, Grid> gridMap;
    private HashMap<Grid, IRoom> iRoomMap;
    private Transition transitionScreen;
    private static boolean locked;
    private Grid startingGrid;
    private PresentationHub hub;


    @FXML
    Pane root;

    @FXML
    Canvas gameCanvas;

    @FXML
    Pane sideMenu;

    @FXML
    Pane shelfMenu;

    @FXML
    Pane textBox;

    @FXML
    TextArea textArea;

    /**
     * This method gets called as soon as this Controller is loaded.
     */
    @FXML
    public void initialize(){
        hub = PresentationHub.getInstance();
        hub.setSideMenu(sideMenu);
        hub.setShelfMenu(shelfMenu);
        hub.setTextBox(textBox);
        hub.setTextBoxTextArea(textArea);


        //TODO: Canvas has width and height hardcoded. Do something about that, yes?
        gridMap = new HashMap<>();
        iRoomMap = new HashMap<>();
        IPlayer player = hub.getGame().getPlayer();

        //Make hashMap of rooms and grids
        for (IRoom iRoom : hub.getGame().getRooms()) {
            Grid grid = new Grid(gameCanvas, iRoom.getWidth(), iRoom.getHeight(), new Image(iRoom.getBackground()));

            for (IRoomObject object : iRoom.getObjects()) {
                if (object instanceof IShelf) {
                    //Represents a Shelf that can contain items
                    grid.setGridObject(new Shelf(((IShelf) object).getItems()), new Position(object.getXPosition(), object.getYPosition()));
                } else if (object instanceof ICashier) {
                    //Represents Is a Cashier you can pay/checkout atf
                    grid.setGridObject(new Cashier(), new Position(object.getXPosition(), object.getYPosition()));
                } else if (object instanceof IWall) {
                    //Makes a wall. Or simply sets a tile impassable.
                    grid.setGridObject(new Wall(), new Position(object.getXPosition(), object.getYPosition()));
                }
            }

            gridMap.put(iRoom, grid);
            iRoomMap.put(grid, iRoom);
        }

        //Sets Warps between the rooms.
        for (IRoom iRoom : gridMap.keySet()) {
            for (IRoomObject object : iRoom.getObjects()) {
                if (object instanceof IWarp) {
                    IWarp iWarp = (IWarp) object;
                    Warp warp = new Warp(gridMap.get(iWarp.getDestination()), new Position(iWarp.getDestX(), iWarp.getDestY()));
                    gridMap.get(iRoom).setGridObject(warp, new Position(iWarp.getXPosition(), iWarp.getYPosition()));
                }
            }
        }


        //Places the player on the grid.
        IRoom startingRoom = player.getStartingRoom();
        startingGrid = gridMap.get(startingRoom); //Finds the Grid that represents the startingRoom by a HashMap
        playerObject = new PlayerObject(startingGrid, new Position(player.getStartingX(), player.getStartingY()));
        playerObject.setAvatarImg(new Image(player.getSprite()));


        //Transition Work!
        locked = true;
        this.transitionScreen = new Transition(gameCanvas);
        this.transitionScreen.setDoneHandler(new IAnimationDoneHandler() {
            @Override
            public void animationDone() {
                startingGrid.setActive(true);
                locked = false;
            }
        });

        this.transitionScreen.addLine("Welcome to World Of Zhopping!");
        this.transitionScreen.addLine("In this game you are going shopping\nas a given character.\n\nEach character has it's own needs that you\nneed to fulfill.");
        this.transitionScreen.addLine(hub.getGame().getPlayerDescription());
        this.transitionScreen.addLine("Move around using the WASD or Arrow keys.\nInteract with things using the ENTER key.\nPress 'I' to open your inventory.\nYou can use ESCAPE to quit the game.\n\nHave fun!");
        this.transitionScreen.setActive(true);

        root.setFocusTraversable(true); //Makes onKeyPressed() work.
    }

    /**
     * This method runs every time the user pressed any key on their keyboard, while the game
     * is focused.
     *
     * @param keyEvent Represents the key being pressed along with more information.
     */
    @FXML
    public void onKeyPressed(KeyEvent keyEvent) throws IOException {

        switch (keyEvent.getCode()) {
            case S, DOWN -> {
                if (!locked) this.playerObject.tryMove(Direction.DOWN);
            }
            case W, UP -> {
                if (!locked) this.playerObject.tryMove(Direction.UP);
            }
            case A, LEFT -> {
                if (!locked) this.playerObject.tryMove(Direction.LEFT);
            }
            case D, RIGHT -> {
                if (!locked) this.playerObject.tryMove(Direction.RIGHT);
            }
            case G -> playerObject.getActiveGrid().setShowDebug(!playerObject.getActiveGrid().isShowDebug());
            case I -> toggleSideMenu();
            case C -> closeShelfMenu();
            case SPACE -> toggleTextBox();
            case ENTER -> interact();
            case ESCAPE -> quitAlert();
            case E -> {
                if (this.transitionScreen.isActive()) {
                    this.transitionScreen.advanceAnimationState();
                }
            }
        }

    }


    private void toggleSideMenu() {
        if (!shelfMenu.isVisible() && !locked) {
            hub.playSoundEffect("inventory.wav");
            if (sideMenu.isVisible()) {
                sideMenu.setVisible(false);
                sideMenu.setManaged(false);
            } else {
                sideMenu.setVisible(true);
                sideMenu.setManaged(true);
                ListView<IItem> sideMenuListView = hub.getSideMenuListView();
                sideMenuListView.requestFocus();
                sideMenuListView.getItems().setAll(hub.getGame().getPlayer().getInventory());
            }
        }
    }

    private void toggleTextBox() {
        if (textBox.isVisible()) {
            textBox.setVisible(false);
        }
        shelfMenu.setVisible(false);
        shelfMenu.setManaged(false);
    }

    public void toggleShelfMenu() {
        textBox.setVisible(false);
    }

    public void closeShelfMenu() {
        if (shelfMenu.isVisible()) {
            shelfMenu.setVisible(false);
            shelfMenu.setManaged(false);
            sideMenu.setDisable(false);
            sideMenu.setVisible(false);
            textBox.setVisible(false);
            hub.playSoundEffect("select.wav");
            locked = false;
        }
    }

    private void interact() {
        if (locked) {
            return;
        }
        GridObject objectAbovePlayer = playerObject.getActiveGrid().getGridObject(new Position(playerObject.getPlayerPos().getX(), playerObject.getPlayerPos().getY() - 1));
        // TODO check whether the player is standing in front of a shelf
        if (objectAbovePlayer instanceof Shelf) {
            Shelf currentShelf = (Shelf) objectAbovePlayer;
            hub.getShelfMenuListView().getItems().setAll(currentShelf.getItems());

            System.out.println(Arrays.toString(Collections.singletonList(currentShelf.getItems()).toArray()));

            sideMenu.setVisible(true);
            sideMenu.setDisable(true);
            shelfMenu.setVisible(true);
            shelfMenu.setManaged(true);
            hub.getShelfMenuListView().requestFocus();
            hub.playSoundEffect("select.wav");
            locked = true;

        } else if (objectAbovePlayer instanceof Cashier) {
            System.out.println("CASHIER");
            checkoutmenu.setPrefWidth(160);
            checkoutmenu.setText("Do you wanna checkout?");
            checkoutmenu.setVisible(true);
            checkoutmenu.lookup(".arrow").setStyle("-fx-background-color: red;");
            checkoutmenu.fire();
            checkoutmenu.lookup( ".arrow" ).setStyle( "-fx-background-insets: 0; -fx-padding: 0; -fx-shape: null;" );
            hub.playSoundEffect("select.wav");
            locked = true;
        }
    }

    private void quitAlert() {
        //Prompts the user if they want to exit.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit the game?");
        alert.setHeaderText("Do you want to quit the game?");
        alert.setContentText("You will lose your unsaved progress.");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) quit();

        });
    }

    private void quit() {
        //TODO go to main menu
        Parent mainMenu = null;
        try {
            mainMenu = FXMLLoader.load(MainGUI.class.getResource("/fxml/mainmenu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        hub.getPrimaryStage().setScene(new Scene(mainMenu, 1280,720));
    }

    public void checkoutButtonHandle(ActionEvent actionEvent) {
        if(actionEvent.getSource() == yesButton){
            ICheckoutReturnObject object = hub.getGame().Checkout();

            if(!object.didCheckout()){
                TextArea textArea = hub.getTextBoxTextArea();
                textArea.setText(object.getReturnString().get(0));
                textArea.getParent().setVisible(true);
                checkoutmenu.setText("You can't checkout!");
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(2.5), event -> close());
                Timeline timeline = new Timeline();
                timeline.getKeyFrames().add(keyFrame);
                timeline.play();
            } else if (!object.isGameOver()) {
                checkoutmenu.setText("Thank you, come again!");

                //reset game
                hub.getSideMenuListView().getItems().setAll(hub.getGame().getPlayer().getInventory());

                locked = true;
                //set timer for message.
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(2.5), event -> newGameTransition(object.getReturnString()));
                Timeline timeline = new Timeline();
                timeline.getKeyFrames().add(keyFrame);

                timeline.play();
            } else {
                //GameOver
                locked = true;
                //set timer for message.
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(2.5), event -> gameOverTransition(object.getReturnString()));
                Timeline timeline = new Timeline();
                timeline.getKeyFrames().add(keyFrame);

                timeline.play();
            }
        } else if (actionEvent.getSource() == noButton) {
            close();
        }
        hub.playSoundEffect("select.wav");
    }

    void close() {
        checkoutmenu.setVisible(false);
        locked = false;
    }

    void newGameTransition(ArrayList<String> resultArray) {
        close();

        //[Merge] The following 2 lines may have to be removed.
        IRoom outside = hub.getGame().getRooms().get(0);
        gridMap.get(outside).setBackground(new Image(outside.getBackground()));

        this.transitionScreen.reset();
        transitionScreen.setDoneHandler(new IAnimationDoneHandler() {
            @Override
            public void animationDone() {

                playerObject.getActiveGrid().setActive(false);
                playerObject.getActiveGrid().setGridObject(null, playerObject.getPlayerPos());
                startingGrid.setActive(true);
                playerObject.setActiveGrid(startingGrid);
                playerObject.setPlayerPos(new Position(hub.getGame().getPlayer().getStartingX(), hub.getGame().getPlayer().getStartingY()));
                startingGrid.setGridObject(playerObject, new Position(hub.getGame().getPlayer().getStartingX(), hub.getGame().getPlayer().getStartingY()));
                locked = false;
            }
        });
        this.locked = true;
        this.transitionScreen.addText(resultArray);
        this.transitionScreen.addLine(hub.getGame().getPlayer().getPlayerType().getDescription());
        this.transitionScreen.addLine("Happy shopping!\n\nYour game has been saved!");


        playerObject.getActiveGrid().setActive(false);
        this.transitionScreen.setActive(true);

    }

    void gameOverTransition(ArrayList<String> resultArray) {
        close();

        //[Merge] The following 2 lines may have to be removed.
        IRoom outside = hub.getGame().getRooms().get(0);
        gridMap.get(outside).setBackground(new Image(outside.getBackground()));

        this.transitionScreen.reset();
        transitionScreen.setDoneHandler(new IAnimationDoneHandler() {
            @Override
            public void animationDone() {
                quit();
            }
        });
        this.locked = true;
        this.transitionScreen.addText(resultArray);

        playerObject.getActiveGrid().setActive(false);
        this.transitionScreen.setActive(true);

    }

    public static void setLocked(boolean set) {
        locked = set;
    }
}

