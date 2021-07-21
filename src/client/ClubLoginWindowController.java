package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ClubLoginWindowController {

    @FXML
    private TextField usernameTextField;

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
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        client.loginClub(username, password);
        reset(event);
    }

    @FXML
    void register(ActionEvent event) {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        client.registerClub(username, password);
        reset(event);
    }

    @FXML
    void reset(ActionEvent event) {
        usernameTextField.setText("");
        passwordTextField.setText("");
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
