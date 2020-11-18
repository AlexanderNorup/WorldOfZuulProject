package worldofzuul.PresentationLayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Date;

public class TestController {
    @FXML
    public Label clock;

    @FXML
    public void updateClock(ActionEvent actionEvent) {
        clock.setText("The time is now: " + new Date().toString());
    }
}
