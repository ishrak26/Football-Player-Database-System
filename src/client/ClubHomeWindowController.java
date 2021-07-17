package client;

import data.database.Club;
import data.database.Database;
import data.database.Player;
import home.FileOperations;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class ClubHomeWindowController implements Initializable {

    @FXML
    private ImageView clubLogoImage;

    @FXML
    private Label clubNameFirstLine;

    @FXML
    private Label clubNameSecondLine;

    @FXML
    private Label clubBudgetLabel;

    @FXML
    private Button addNewPlayerButton;

    @FXML
    private TextField searchPlayerNameTextField;

    @FXML
    private Button searchPlayerNameButton;

    @FXML
    private Button buyPlayerButton;

    @FXML
    private Button logoutButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;

    private Club club;
    private String clubName;
    private String logoImgSource;

    @FXML
    void addNewPlayer(ActionEvent event) {

    }

    @FXML
    void buyPlayer(ActionEvent event) {

    }

    @FXML
    void logout(ActionEvent event) {

    }

    @FXML
    void searchPlayerByName(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clubName = "Liverpool";
        loadClubData();
        initClubInfo();
        loadPlayerCards();
    }

    private void initClubInfo() {
        String clubName = this.clubName.replace(' ', '_');
        logoImgSource = "/images/logo/" + clubName + ".png";
        clubLogoImage.setImage(new Image(getClass().getResourceAsStream(logoImgSource)));
        String[] words = clubName.split("_");
        clubNameFirstLine.setText(words[0].toUpperCase());
        if (words.length > 1) {
            clubNameSecondLine.setText(words[1].toUpperCase());
        } else {
            clubNameSecondLine.setText("");
        }
    }

    private void loadPlayerCards() {
        try {
            int row = 0;
            int col = 0;
            for (Player player :
                    club.getPlayers()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/client/playerCard.fxml"));

                Parent card = fxmlLoader.load();

                PlayerCardController playerCardController = fxmlLoader.getController();
                playerCardController.setData(player);

                gridPane.add(card, col, row++);
                //set grid width
                gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(card, new Insets(10));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadClubData() {
        FileOperations fileOperations = new FileOperations();
        Database db = new Database();
        try {
            fileOperations.readFromFile(db);
            this.club = db.searchClub(this.clubName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
