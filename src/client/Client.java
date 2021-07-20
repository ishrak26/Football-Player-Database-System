package client;

import data.network.LoginInfo;
import data.network.MessageHeader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.NetworkUtil;

import java.io.IOException;

public class Client extends Application {

    private Stage stage;
    private NetworkUtil networkUtil;

    private void connectToServer() throws IOException {
        String serverAddress = "127.0.0.1";
        int serverPort = 45045;
        networkUtil = new NetworkUtil(serverAddress, serverPort);
        new ReadThreadClient(this);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        connectToServer();
        showLoginPage();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void showLoginPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/clubLoginWindow.fxml"));
        Parent root = fxmlLoader.load();

        ClubLoginWindowController controller = fxmlLoader.getController();
        controller.setClient(this);

        Scene scene = new Scene(root);

        stage.setTitle("Home");
        stage.setScene(scene);
        stage.show();
    }

    public void loginClub(String username, String password) {
        LoginInfo loginInfo = new LoginInfo(MessageHeader.LOGIN, username, password);
        try {
            networkUtil.write(loginInfo);
            Object obj = networkUtil.read();
            if (obj instanceof Boolean) {
                Boolean b = (Boolean) obj;
                if (b) System.out.println("login successful");
                else System.out.println("login failure");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void registerClub(String username, String password) {
        LoginInfo loginInfo = new LoginInfo(MessageHeader.REGISTER, username, password);
        try {
            networkUtil.write(loginInfo);
            Object obj = networkUtil.read();
            if (obj instanceof Boolean) {
                Boolean b = (Boolean) obj;
                if (b) System.out.println("registration successful");
                else System.out.println("registration failure");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public NetworkUtil getNetworkUtil() {
        return networkUtil;
    }
}
