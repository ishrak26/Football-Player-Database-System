package client;

import data.database.Database;
import data.database.Player;
import home.FileOperations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.RangeSlider;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class clubWindowController implements Initializable {

    @FXML
    private HBox topHBox;

    @FXML
    private MenuBar menuBar;

    @FXML
    private TextField searchText;

    @FXML
    private Button logoutButton;

    @FXML
    private VBox leftVBox;

    @FXML
    private Label filterLabel;

    @FXML
    private TreeView<CheckBox> filterTree;

    @FXML
    private Button applyFilterButton;

    @FXML
    private Button resetFilterButton;

    @FXML
    private HBox bottomHBox;

    @FXML
    private Button sellPlayersButton;

    @FXML
    private TableView<Player> playersTable;

    @FXML
    private TableColumn<Player, String> columnName;

    @FXML
    private TableColumn<Player, String> columnCountry;

    @FXML
    private TableColumn<Player, String> columnPosition;

    @FXML
    private TableColumn<Player, Integer> columnAge;

    @FXML
    private TableColumn<Player, Double> columnHeight;

    @FXML
    private TableColumn<Player, Double> columnSalary;

    @FXML
    void applyFilters(ActionEvent event) {

    }

    @FXML
    void logout(ActionEvent event) {

    }

    @FXML
    void resetFilters(ActionEvent event) {

    }

    @FXML
    void sellSelectedPlayers(ActionEvent event) {
        ObservableList<Player> selectedPlayers = playersTable.getSelectionModel().getSelectedItems();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        makeFilterTree();
        loadPlayersTable();
    }

    private void loadPlayersTable() {
        initializeTableColumns();
        loadPlayersTableData();
        playersTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void initializeTableColumns() {
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        columnPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        columnAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        columnHeight.setCellValueFactory(new PropertyValueFactory<>("height"));
        columnSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }

    private void loadPlayersTableData() {
        ObservableList<Player> tableData = FXCollections.observableArrayList();
        Database db = new Database();
        FileOperations file = new FileOperations();
        try {
            file.readFromFile(db);
            List<Player> playerList = db.getPlayerList();
            tableData.addAll(playerList);
            playersTable.setItems(tableData);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void makeFilterTree() {
        TreeItem<CheckBox> root, country, position;
        root = new TreeItem<>();
        root.setExpanded(true);

        country = makeBranch("Country", root);
        makeBranch("Spain", country);
        makeBranch("Germany", country);

        position = makeBranch("Position", root);
        makeBranch("Goalkeeper", position);
        makeBranch("Defender", position);

        filterTree.setRoot(root);
        filterTree.setShowRoot(false);
    }

    private TreeItem<CheckBox> makeBranch(String title, TreeItem<CheckBox> parent) {
        CheckBox checkBox = new CheckBox(title);
        TreeItem<CheckBox> item = new TreeItem<>(checkBox);
        parent.getChildren().add(item);
        return item;
    }

}
