package worldofzuul.GUI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlayerObject extends GridObject{

    private Grid grid;
    private Position playerPos;
    public PlayerObject(Grid grid, int x, int y){
        this.grid = grid;
        this.playerPos = new Position(x,y);
        this.grid.setGridObject(this,this.playerPos);
    }

    public void moveDown(){
        Position newPosition = new Position(playerPos.getX(),playerPos.getY() + 1);
        if(playerPos.getY() < grid.getGridHeight()-1 && grid.isSpaceFree(newPosition)) {
            this.grid.setGridObject(null, this.playerPos);
            this.playerPos = newPosition;
            this.grid.setGridObject(this, this.playerPos);
        }
    }

    public void moveUp(){
        Position newPosition = new Position(playerPos.getX(),playerPos.getY() - 1);
        if(playerPos.getY() > 0 && grid.isSpaceFree(newPosition)) {
            this.grid.setGridObject(null, this.playerPos);
            this.playerPos = newPosition;
            this.grid.setGridObject(this, this.playerPos);
        }
    }

    public void moveRight(){
        Position newPosition = new Position(playerPos.getX() + 1 ,playerPos.getY());
        if(playerPos.getX() < grid.getGridWidth()-1 && grid.isSpaceFree(newPosition)) {
            this.grid.setGridObject(null, this.playerPos);
            this.playerPos = newPosition;
            this.grid.setGridObject(this, this.playerPos);
        }
    }

    public void moveLeft(){
        Position newPosition = new Position(playerPos.getX() - 1 ,playerPos.getY());
        if(playerPos.getX() > 0 && grid.isSpaceFree(newPosition)) {
            this.grid.setGridObject(null, this.playerPos);
            this.playerPos = newPosition;
            this.grid.setGridObject(this, this.playerPos);
        }
    }

    @Override
    public ImageView draw() {
        Image avatarImg = new Image(getClass().getResource("/sprites/avatar.png").toString());
        ImageView avatar = new ImageView(avatarImg);
        avatar.setFitHeight(100);
        avatar.setFitWidth(100);
        return avatar;
    }
}
