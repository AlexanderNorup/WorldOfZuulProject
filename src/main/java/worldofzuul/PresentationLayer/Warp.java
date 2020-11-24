package worldofzuul.PresentationLayer;

public class Warp extends GridObject {

    private Grid grid;
    private Position playerPos;

    /**
     * Can teleport the player around and between rooms/grids.
     * @param newGrid The Grid the player should be teleported to
     * @param playerPosition The position on the new grid where the player has to be teleported.
     */
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
