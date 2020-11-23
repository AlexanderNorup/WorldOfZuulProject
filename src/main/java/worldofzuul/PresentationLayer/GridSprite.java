package worldofzuul.PresentationLayer;

import javafx.scene.image.Image;

public abstract class GridSprite extends GridObject {
    public abstract Image getSprite();

    public GridSprite(){
        this.animating = false;
    }

    private boolean animating;
    public boolean isAnimating(){
        return this.animating;
    }

    public void setAnimating(boolean animating){
        this.animating = animating;
    }
}
