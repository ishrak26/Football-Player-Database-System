package client;

import data.database.Club;
import data.database.Country;
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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
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
    private VBox bodyVBox;

    @FXML
    private HBox topBarHBox;

    @FXML
    private TextField searchPlayerNameTextField;

    @FXML
    private Button searchPlayerNameButton;

    @FXML
    private Button buyPlayerButton;

    @FXML
    private Button logoutButton;

    @FXML
    private HBox listPlayerHBox;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;

    @FXML
    private TreeView<CheckBox> filterTreeCountry;

    @FXML
    private TreeView<CheckBox> filterTreePosition;

    @FXML
    private TextField ageFromTextField;

    @FXML
    private TextField ageToTextField;

    @FXML
    private TextField heightFromTextField;

    @FXML
    private TextField salaryFromTextField;

    @FXML
    private TextField heightToTextField;

    @FXML
    private TextField salaryToTextField;

    @FXML
    private Button applyFiltersButton;

    @FXML
    private Button resetFiltersButton;

    @FXML
    private HBox bottomBarHBox;

    private Club club;
    private String clubName;
    private String logoImgSource;
    private boolean aBoolean = false;

    @FXML
    void addNewPlayer(ActionEvent event) {
//        if (listPlayerHBox.isVisible()) listPlayerHBox.setVisible(false);
//        else listPlayerHBox.setVisible(true);
        if (!aBoolean) {
            bodyVBox.getChildren().clear();
            aBoolean = true;
        }
        else {
            bodyVBox.getChildren().addAll(bottomBarHBox, topBarHBox, listPlayerHBox);
            aBoolean = false;
        }
    }

    @FXML
    void buyPlayer(ActionEvent event) {
//        listPlayerHBox.toFront();

    }

    @FXML
    void logout(ActionEvent event) {

    }

    @FXML
    void searchPlayerByName(ActionEvent event) {

    }

    @FXML
    void applyFilters(ActionEvent event) {

    }

    @FXML
    void resetFilters(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clubName = "Manchester United";
        loadClubData();
        initClubInfo();
        loadPlayerCards(club.getPlayers());
        makeFilterTree();
    }

    private void makeFilterTree() {
        makeFilterTreeCountry();
        makeFilterTreePosition();
    }

    private void makeFilterTreePosition() {
        TreeItem<CheckBox> root;
        root = new TreeItem<>();
        root.setExpanded(true);

        makeBranchFilterTree("Goalkeeper", root);
        makeBranchFilterTree("Defender", root);
        makeBranchFilterTree("Midfielder", root);
        makeBranchFilterTree("Forward", root);

        filterTreePosition.setRoot(root);
    }

    private void makeFilterTreeCountry() {
        TreeItem<CheckBox> root;
        root = new TreeItem<>();
        root.setExpanded(true);

        for (String country:
             club.getCountrySet()) {
            makeBranchFilterTree(country, root);
        }

        filterTreeCountry.setRoot(root);
    }

    private TreeItem<CheckBox> makeBranchFilterTree(String title, TreeItem<CheckBox> parent) {
        CheckBox checkBox = new CheckBox(title);
        TreeItem<CheckBox> item = new TreeItem<>(checkBox);
        parent.getChildren().add(item);
        return item;
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

    // for listing players under any condition
    private void loadPlayerCards(List<Player> playerList) {
        try {
            int row = 0;
            int col = 0;
            for (Player player :
                    playerList) {
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
