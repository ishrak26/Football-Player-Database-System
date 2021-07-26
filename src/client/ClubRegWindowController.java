package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class ClubRegWindowController {

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button resetButton;

    @FXML
    private Button registerButton;

    @FXML
    private Label userNameLabel;

    @FXML
    private PasswordField retypePasswordTextField;

    private Client client;

    @FXML
    void register(ActionEvent event) {
        String password = passwordTextField.getText();
        String retypePassword = retypePasswordTextField.getText();
        if (password.isBlank() || retypePassword.isBlank()) {
            showAlert("Password field cannot be empty.");
        } else if (!password.equals(retypePassword)) {
            showAlert("Retyped password did not match.");
            reset(event);
        }
        else {
            client.registerClub(userNameLabel.getText(), password);
        }
    }

    private void showAlert(String text) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Error");
        a.setHeaderText("Registration not successful");
        a.setContentText(text);
        a.showAndWait();
    }

    @FXML
    void reset(ActionEvent event) {
        passwordTextField.setText("");
        retypePasswordTextField.setText("");
    }

    public String getUserNameLabelText() {
        return userNameLabel.getText();
    }

    public void setUserNameLabelText(String userName) {
        this.userNameLabel.setText(userName);
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
