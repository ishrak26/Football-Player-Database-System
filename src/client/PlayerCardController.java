package client;

import data.database.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayerCardController {

    @FXML
    private ImageView playerImage;

    @FXML
    private Label playerNameLabel;

    @FXML
    private Label playerPositionLabel;

    @FXML
    private Button playerDetailsButton;

    private Player player;

    @FXML
    void showPlayerDetails(ActionEvent event) {

        try {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle(player.getName());

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/client/playerDetailsWindow.fxml"));
            Parent root = fxmlLoader.load();

            PlayerDetailsWindowController playerDetails = fxmlLoader.getController();
            playerDetails.setData(player);

            Scene scene = new Scene(root);
            window.setScene(scene);
            window.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setData(Player player) {
        this.player = player;
        playerNameLabel.setText(player.getName());
        playerPositionLabel.setText(player.getPosition());
        this.playerImage = new ImageView(new Image(getClass().getResourceAsStream(player.getImgSource())));
    }

}
