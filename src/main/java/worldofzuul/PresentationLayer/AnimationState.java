package worldofzuul.PresentationLayer;

import javafx.scene.image.Image;

public class AnimationState {

    private GridSprite animation;
    private Position originalPos;
    private Position goalPosition;
    private long finishTime;
    private long startTime;
    private boolean animationDone;

    public AnimationState(GridSprite sprite, Position currentPos, Position newPos, long animationLengthMillis){
        this.originalPos = currentPos;
        this.goalPosition = newPos;
        this.finishTime = animationLengthMillis;
        this.startTime = System.currentTimeMillis();
        this.animation = sprite;
        this.animation.setAnimating(true);
        this.animationDone = false;

        //System.out.println("[Debug] Start : " + this.originalPos);
        //System.out.println("[Debug] End   : " + this.goalPosition);

    }

    public Position getCurrentPosition(){
        double animationProgress = (double) (System.currentTimeMillis() - this.startTime) / (double) this.finishTime;
        if(animationProgress >= 1){
            animationDone = true;
            animation.setAnimating(false);
            return this.goalPosition;
        }
        //System.out.println("[Debug] AnimationProgress: " + animationProgress);
        int newX = this.goalPosition.getX() - this.originalPos.getX();
        int newY = this.goalPosition.getY() - this.originalPos.getY();
        Position differenceVector = new Position(newX, newY);
        Position movementVector = new Position((int)(differenceVector.getX()*animationProgress)+this.originalPos.getX(),
                (int)(differenceVector.getY()*animationProgress)+this.originalPos.getY());
        return movementVector;
    }

    public GridSprite getSprite() {
        return animation;
    }

    public boolean isAnimationDone() {
        return animationDone;
    }
}
