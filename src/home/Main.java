package home;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/client/clubWindow.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 673, 400));
        primaryStage.show();


    }

    public static void main(String[] args) {

        launch(args);
    }
}
