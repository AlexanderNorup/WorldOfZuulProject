//Ingorer IntilliJ her. Den siger der er en fejl.
// - og det er der også. Gradle fikser den bare hver gang vi compiler
// - ved ikke hvordan vi kommer af med den. Men det virker.. Soo, that's a thing
// - Alexander
module worldofzuul {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires json;

    //PresentationLayer
    opens worldofzuul.PresentationLayer to javafx.graphics, javafx.controls, javafx.fxml, javafx.media;
    exports worldofzuul.PresentationLayer to javafx.graphics, javafx.controls, javafx.fxml, javafx.media;

    opens worldofzuul.PresentationLayer.Controllers to javafx.graphics, javafx.controls, javafx.fxml, javafx.media;
    exports worldofzuul.PresentationLayer.Controllers to javafx.graphics, javafx.controls, javafx.fxml, javafx.media;
}