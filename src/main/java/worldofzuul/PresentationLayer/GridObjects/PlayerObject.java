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
