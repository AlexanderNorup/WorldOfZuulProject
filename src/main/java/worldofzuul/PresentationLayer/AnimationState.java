package worldofzuul.PresentationLayer;

import worldofzuul.PresentationLayer.GridObjects.GridSprite;

public class AnimationState {

    private GridSprite animation;
    private Position originalPos;
    private Position goalPosition;
    private long finishTime;
    private long startTime;
    private boolean animationDone;

    /**
     * Creates a new Animation.
     * This class will calculate a sprite should be in space when given a starting and end position, along with the time it has to get there.
     * @param sprite The SpriteObject to animate.
     * @param currentPos The current position of the sprite
     * @param newPos The position we want the sprite to end up at
     * @param animationLengthMillis The amount of milliseconds it should take to get there.
     */
    public AnimationState(GridSprite sprite, Position currentPos, Position newPos, long animationLengthMillis){
        this.originalPos = currentPos;
        this.goalPosition = newPos;
        this.finishTime = animationLengthMillis;
        this.startTime = System.currentTimeMillis();
        this.animation = sprite;

        //Tells the Sprite that it's being animated.
        this.animation.setAnimating(true);
        this.animationDone = false;

        //System.out.println("[Debug] Start : " + this.originalPos);
        //System.out.println("[Debug] End   : " + this.goalPosition);

    }

    /**
     * This calculates using a Linear easing where in space the sprite is at the current moment.
     * <b>Example:</b>
     * If the `finishTime` to 4000 (4 seconds), and 2 seconds passed since `startTime`, we would want
     * the Sprite to be halfwhere there. Then this method will return the midpoint between `originalPosition` and `goalPosition`.
     * @return The current position
     */
    public Position getCurrentPosition(){
        //animationProgress calculates the progress of the animation.
        //is is a double between 0 and 1, where 0.5 is 50% done, and 1 is 100% done.
        //Is does this based on start-time, the current time, and the end time.
        double animationProgress = (double) (System.currentTimeMillis() - this.startTime) / (double) this.finishTime;
        //If the animation is done, then we tell the sprite to stop-animating.
        if(animationProgress >= 1){
            animationDone = true;
            animation.setAnimating(false);
            return this.goalPosition;
        }
        //System.out.println("[Debug] AnimationProgress: " + animationProgress);

        //We then calculate the differenceVector.
        //The difference vector is the Vector starting at the startPosition and pointing to the end-position.
        int newX = this.goalPosition.getX() - this.originalPos.getX();
        int newY = this.goalPosition.getY() - this.originalPos.getY();
        Position differenceVector = new Position(newX, newY);

        //We then multiply the difference vector with our animation vector
        //and then add the starting position. That will make the sprite slide towards the end-position from the start-position.
        //the higher the animationProgress, the closer to the end. 
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
