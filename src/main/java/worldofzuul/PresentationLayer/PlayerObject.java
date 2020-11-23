package worldofzuul.PresentationLayer;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class PlayerObject extends GridSprite {

    private Grid grid;
    private Position playerPos;
    private Image avatarImg;
    private Image[] walkingSprites;

    public PlayerObject(Grid grid, int x, int y) {
        super();
        this.grid = grid;
        this.playerPos = new Position(x, y);
        this.grid.setGridObject(this, this.playerPos);
        this.avatarImg = new Image(getClass().getResource("/sprites/avatar.png").toString());
        this.walkingSprites = new Image[2];
        this.walkingSprites[0] = (new Image(getClass().getResource("/sprites/avatar_walking_1.png").toString()));
        this.walkingSprites[1] = (new Image(getClass().getResource("/sprites/avatar_walking_2.png").toString()));
    }

    private void tryMove(Position newPosition) {
        //Start by checking if we're moving onto a warp
        GridObject gridObjectAtNewPosition  = grid.getGridObject(newPosition);
        if(gridObjectAtNewPosition instanceof Warp){
            Warp warp = (Warp) gridObjectAtNewPosition;
            this.grid.setGridObject(null, this.playerPos); //Remove the player from the current grid
            this.grid.setActive(false); //Stop animating the current grid


            this.playerPos = warp.getPlayerPos(); //Get the player position that the warp sends the player to
            this.grid = warp.getGrid(); //Get the new grid that is being opened
            this.grid.setGridObject(this, this.playerPos); //Add the player to the new grid
            this.grid.setActive(true); //Start animating the new grid.
            return;
        }

        //If not moving onto the warp, then we just move by calling the grid.
        if (grid.moveObject(playerPos, newPosition)) {
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
        int animationTimer = 25;
        if (System.currentTimeMillis() % animationTimer >= animationTimer/2) {
            return this.walkingSprites[0];
        } else {
            return this.walkingSprites[1];
        }
    }

    public Grid getActiveGrid() {
        return grid;
    }
}
