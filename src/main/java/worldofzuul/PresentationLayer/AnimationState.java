package worldofzuul.PresentationLayer;

import javafx.scene.image.Image;

public class AnimationState {

    private GridSprite animation;
    private Position originalPos;
    private Position goalPosition;
    private long finishTime;
    private long startTime;

    public AnimationState(GridSprite sprite, Position currentPos, Position newPos, long time){
        this.originalPos = currentPos;
        this.goalPosition = newPos;
        this.finishTime = time;
        this.startTime = System.currentTimeMillis();
        this.animation = sprite;
        this.animation.setAnimating(true);
    }

    public Position getCurrentPosition(){




        return new Position(0,0);
    }

}
