package home;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/client/clubHomeWindow.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("/client/playerCard.fxml"));
        primaryStage.setTitle("Club");
        Scene scene = new Scene(root);
//        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public static void main(String[] args) {

        launch(args);
    }
}
