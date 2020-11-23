package worldofzuul.PresentationLayer;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
