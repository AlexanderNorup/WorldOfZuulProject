package worldofzuul.PresentationLayer;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Dog extends GridObject {
    @Override
    public ImageView draw() {
        Image avatarImg = new Image(getClass().getResource("/sprites/dog.png").toString());
        ImageView avatar = new ImageView(avatarImg);
        avatar.setFitHeight(100);
        avatar.setFitWidth(100);
        return avatar;
    }
}
