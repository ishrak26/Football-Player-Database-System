package client;

import data.database.Club;
import data.database.Country;
import data.database.Database;
import data.database.Player;
import home.FileOperations;
import javafx.application.Platform;
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

public class ClubHomeWindowController {

    @FXML
    private ImageView clubLogoImage;

    @FXML
    private Label clubNameFirstLine;

    @FXML
    private Label clubNameSecondLine;

    @FXML
    private Label clubBudgetLabel;

    @FXML
    private Button buyPlayerButton;

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
    private HBox refreshRateHBox;

    @FXML
    private ChoiceBox<String> refreshRateChoiceBox;

    @FXML
    private MenuButton clubMenuButton;

    @FXML
    private MenuItem usernameMenuItem;

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
    private List<Player> playerListOnDisplay;
    private Client client;
    volatile private int refreshRate;

    private boolean aBoolean = false;

    @FXML
    void showTransferWindow(ActionEvent event) {
        if (buyPlayerButton.getText().equals("Buy Player")) {
            client.startRefreshThread(this);
            refreshRateHBox.setVisible(true);
            buyPlayerButton.setText("Home");
        } else {
            client.interruptRefreshThread();
            refreshRateHBox.setVisible(false);
            buyPlayerButton.setText("Buy Player");
            loadPlayerCards(this.club.getPlayers());
        }

    }

    void loadTransferWindow() {
        List<?> players = this.client.loadTransferList();
        if (players != null) {
            List<Player> playerList = new ArrayList<>();
            players.forEach(e -> {
                if (e instanceof Player && !((Player) e).getClub().equals(this.clubName)) {
                    playerList.add((Player) e);
                }
            });
            loadPlayerCards(playerList);
        }

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

    public void init(Client client, String clubName) {
        this.client = client;
        this.clubName = clubName;
        loadClubData();
        initClubInfo();
        loadPlayerCards(club.getPlayers());
        makeFilterTree();

        makeMenu();

        makeRefreshRateChoiceBox();
    }

    private void makeRefreshRateChoiceBox() {
        refreshRateChoiceBox.getItems().addAll(
                "5 seconds",
                "10 seconds",
                "15 seconds",
                "30 seconds",
                "1 minute",
                "2 minutes",
                "5 minutes"
        );
        refreshRateChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (v, oldValue, newValue) -> {
                    if (oldValue != null && !oldValue.equals(newValue)) {
                        this.refreshRate = toRefreshRate(newValue);
                        client.interruptRefreshThread();
                        client.startRefreshThread(this);
                    }
                }
        );
        refreshRateChoiceBox.setValue("5 seconds");
        this.refreshRate = 5;
    }

    int toRefreshRate(String newValue) {
        String[] choice = newValue.split(" ");
        int rate = Integer.parseInt(choice[0]);
        if (choice[1].charAt(0) == 'm') rate *= 60;
        return rate;
    }

    private void makeMenu() {
        clubMenuButton.setText("");
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(club.getImgSource())));
        imageView.setFitHeight(25);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        clubMenuButton.setGraphic(imageView);

        usernameMenuItem.setText("Signed in as " + clubName);
    }

    private void makeFilterTree() {
        makeFilterTreeCountry();
        makeFilterTreePosition();
    }

    private void makeFilterTreePosition() {
        TreeItem<CheckBox> root;
        root = new TreeItem<>();
        root.setExpanded(true);

        this.club.getPositionList().forEach(e -> makeBranchFilterTree(e, root));

        filterTreePosition.setRoot(root);
        filterTreePosition.setShowRoot(false);
    }

    private void makeFilterTreeCountry() {
        TreeItem<CheckBox> root;
        root = new TreeItem<>();
        root.setExpanded(true);

        club.getCountryList().forEach(e -> makeBranchFilterTree(e, root));

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
        logoImgSource = this.club.getImgSource();
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
            playerListViewController.setClubHomeWindowController(this);
            playerListViewController.loadPlayerCards(playerList);

            playerListVBox.getChildren().clear();
            playerListVBox.getChildren().add(root);

            this.playerListOnDisplay = new ArrayList<>(playerList);

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
        this.club = client.loadClubFromServer(this.clubName);
    }

    @FXML
    void listMaxAgePlayers(ActionEvent event) {
        loadPlayerCards(club.getMaxAgePlayers());
    }

    @FXML
    void listMaxHeightPlayers(ActionEvent event) {
        loadPlayerCards(club.getMaxHeightPlayers());
    }

    @FXML
    void listMaxSalaryPlayers(ActionEvent event) {
        loadPlayerCards(club.getMaxSalaryPlayers());
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    @FXML
    void logoutClub(ActionEvent event) {
        client.logoutClub(this.clubName);
    }

    public void sellPlayer(String playerName, double playerPrice) {
        boolean b = client.sellPlayer(playerName, playerPrice);
        if (b) {
            this.club.removePlayer(playerName);
            makeFilterTree();
            loadPlayerCards(this.club.getPlayers());
        }
    }

    public void buyPlayer(Player player) {
        boolean b = client.buyPlayer(player.getName(), this.clubName);
        if (b) {
            this.playerListOnDisplay.remove(player);
            player.setInTransferList(false);
            player.setClub(this.clubName);
            this.club.addPlayer(player);
            makeFilterTree();
            loadPlayerCards(this.playerListOnDisplay);
        }
    }

    public int getRefreshRate() {
        return refreshRate;
    }
}
