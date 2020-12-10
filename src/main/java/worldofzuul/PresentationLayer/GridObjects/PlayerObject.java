package worldofzuul.PresentationLayer.GridObjects;

import javafx.scene.image.Image;
import worldofzuul.PresentationLayer.*;

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
        this.avatarImg = new Image(getClass().getResource("/sprites/geoalex.png").toString());
        this.walkingSprites = new Image[2];
        this.walkingSprites[0] = avatarImg;//(new Image(getClass().getResource("/sprites/avatar_walking_1.png").toString()));
        this.walkingSprites[1] = avatarImg;//(new Image(getClass().getResource("/sprites/avatar_walking_2.png").toString()));
    }

    public void setAvatarImg(Image playerSprite){
        this.avatarImg = playerSprite;
    }

    @Override
    public Image getIdleSprite() {
        return avatarImg;
    }

    /*@Override
    public Image getWalkingSprite() {
        //Because the player has more than 1 walking animation, the animationTimer and currentTimeMillis()
        //is used to periodically switch the sprite.
        int animationTimer = 2;
        if (System.currentTimeMillis() % animationTimer >= animationTimer/2) {
            return this.walkingSprites[0];
        } else {
            return this.walkingSprites[1];
        }
    }*/


    /**
     * Tries to move the player to a new position.
     * If the new position is a Warp, then the player changes the active Grid, and moves to the Warp's destination
     * @param direction the direction the player should go.
     */
    public void tryMove(Direction direction) {
        Position newPosition = this.playerPos;
        switch (direction) {
            case UP -> newPosition = new Position(this.playerPos.getX(), this.playerPos.getY() - 1);
            case DOWN -> newPosition = new Position(this.playerPos.getX(), this.playerPos.getY() + 1);
            case LEFT -> newPosition = new Position(this.playerPos.getX() - 1, this.playerPos.getY());
            case RIGHT -> newPosition = new Position(this.playerPos.getX() + 1, this.playerPos.getY());
        }
        GridObject gridObjectAtNewPosition = this.grid.getGridObject(newPosition);
        if (gridObjectAtNewPosition instanceof Warp) {
            this.setAnimating(true);
            Warp warp = (Warp) gridObjectAtNewPosition;
            this.grid.setGridObject(null, this.playerPos); //Remove the player from the current grid
            this.grid.setActive(false); //Stop animating the current grid
            this.playerPos = warp.getPlayerPos(); //Get the player position that the warp sends the player to
            warp.getGrid().setGridObject(this, warp.getPlayerPos()); //Add the player to the new grid
            this.grid = warp.getGrid(); //Get the new grid that is being opened
            this.grid.setActive(true);//Start animating the new grid.
            this.setAnimating(false);
            PresentationHub.getInstance().playSoundEffect("door.wav");
            return;
        }


        if (!this.isAnimating() && this.getActiveGrid().moveObject(this.getPlayerPos(), newPosition)) {
            this.setPlayerPos(newPosition);
            //If not moving onto the warp, then we just move by calling the grid.
        }else{
            PresentationHub.getInstance().playSoundEffect("block.wav");
        }
    }

    /**
     * @return The active Grid. The one the player is on, and currently being drawn.
     */
    public Grid getActiveGrid() {
        return grid;
    }

    public Position getPlayerPos(){
        return playerPos;
    }

    public void setPlayerPos(Position newPosition){
        this.playerPos = newPosition;
    }

    public void setActiveGrid(Grid newGrid){
        grid = newGrid;
    }
}
