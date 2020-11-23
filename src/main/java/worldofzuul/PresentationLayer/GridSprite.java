package worldofzuul.PresentationLayer;

import javafx.scene.image.Image;

public abstract class GridSprite extends GridObject {
    /**
     * The default Image shown when the sprite is stationary.
     * @return The Image that should be shown as the sprite.
     */
    public abstract Image getIdleSprite();

    /**
     * The image shown when the sprite is moving/animating.
     * @return By default: The idleSprite. Otherwise the sprite that should be shown when animating.
     */
    public Image getWalkingSprite(){
        return this.getIdleSprite();
    }

    /**
     * By default, a GridSprite is never animating.
     */
    public GridSprite(){
        this.animating = false;
    }


    private boolean animating;

    /**
     *
     * Represents the animating state.
     * @return True if the sprite is currently animating
     */
    public boolean isAnimating(){
        return this.animating;
    }

    /**
     * Set the animation-state
     */
    public void setAnimating(boolean animating){
        this.animating = animating;
    }
}
