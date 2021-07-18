package client;

import data.database.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerDetailsWindowController implements Initializable {

    @FXML
    private ImageView playerImage;

    @FXML
    private Label playerNameLabel;

    @FXML
    private Label playerPositionLabel;

    @FXML
    private Label playerClubLabel;

    @FXML
    private Label playerCountryLabel;

    @FXML
    private Label playerAgeLabel;

    @FXML
    private Label playerSalaryLabel;

    @FXML
    private Label playerHeightLabel;

    public void setData(Player player) {
        playerImage.setImage(new Image(getClass().getResourceAsStream(player.getImgSource())));
        playerNameLabel.setText(player.getName());
        playerPositionLabel.setText(player.getPosition());
        playerClubLabel.setText(player.getClub());
        playerCountryLabel.setText(player.getCountry());
        playerAgeLabel.setText("Age: " + player.getAge());
        playerHeightLabel.setText("Height: " + player.getHeight());
        playerSalaryLabel.setText("Weekly Salary: " + String.format("%,.2f", player.getSalary()) + " USD");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
