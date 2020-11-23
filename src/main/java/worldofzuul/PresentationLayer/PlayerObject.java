package worldofzuul.PresentationLayer;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlayerObject extends GridSprite{

    private Grid grid;
    private Position playerPos;
    public PlayerObject(Grid grid, int x, int y){
        super();
        this.grid = grid;
        this.playerPos = new Position(x,y);
        this.grid.setGridObject(this,this.playerPos);
    }

    private void tryMove(Position newPosition){
        if(grid.moveObject(playerPos, newPosition)){
            playerPos = newPosition;
        }
    }

    public void moveDown(){
        this.tryMove(new Position(playerPos.getX(),playerPos.getY() + 1));
    }

    public void moveUp(){
        this.tryMove(new Position(playerPos.getX(),playerPos.getY() - 1));
    }

    public void moveRight(){
        this.tryMove(new Position(playerPos.getX() + 1 ,playerPos.getY()));
    }

    public void moveLeft(){
        this.tryMove(new Position(playerPos.getX() - 1 ,playerPos.getY()));
    }

    @Override
    public Image getSprite() {
        Image avatarImg = new Image(getClass().getResource("/sprites/avatar.png").toString());
        return avatarImg;
    }
}
