package worldofzuul.PresentationLayer;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class Grid {
    private GridObject[][] grid;
    private int gridWidth, gridHeight, windowHeight, windowWidth;
    private Pane root;
    public Grid(Pane root, int gridWidth, int gridHeight, int windowHeight, int windowWidth){
        grid = new GridObject[gridWidth][gridHeight];
        for(int x = 0; x < grid.length; x++){
            for(int y = 0; y < grid[x].length; y++){
                grid[x][y] = null;
            }
        }
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;
        this.root = root;
    }

    public void setGridObject(GridObject obj, Position pos){
        this.grid[pos.getX()][pos.getY()] = obj;
    }

    public boolean isSpaceFree(Position pos){
        return this.grid[pos.getX()][pos.getY()] == null;
    }

    public void drawGrid(){
        root.getChildren().clear();
        for(int x = 0; x < grid.length; x++){
            for(int y = 0; y < grid[x].length; y++){
                if(grid[x][y] != null){
                    this.drawObject(grid[x][y], getPositionGrid(x,y));
                }
            }
        }
    }

    private Position getPositionGrid(int gridX, int gridY){
        double xScaling = windowHeight / gridHeight;
        double yScaling = windowWidth / gridWidth;

        return new Position(gridX*(int)xScaling, gridY*(int)yScaling);
    }

    private void drawObject(GridObject obj,Position pos){
        AnchorPane spriteContainer = new AnchorPane();
        ImageView sprite = obj.draw();
        spriteContainer.setLayoutX(pos.getX() + (windowHeight / gridHeight / 2) - sprite.getFitHeight()/2 );
        spriteContainer.setLayoutY(pos.getY() + (windowWidth / gridWidth / 2) - sprite.getFitWidth()/2);
        spriteContainer.getChildren().add(sprite);
        root.getChildren().add(spriteContainer);
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }
}
