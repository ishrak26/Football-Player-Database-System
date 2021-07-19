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
import org.controlsfx.control.spreadsheet.Grid;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    private Button resetPlayerNameButton;

    @FXML
    private Button buyPlayerButton;

    @FXML
    private Button logoutButton;

    @FXML
    private HBox listPlayerHBox;

    @FXML
    private VBox playerListVBox;

//    @FXML
//    private ScrollPane scrollPane;
//
//    @FXML
//    private GridPane gridPane;

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
        String playerName = searchPlayerNameTextField.getText().trim();
        Database db = new Database();
        db.addPlayer(club.getPlayers());
        Player player = db.searchPlayerByName(playerName);
        db.setPlayerList(new ArrayList<>());
        if (player != null) {
            db.getPlayerList().add(player);
        }
        loadPlayerCards(db.getPlayerList());
    }

    @FXML
    void resetPlayerNameTextField(ActionEvent event) {
        searchPlayerNameTextField.setText("");
        loadPlayerCards(club.getPlayers());
    }

    @FXML
    void applyFilters(ActionEvent event) {
        Database db = new Database();
        db.addPlayer(club.getPlayers());
        applyFiltersCountry(db);
        applyFiltersPosition(db);
        applyFiltersAge(db);
        applyFiltersHeight(db);
        applyFiltersSalary(db);

        loadPlayerCards(db.getPlayerList());
    }

    private void applyFiltersSalary(Database db) {
        double lo, hi;
        try {
            lo = Double.parseDouble(salaryFromTextField.getText());
        } catch (Exception e) {
            lo = 0;
            salaryFromTextField.setText(String.valueOf(lo));
        }
        try {
            hi = Double.parseDouble(salaryToTextField.getText());
        } catch (Exception e) {
            hi = club.getMaxSalaryPlayers().get(0).getSalary();
            salaryToTextField.setText(String.valueOf(hi));
        }
        db.setPlayerList(db.searchPlayerBySalary(lo, hi));
    }

    private void applyFiltersHeight(Database db) {
        double lo, hi;
        try {
            lo = Double.parseDouble(heightFromTextField.getText());
        } catch (Exception e) {
            lo = 0;
            heightFromTextField.setText(String.valueOf(lo));
        }
        try {
            hi = Double.parseDouble(heightToTextField.getText());
        } catch (Exception e) {
            hi = club.getMaxHeightPlayers().get(0).getHeight();
            heightToTextField.setText(String.valueOf(hi));
        }
        db.setPlayerList(db.searchPlayerByHeight(lo, hi));
    }

    private void applyFiltersAge(Database db) {
        int lo, hi;
        try {
            lo = Integer.parseInt(ageFromTextField.getText());
        } catch (Exception e) {
            lo = 0;
            ageFromTextField.setText(String.valueOf(lo));
        }
        try {
            hi = Integer.parseInt(ageToTextField.getText());
        } catch (Exception e) {
            hi = club.getMaxAgePlayers().get(0).getAge();
            ageToTextField.setText(String.valueOf(hi));
        }
        db.setPlayerList(db.searchPlayerByAge(lo, hi));
    }

    private void applyFiltersPosition(Database db) {
        for (TreeItem<CheckBox> item:
                filterTreePosition.getRoot().getChildren()) {
            if (!item.getValue().isSelected()) {
                // remove players playing at this position
                for (Player player:
                        db.searchPlayerByPosition(item.getValue().getText())) {
                    db.getPlayerList().remove(player);
                }
            }
        }
    }

    private void applyFiltersCountry(Database db) {
        for (TreeItem<CheckBox> item:
             filterTreeCountry.getRoot().getChildren()) {
            if (!item.getValue().isSelected()) {
                // remove players from this country
                for (Player player:
                        db.searchPlayerByCountry(item.getValue().getText())) {
                    db.getPlayerList().remove(player);
                }
            }
        }
    }

    @FXML
    void resetFilters(ActionEvent event) {
        // reset countries
        for (TreeItem<CheckBox> item:
                filterTreeCountry.getRoot().getChildren()) {
            if (!item.getValue().isSelected()) {
                item.getValue().setSelected(true);
            }
        }

        // reset position
        for (TreeItem<CheckBox> item:
                filterTreePosition.getRoot().getChildren()) {
            if (!item.getValue().isSelected()) {
                item.getValue().setSelected(true);
            }
        }

        // reset text fields
        ageFromTextField.setText("");
        ageToTextField.setText("");

        heightFromTextField.setText("");
        heightToTextField.setText("");

        salaryFromTextField.setText("");
        salaryToTextField.setText("");

        applyFilters(event);
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
        filterTreePosition.setShowRoot(false);
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
        filterTreeCountry.setShowRoot(false);
    }

    private TreeItem<CheckBox> makeBranchFilterTree(String title, TreeItem<CheckBox> parent) {
        CheckBox checkBox = new CheckBox(title);
        checkBox.setSelected(true);
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
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/views/playerListView.fxml"));
            Parent root = fxmlLoader.load();
            PlayerListViewController playerListViewController = fxmlLoader.getController();
            playerListViewController.loadPlayerCards(playerList);
            playerListVBox.getChildren().clear();
            playerListVBox.getChildren().add(root);

//            System.out.println("The list of players is: ");
//            for (Player player:
//                 playerList) {
//                System.out.println(player.getName());
//            }
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

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
}
