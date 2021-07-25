package client;

import data.database.Club;
import data.network.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import util.NetworkUtil;

import java.io.IOException;
import java.util.List;

public class Client extends Application {

    private Stage stage;
    private NetworkUtil networkUtil;
    private TransferWindowRefreshThread refreshThread;

    private void connectToServer() throws IOException {
        String serverAddress = "127.0.0.1";
        int serverPort = 45045;
        networkUtil = new NetworkUtil(serverAddress, serverPort);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        connectToServer();
        showLoginPage();
    }

    private void showLoginPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/clubLoginWindow.fxml"));
        Parent root = fxmlLoader.load();

        ClubLoginWindowController controller = fxmlLoader.getController();
        controller.setClient(this);
        controller.init();

        Scene scene = new Scene(root);

        stage.setOnCloseRequest(e -> stage.close());

        stage.setTitle("Home");
        stage.setScene(scene);
        stage.show();
    }

    private void showClubHomePage(String clubName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/clubHomeWindow.fxml"));

        Parent root = fxmlLoader.load();

        ClubHomeWindowController controller = fxmlLoader.getController();
//        controller.setClubName(clubName);
        controller.init(this, clubName);

        Scene scene = new Scene(root);

        stage.setOnCloseRequest(e -> {
            e.consume();
            logoutClub(clubName);
        });

        stage.setTitle(clubName);
        stage.setScene(scene);
        stage.setX(10);
        stage.setY(10);
        stage.show();
    }

    public void loginClub(String username, String password) {
        LoginInfo loginInfo = new LoginInfo(MessageHeader.LOGIN, username, password);
        try {
            networkUtil.write(loginInfo);
            Object obj = networkUtil.read();
            if (obj instanceof Boolean) {
                Boolean b = (Boolean) obj;
                if (b) {
                    showClubHomePage(username);
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Error");
                    a.setHeaderText("Login window");
                    a.setContentText("Login is unsuccessful");
                    a.showAndWait();
                }
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
                Alert a = new Alert(Alert.AlertType.NONE);
                a.setHeaderText("Registration window");
                if (b) {
                    a.setAlertType(Alert.AlertType.CONFIRMATION);
                    a.setTitle("Confirmation");
                    a.setContentText("Registration is successful");
                } else {
                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setTitle("Error");
                    a.setContentText("Registration is unsuccessful");
                }
                a.showAndWait();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void logoutClub(String clubName) {
        try {
            networkUtil.write(new Message(MessageHeader.LOGOUT, clubName));
            Object obj = networkUtil.read();
            if (obj instanceof Boolean) {
                Boolean b = (Boolean) obj;
                if (b) {
                    interruptRefreshThread();
                    showLoginPage();
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Error");
                    a.setHeaderText("Logout window");
                    a.setContentText("Logout is unsuccessful");
                    a.showAndWait();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean sellPlayer(String playerName, double playerPrice) {
        try {
//            networkUtil.write(new Message(MessageHeader.SELL, playerName));
            networkUtil.write(new SaleInfo(MessageHeader.SELL, playerName, playerPrice));
            Object obj = networkUtil.read();
            if (obj instanceof Boolean) {
                Boolean b = (Boolean) obj;
                if (!b) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Error");
                    a.setHeaderText(playerName);
                    a.setContentText("Player could not be sold!");
                    a.showAndWait();
                }
                return b;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean buyPlayer(String playerName, String clubName) {
        try {
            networkUtil.write(new BuyInfo(MessageHeader.BUY, playerName, clubName));
            Object obj = networkUtil.read();
            if (obj instanceof Boolean) {
                Boolean b = (Boolean) obj;
                if (!b) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Error");
                    a.setHeaderText(playerName);
                    a.setContentText("Player is unavailable!");
                    a.showAndWait();
                }
                return b;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Club loadClubFromServer(String clubName) {
        try {
            networkUtil.write(new Message(MessageHeader.CLUB_INFO, clubName));
            Object obj = networkUtil.read();
            if (obj instanceof Club) {
                return (Club) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<?> loadClubList() {
        try {
            networkUtil.write(new Message(MessageHeader.CLUB_LIST, null));
            Object obj = networkUtil.read();
            if (obj instanceof List) {
                return (List<?>) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    synchronized public List<?> loadTransferList() {
        try {
            networkUtil.write(new Message(MessageHeader.TRANSFER_WINDOW, null));
            Object obj = networkUtil.read();
            if (obj instanceof List) {
                return (List<?>) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    synchronized public void startRefreshThread(ClubHomeWindowController clubHomeWindowController) {
        refreshThread = new TransferWindowRefreshThread(clubHomeWindowController);
    }

    synchronized public void interruptRefreshThread() {
        if (refreshThread != null) refreshThread.getThread().interrupt();
    }

    public NetworkUtil getNetworkUtil() {
        return networkUtil;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
