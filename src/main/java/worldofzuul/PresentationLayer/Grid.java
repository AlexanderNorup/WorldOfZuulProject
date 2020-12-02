package worldofzuul.PresentationLayer;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import worldofzuul.PresentationLayer.GridObjects.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Grid {
    /**
     * Keeps track of what has to be animated.
     */
    private ArrayList<AnimationState> animationStates;

    /**
     * A 2D array keeping track of the grid.
     */
    private GridObject[][] grid;

    //GridWidth and GridHeight is the amount of rows and coloums in our grid
    //WindowHeight and WindowWidth is the width and height of the entire canvas
    private int gridWidth, gridHeight, windowHeight, windowWidth;

    //TileSize is how big each of our tiles in our grid is.
    //The gameWidth and gameHeight represents the height and width of the box in the middle of the screen
    // in which the grid is shown.
    private int tileSize, gameWidth, gameHeight;

    /**
     * The GraphicContext is what allows the Grid to actually draw onto the grid.
     */
    private GraphicsContext gc;

    /**
     * If true, the grid-lines will be shonw
     */
    private boolean showDebug;

    /**
     * A JavaFX class. Calls drawGrid() on every frame whenever active.
     */
    private AnimationTimer animationTimer;

    /**
     * The background image used on this grid.
     */
    private Image background;

    /**
     * Represents a grid.
     * Has a background, and can move things around in it.
     * @param canvas A JavaFX Canvas to draw onto.
     * @param gridWidth The width of the grid.
     * @param gridHeight The height of the grid.
     * @param background A background image for this grid.
     */
    public Grid(Canvas canvas, int gridWidth, int gridHeight, Image background) {
        //Starts off by filling the grid with null
        grid = new GridObject[gridWidth][gridHeight];

        //Then grabs the GraphicsContext from the Canvas.
        this.gc = canvas.getGraphicsContext2D();


        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
        this.windowHeight = (int) canvas.getHeight();
        this.windowWidth = (int) canvas.getWidth();

        //tileSize is hardcoded for now. Represents how big each tile is.
        //Everything will scale accordingly.
        this.tileSize = 100;
        this.gameWidth = tileSize * gridWidth;
        this.gameHeight = tileSize * gridHeight;

        this.showDebug = false;
        this.background = background;

        //Keeps track of the Sprites currently being animated.
        this.animationStates = new ArrayList<>();

        //Sets up the AnimationTimer. By default it is not enabled.
        //When enabled/started, it will call handle(), which in turn will call drawGrid() every frame.
        this.animationTimer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                drawGrid();
            }

        };

    }

    /**
     * Enables or disables the drawing of the grid.
     * @param active If true, the Grid will begin to paint the Canvas.
     */
    public void setActive(boolean active){
        if(active){
            this.animationTimer.start();
        }else{
            this.animationTimer.stop();
        }
    }

    /**
     * Adds a GridObject to the Grid at a given position.
     * @param obj The GridObject to add.
     * @param pos Where on the grid to add it. Based on "Grid-Coordinates".
     */
    //NON-ACCESSIBLE OBJECT IS EXPOSED ?
    public void setGridObject(GridObject obj, Position pos) {
        this.grid[pos.getX()][pos.getY()] = obj;
    }

    /**
     * Runs every frame when the Grid is "active".
     * The Grid can be set active or inactive from the setActive() method.
     */
    private void drawGrid() {

        //Clear entire background
        gc.clearRect(0, 0, windowWidth, windowHeight);

        //Then draw the background
        gc.save();
        gc.translate((int) (windowWidth / 2 - (gameWidth / 2)), //forklar tak
                (int) (windowHeight / 2 - (gameHeight / 2)));

        gc.drawImage(background, 0, 0, gameWidth, gameHeight);

        //If debug is turned on:
        if (showDebug) {
            //Draws a visible grid on the screen
            gc.setLineWidth(1);
            gc.setStroke(Color.GREY);


            for (int col = 0; col < gridWidth + 1; col++) {
                gc.strokeLine(col * tileSize, 0,
                        col * tileSize, gameHeight);
            }

            for (int row = 0; row < gridHeight + 1; row++) {
                gc.strokeLine(0, row * tileSize,
                        gameWidth, row * tileSize);
            }

            gc.setFont(new Font(16));
            gc.fillText("DEBUG VIEW IS ON (Press G)", 0, -16);

        }
        gc.restore(); //Restores the translation


        //Now it's time to draw the GridSprite objects in our grid variable.
        //But we only draw the Sprites which are NOT currently animating.
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                if (grid[x][y] instanceof GridSprite                   //If the spot is a GridSprite
                        && !((GridSprite) grid[x][y]).isAnimating()) { //And that GridSprite isn't currently animating
                    //Draw the SpriteObject
                    this.drawObject(((GridSprite) grid[x][y]).getIdleSprite(), this.getPositionGrid(new Position(x, y)));
                }else if(this.showDebug){
                    //If debug-mode is turned on, then draw some of the invisible things
                    if(grid[x][y] instanceof Warp) {
                        this.drawObject(new Image(getClass().getResource("/sprites/warp.png").toString()), this.getPositionGrid(new Position(x, y)));
                    }else if(grid[x][y] instanceof Shelf) {
                        this.drawObject(new Image(getClass().getResource("/sprites/shelf.png").toString()), this.getPositionGrid(new Position(x, y)));
                    }else if(grid[x][y] instanceof Wall) {
                        this.drawObject(new Image(getClass().getResource("/sprites/wall.png").toString()), this.getPositionGrid(new Position(x, y)));
                    }
                }
            }
        }

        //Now we handle if there's any Animations that should currently run:
        ArrayList<AnimationState> doneAnimations = new ArrayList<>();
        for (AnimationState animState : this.animationStates) {
            //For each animation, get the currentPosition.
            this.drawObject(animState.getSprite().getWalkingSprite(), animState.getCurrentPosition());
            if (animState.isAnimationDone()) {
                doneAnimations.add(animState);
            }
        }
        //Remove the animations that are done.
        this.animationStates.removeAll(doneAnimations);

    }

    /**
     * Converts from "Grid-Coordinates" to "Game coordinates".
     * A "Grid-coordinate" could be like: Position(1,2), which would be the third row in the second coloumn.
     * This method will use the Canvas information and tileSize to calculate where the game coordinates is. That means the exact coordinates on the game-area the grid-coordinate is.
     * @param pos The "grid-coordinates" to convert
     * @return The absoloute position in the game-window, this grid-coordinate is.
     */
    private Position getPositionGrid(Position pos) {
        double xScaling = gameHeight / gridHeight;
        double yScaling = gameWidth / gridWidth;

        return new Position(pos.getX() * (int) xScaling + (gameHeight / gridHeight / 2) - tileSize / 2,
                pos.getY() * (int) yScaling + (gameWidth / gridWidth / 2) - tileSize / 2);
    }

    /**
     * Draws a Sprite at Absoloute Coordinates.
     * @param sprite The sprite to draw
     * @param pos Where to draw it.
     */
    private void drawObject(Image sprite, Position pos) {

        //Starts by translating the coordinates into the GameWindow
        gc.save();
        gc.translate((int) (windowWidth / 2 - (gameWidth / 2)),
                (int) (windowHeight / 2 - (gameHeight / 2)));

        gc.drawImage(sprite,
                pos.getX(),
                pos.getY(),
                tileSize,
                tileSize
        );
        gc.restore(); //Undoes the translation
    }

    private boolean isInsideGrid(Position pos) {
        return !(pos.getX() >= gridWidth || pos.getX() < 0 ||
                pos.getY() >= gridHeight || pos.getY() < 0);

    }

    private boolean isSpaceFree(Position pos) {
        if (isInsideGrid(pos)) {
            return this.grid[pos.getX()][pos.getY()] == null;
        }
        return false;
    }

    public GridObject getGridObject(Position pos) {
        if (!isInsideGrid(pos)) {
            return null;
        }
        return this.grid[pos.getX()][pos.getY()];
    }

    /**
     * Moves a GridObject around inside the grid.
     * If the GridObject is an instance of SpriteObject, the sprite is animated to the new position.
     * @param currentPos The position of the thing you want to move.
     * @param newPos The position you want to move to
     * @return True if the move was a success. False if the move was interrupted.
     */
    public boolean moveObject(Position currentPos, Position newPos) {
        if (!this.isSpaceFree(newPos)) {
            //System.out.println("[Debug] Cannot move. Space is not free!");
            return false;
        }
        GridObject gridObject = this.getGridObject(currentPos);
        if (gridObject == null) {
            //System.out.println("[Debug] GridObject is null!");
            return false;
        }
        if (gridObject instanceof GridSprite) {
            if (((GridSprite) gridObject).isAnimating()) {
                //System.out.println("[Debug] Cannot move animating object!");
                return false;
            }

            //We now need to animate the Sprite.
            //By default animationLength is really short.
            AnimationState animState = new AnimationState((GridSprite) gridObject,
                    this.getPositionGrid(currentPos),
                    this.getPositionGrid(newPos),
                    50);
            //Then add it to the animationStates list, so the drawGrid() mehhod will draw the item correctly.
            this.animationStates.add(animState);
        }

        //Then move it on the grid
        this.setGridObject(gridObject, newPos);
        this.setGridObject(null, currentPos);


        return true;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public int getGameWidth() {
        return gameWidth;
    }

    public int getGameHeight() {
        return gameHeight;
    }

    public boolean isShowDebug() {
        return showDebug;
    }

    public void setShowDebug(boolean showDebug) {
        this.showDebug = showDebug;
    }
}
