package worldofzuul.PresentationLayer;

import javafx.scene.canvas.Canvas;

public class Warp extends GridObject {

    private Grid grid;
    private Position playerPos;

    public Warp(Grid newGrid, Position playerPosition){
        this.grid = newGrid;
        this.playerPos = playerPosition;
    }

    public Grid getGrid() {
        return this.grid;
    }

    public Position getPlayerPos() {
        return this.playerPos;
    }
}
