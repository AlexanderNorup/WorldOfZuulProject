package worldofzuul.PresentationLayer;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Dog extends GridSprite {
    @Override
    public Image getSprite() {
        Image avatarImg = new Image(getClass().getResource("/sprites/dog.png").toString());
        return avatarImg;
    }
}
