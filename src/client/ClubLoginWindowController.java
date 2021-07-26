package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClubLoginWindowController {

    @FXML
    private ChoiceBox<String> usernameChoiceBox;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Button resetButton;

    @FXML
    private Button registerButton;

    private Client client;

    @FXML
    void login(ActionEvent event) {
        String username = usernameChoiceBox.getValue();
        String password = passwordTextField.getText();
        if (username == null || password.isBlank()) {
            showAlert("Login");
        } else {
            client.loginClub(username, password);
            reset(event);
        }
    }

    private void showAlert(String header) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Error");
        a.setHeaderText(header + " not successful");
        a.setContentText("Username or password field cannot be empty.");
        a.showAndWait();
    }

    @FXML
    void register(ActionEvent event) {
        String username = usernameChoiceBox.getValue();
        if (username == null) {
            showAlert("Registration request");
        } else {
            try {
                client.showRegWindow(username);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void reset(ActionEvent event) {
        usernameChoiceBox.setValue(null);
        passwordTextField.setText("");
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void init() {
        initializeUsernameChoiceBox();
    }

    private void initializeUsernameChoiceBox() {
//        usernameChoiceBox.getItems().addAll(
//          "Manchester United", "Manchester City", "Arsenal"
//        );
        List<?> clubList = client.loadClubList();
        clubList.forEach(e -> {
            if (e instanceof String) {
                usernameChoiceBox.getItems().add((String) e);
            }
        });
    }
}
