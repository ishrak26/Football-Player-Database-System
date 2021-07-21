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
import javafx.scene.input.MouseEvent;
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

    @FXML
    private Button playerSellButton;

    private Player player;
    private ClubHomeWindowController clubHomeWindowController;

    @FXML
    void sellPlayer(ActionEvent event) {
        clubHomeWindowController.sellPlayer(player.getName());
    }

    @FXML
    void showPlayerDetails(ActionEvent event) {

        try {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle(player.getName());

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/views/playerDetailsWindow.fxml"));
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

    @FXML
    public void selectPlayer(MouseEvent event) {
        System.out.println(this.player.getName());
        playerSellButton.setVisible(true);
    }

    public void setData(Player player) {
        this.player = player;
        playerNameLabel.setText(player.getName());
        playerPositionLabel.setText(player.getPosition());
        playerImage.setImage(new Image(getClass().getResourceAsStream(player.getImgSource())));
    }

    public ClubHomeWindowController getClubHomeWindowController() {
        return clubHomeWindowController;
    }

    public void setClubHomeWindowController(ClubHomeWindowController clubHomeWindowController) {
        this.clubHomeWindowController = clubHomeWindowController;
    }
}
