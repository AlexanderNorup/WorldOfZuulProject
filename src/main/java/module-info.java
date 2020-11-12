//Ingorer IntilliJ her. Den siger der er en fejl.
// - og det er der også. Gradle fikser den bare hver gang vi compiler
// - ved ikke hvordan vi kommer af med den. Men det virker.. Soo, that's a thing
// - Alexander
module worldofzuul {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens worldofzuul to javafx.graphics, javafx.controls, javafx.fxml;
    exports worldofzuul to javafx.graphics, javafx.controls, javafx.fxml;
}