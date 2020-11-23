package worldofzuul.PresentationLayer;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.function.DoubleToIntFunction;

public class Grid {
    private GridObject[][] grid;
    private int gridWidth, gridHeight, windowHeight, windowWidth;
    private int tileSize, gameWidth, gameHeight;
    private GraphicsContext gc;
    private boolean drawVisibleGrid;

    private Image background;

    public Grid(Canvas canvas, int gridWidth, int gridHeight){
        grid = new GridObject[gridWidth][gridHeight];
        for(int x = 0; x < grid.length; x++){
            for(int y = 0; y < grid[x].length; y++){
                grid[x][y] = null;
            }
        }
        this.gc = canvas.getGraphicsContext2D();
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
        this.windowHeight = (int)canvas.getHeight();
        this.windowWidth = (int)canvas.getWidth();
        this.tileSize = 75;
        this.gameWidth = tileSize * gridWidth;
        this.gameHeight = tileSize * gridHeight;
        this.drawVisibleGrid = false;
        this.background = new Image(MainGUI.class.getResource("/backgrounds/pink.png").toString());

        new AnimationTimer(){

            @Override
            public void handle(long now) {
                drawGrid();
            }

        }.start();

    }

    public void setGridObject(GridObject obj, Position pos){
        this.grid[pos.getX()][pos.getY()] = obj;
    }



    public void drawGrid(){
        //Reimplement

        //Clear entire background
        gc.setFill(Color.BLACK);
        gc.rect(0,0, windowWidth, windowHeight);


        gc.save();
        gc.translate((int) (windowWidth / 2 - (gameWidth / 2)),
                (int) (windowHeight / 2 - (gameHeight / 2)));


        gc.drawImage(background, 0,0,gameWidth, gameHeight);



        if(drawVisibleGrid) {
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

        for(int x = 0; x < grid.length; x++){
            for(int y = 0; y < grid[x].length; y++){
                if(grid[x][y] != null && grid[x][y] instanceof GridSprite){
                    this.drawObject((GridSprite)grid[x][y], this.getPositionGrid(x,y));
                }
            }
        }
    }

    private Position getPositionGrid(int gridX, int gridY){
        double xScaling = gameHeight / gridHeight;
        double yScaling = gameWidth / gridWidth;

        return new Position(gridX*(int)xScaling + (gameHeight / gridHeight / 2) - tileSize/2,
                gridY*(int)yScaling + (gameWidth / gridWidth / 2) - tileSize/2);
    }

    private void drawObject(GridSprite obj,Position pos){
        Image sprite = obj.getSprite();

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

    private boolean isInsideGrid(Position pos){
        return !( pos.getX() >= gridWidth || pos.getX() < 0 ||
                pos.getY() >= gridHeight || pos.getY() < 0 );

    }

    public boolean isSpaceFree(Position pos){
        if(isInsideGrid(pos)){
            return this.grid[pos.getX()][pos.getY()] == null;
        }
        return false;
    }

    private GridObject getObjectAtPosition(Position pos){
        if(!isInsideGrid(pos)){
            return null;
        }
        return this.grid[pos.getX()][pos.getY()];
    }

    public boolean moveObject(Position currentPos, Position newPos){
        if(!this.isSpaceFree(newPos)){
            System.out.println("[Debug] Cannot move. Space is not free!");
            return false;
        }
        GridObject gridObject = this.getObjectAtPosition(currentPos);
        if(gridObject == null){
            System.out.println("[Debug] GridObject is null!");
            return false;
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
