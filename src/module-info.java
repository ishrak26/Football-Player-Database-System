module JavaFxApplication {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires controlsfx;

    opens server to javafx.graphics, javafx.fxml;
    opens client to javafx.graphics, javafx.fxml;
    opens data.database to javafx.base;
}