package worldofzuul.PresentationLayer.GridObjects;

import javafx.scene.image.Image;
import worldofzuul.PresentationLayer.Grid;
import worldofzuul.PresentationLayer.Position;

/**
 * Represents the player.
 */
public class PlayerObject extends GridSprite {

    private Grid grid;
    private Position playerPos;
    private Image avatarImg;
    private Image[] walkingSprites;

    /**
     * The Player on the Grid. The one the player controls around.
     * Keeps track of the active grid shown on screen.
     * @param grid The Grid the player starts on.
     * @param startingPos The Position the player starts on.
     */
    public PlayerObject(Grid grid, Position startingPos) {
        super();
        this.grid = grid;
        this.playerPos = startingPos;
        this.grid.setGridObject(this, this.playerPos);
        this.avatarImg = new Image(getClass().getResource("/sprites/avatar.png").toString());
        this.walkingSprites = new Image[2];
        this.walkingSprites[0] = (new Image(getClass().getResource("/sprites/avatar_walking_1.png").toString()));
        this.walkingSprites[1] = (new Image(getClass().getResource("/sprites/avatar_walking_2.png").toString()));
    }

    /**
     * Tries to move the player to a new position.
     * If the new position is a Warp, then the player changes the active Grid, and moves to the Warp's destination
     * @param newPosition The position on the Grid the player should try and move to.
     */
    private void tryMove(Position newPosition) {
        //Start by checking if we're moving onto a warp
        GridObject gridObjectAtNewPosition  = grid.getGridObject(newPosition);
        if(gridObjectAtNewPosition instanceof Warp){
            this.setAnimating(true);
            Warp warp = (Warp) gridObjectAtNewPosition;
            this.grid.setGridObject(null, this.playerPos); //Remove the player from the current grid
            this.grid.setActive(false); //Stop animating the current grid


            this.playerPos = warp.getPlayerPos(); //Get the player position that the warp sends the player to
            this.grid = warp.getGrid(); //Get the new grid that is being opened
            this.grid.setGridObject(this, this.playerPos); //Add the player to the new grid
            this.grid.setActive(true); //Start animating the new grid.
            this.setAnimating(false);
            return;
        }

        //If not moving onto the warp, then we just move by calling the grid.
        if (!this.isAnimating() && grid.moveObject(playerPos, newPosition)) {
            playerPos = newPosition;
        }
    }

    public void moveDown() {
        this.tryMove(new Position(playerPos.getX(), playerPos.getY() + 1));
    }

    public void moveUp() {
        this.tryMove(new Position(playerPos.getX(), playerPos.getY() - 1));
    }

    public void moveRight() {
        this.tryMove(new Position(playerPos.getX() + 1, playerPos.getY()));
    }

    public void moveLeft() {
        this.tryMove(new Position(playerPos.getX() - 1, playerPos.getY()));
    }

    @Override
    public Image getIdleSprite() {
        return avatarImg;
    }

    @Override
    public Image getWalkingSprite() {
        //Because the player has more than 1 walking animation, the animationTimer and currentTimeMillis()
        //is used to periodically switch the sprite.
        int animationTimer = 25;
        if (System.currentTimeMillis() % animationTimer >= animationTimer/2) {
            return this.walkingSprites[0];
        } else {
            return this.walkingSprites[1];
        }
    }

    /**
     * @return The active Grid. The one the player is on, and currently being drawn.
     */
    public Grid getActiveGrid() {
        return grid;
    }
}
