package worldofzuul;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Enumeration;

public class MainGUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Pathen til resourcen er relativt fra /src/main/resources/
        Parent root = FXMLLoader.load(MainGUI.class.getResource("/fxml/test.fxml"));

        Scene s = new Scene(root, 1197,720);
        primaryStage.setScene(s);
        primaryStage.setTitle("WorldOfZuul Test");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
