package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ClubLoginWindowController implements Initializable {

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
        client.loginClub(username, password);
        reset(event);
    }

    @FXML
    void register(ActionEvent event) {
        String username = usernameChoiceBox.getValue();
        String password = passwordTextField.getText();
        client.registerClub(username, password);
        reset(event);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeUsernameChoiceBox();
    }

    private void initializeUsernameChoiceBox() {
        usernameChoiceBox.getItems().addAll(
          "Manchester United", "Manchester City", "Arsenal"
        );
    }
}
