package client;

import data.database.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

    }

    public void setData(Player player) {
        this.player = player;
        playerNameLabel.setText(player.getName());
        playerPositionLabel.setText(player.getPosition());
        this.playerImage = new ImageView(new Image(getClass().getResourceAsStream(player.getImgSource())));
    }

}
