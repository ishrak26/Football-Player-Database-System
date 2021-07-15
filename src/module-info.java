module JavaFxApplication {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    opens server to javafx.graphics, javafx.fxml;
}