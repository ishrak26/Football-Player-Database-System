package client;

import org.controlsfx.control.RangeSlider;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
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

//    @FXML
//    private TreeView<CheckBox> filterTreePosition;

    @FXML
    private Button applyFilterButton;

    @FXML
    private Button resetFilterButton;

    @FXML
    private HBox bottomHBox;

    @FXML
    private Button sellPlayersButton;

    @FXML
    private TableView<?> playersTable;

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

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        makeFilterTree();
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
//        leftVBox.getChildren().add(filterTree);
    }

    private TreeItem<CheckBox> makeBranch(String title, TreeItem<CheckBox> parent) {
        CheckBox checkBox = new CheckBox(title);
        if (title.equals("Country")) {
            checkBox.setOnContextMenuRequested(e -> checkBox.setSelected(true));
        }
        TreeItem<CheckBox> item = new TreeItem<>(checkBox);
        parent.getChildren().add(item);
        return item;
    }

}
