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

    @FXML
    private Label playerPriceLabel;

    private Player player;
    private ClubHomeWindowController clubHomeWindowController;

    @FXML
    void sellPlayer(ActionEvent event) {
        if (player.isInTransferList()) {
            // buy
            clubHomeWindowController.buyPlayer(player);
        } else {
            // sell
            boolean b = showPlayerSaleConfirmationWindow();
            if (b) clubHomeWindowController.sellPlayer(player.getName(), player.getPrice());
        }
    }

    private boolean showPlayerSaleConfirmationWindow() {
        boolean b = false;
        try {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Sale Confirmation");

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/views/saleConfirmationWindow.fxml"));

            Parent root = fxmlLoader.load();

            SaleConfirmationWindowController controller = fxmlLoader.getController();
            controller.setPlayer(this.player);
            controller.setStage(window);

            Scene scene = new Scene(root);
            window.setScene(scene);
            window.showAndWait();

            b = controller.isSaleConfirm();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    @FXML
    void hideSellButton(MouseEvent event) {
        playerSellButton.setVisible(false);
    }

    @FXML
    void showSellButton(MouseEvent event) {
        playerSellButton.setVisible(true);
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
//        playerSellButton.setVisible(true);
    }

    public void setData(Player player) {
        this.player = player;
        playerNameLabel.setText(player.getName());
        playerPositionLabel.setText(player.getPosition());
        playerPriceLabel.setText("Price: " + String.format("%,.2f", player.getPrice()) + " USD");
        playerImage.setImage(new Image(getClass().getResourceAsStream(player.getImgSource())));

        if (player.isInTransferList()) {
            playerSellButton.setText("Buy");
            playerPriceLabel.setVisible(true);
        } else {
            playerSellButton.setText("Sell");
            playerPriceLabel.setVisible(false);
        }
    }

    public ClubHomeWindowController getClubHomeWindowController() {
        return clubHomeWindowController;
    }

    public void setClubHomeWindowController(ClubHomeWindowController clubHomeWindowController) {
        this.clubHomeWindowController = clubHomeWindowController;
    }
}
