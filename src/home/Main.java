package home;

import client.ClubHomeWindowController;
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
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/client/clubHomeWindow.fxml"));
        Parent root = fxmlLoader.load();
//        Parent root = FXMLLoader.load(getClass().getResource("/client/playerCard.fxml"));
        ClubHomeWindowController clubHomeWindowController = fxmlLoader.getController();
        primaryStage.setTitle(clubHomeWindowController.getClubName());
        Scene scene = new Scene(root);
//        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
