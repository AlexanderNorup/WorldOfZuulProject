package worldofzuul.PresentationLayer;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Grid {
    private ArrayList<AnimationState> animationStates;
    private GridObject[][] grid;
    private int gridWidth, gridHeight, windowHeight, windowWidth;
    private int tileSize, gameWidth, gameHeight;
    private GraphicsContext gc;
    private boolean drawVisibleGrid;
    private AnimationTimer animationTimer;

    private Image background;

    public Grid(Canvas canvas, int gridWidth, int gridHeight, Image background) {
        grid = new GridObject[gridWidth][gridHeight];
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                grid[x][y] = null;
            }
        }
        this.gc = canvas.getGraphicsContext2D();
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
        this.windowHeight = (int) canvas.getHeight();
        this.windowWidth = (int) canvas.getWidth();
        this.tileSize = 75;
        this.gameWidth = tileSize * gridWidth;
        this.gameHeight = tileSize * gridHeight;
        this.drawVisibleGrid = false;
        this.background = background;
        this.animationStates = new ArrayList<>();
        this.animationTimer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                drawGrid();
            }

        };

    }

    public void setActive(boolean active){
        if(active){
            this.animationTimer.start();
        }else{
            this.animationTimer.stop();
        }
    }

    public void setGridObject(GridObject obj, Position pos) {
        this.grid[pos.getX()][pos.getY()] = obj;
    }


    private void drawGrid() {
        //Reimplement

        //Clear entire background
        gc.setFill(Color.BLACK);
        gc.clearRect(0, 0, windowWidth, windowHeight);


        gc.save();
        gc.translate((int) (windowWidth / 2 - (gameWidth / 2)),
                (int) (windowHeight / 2 - (gameHeight / 2)));


        gc.drawImage(background, 0, 0, gameWidth, gameHeight);


        if (drawVisibleGrid) {
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

        }
        gc.restore();

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                if (grid[x][y] instanceof GridSprite           //And the spot is a GridSprite
                        && !((GridSprite) grid[x][y]).isAnimating()) { //And that GridSprite isn't currently animating
                    this.drawObject(((GridSprite) grid[x][y]).getIdleSprite(), this.getPositionGrid(x, y));
                }else if(grid[x][y] instanceof Warp && this.drawVisibleGrid){
                    this.drawObject(new Image(getClass().getResource("/sprites/warp.png").toString()), this.getPositionGrid(x, y));
                }
            }
        }

        //Now we handle if there's any Animations that should currently run:
        ArrayList<AnimationState> doneAnimations = new ArrayList<>();
        for (AnimationState animState : this.animationStates) {
            this.drawObject(animState.getSprite().getWalkingSprite(), animState.getCurrentPosition());
            if (animState.isAnimationDone()) {
                doneAnimations.add(animState);
            }
        }
        this.animationStates.removeAll(doneAnimations);

    }

    private Position getPositionGrid(Position pos) {
        return this.getPositionGrid(pos.getX(), pos.getY());
    }

    private Position getPositionGrid(int gridX, int gridY) {
        double xScaling = gameHeight / gridHeight;
        double yScaling = gameWidth / gridWidth;

        return new Position(gridX * (int) xScaling + (gameHeight / gridHeight / 2) - tileSize / 2,
                gridY * (int) yScaling + (gameWidth / gridWidth / 2) - tileSize / 2);
    }

    private void drawObject(Image sprite, Position pos) {

        gc.save();
        gc.translate((int) (windowWidth / 2 - (gameWidth / 2)),
                (int) (windowHeight / 2 - (gameHeight / 2)));

        gc.drawImage(sprite,
                pos.getX(),
                pos.getY(),
                tileSize,
                tileSize
        );
        gc.restore();
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

    public boolean moveObject(Position currentPos, Position newPos) {
        if (!this.isSpaceFree(newPos)) {
            System.out.println("[Debug] Cannot move. Space is not free!");
            return false;
        }
        GridObject gridObject = this.getGridObject(currentPos);
        if (gridObject == null) {
            System.out.println("[Debug] GridObject is null!");
            return false;
        }
        if (gridObject instanceof GridSprite) {
            if (((GridSprite) gridObject).isAnimating()) {
                System.out.println("[Debug] Cannot move animating object!");
                return false;
            }

            AnimationState animState = new AnimationState((GridSprite) gridObject,
                    this.getPositionGrid(currentPos),
                    this.getPositionGrid(newPos),
                    50);
            this.animationStates.add(animState);
        }

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

    public boolean isDrawVisibleGrid() {
        return drawVisibleGrid;
    }

    public void setDrawVisibleGrid(boolean drawVisibleGrid) {
        this.drawVisibleGrid = drawVisibleGrid;
    }
}
