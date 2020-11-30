package worldofzuul.PresentationLayer.GridObjects;

import javafx.scene.image.Image;

public class Dog extends GridSprite {

    private Image avatarImg;
    public Dog(){
        super();
        this.avatarImg = new Image(getClass().getResource("/sprites/dog.png").toString());
    }

    @Override
    public Image getIdleSprite() {
        return avatarImg;
    }
}
