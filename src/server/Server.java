package server;

import data.database.Database;
import data.database.Player;
import home.FileOperations;
import util.NetworkUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Server {
    Database db;
    private FileOperations fileOperations;
    List<Player> transferPlayerList;
//    List<ClientInfo> clientList;
    HashMap<String, ClientInfo> clientMap;

    public Server(int port) {
//        clientList = new ArrayList<>();
        clientMap = new HashMap<>();
        transferPlayerList = new ArrayList<>();
        try {
            loadDatabase();
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                serve(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDatabase() throws IOException {
        db = new Database();
        fileOperations = new FileOperations();
        fileOperations.readFromFile(db);
    }

    private void serve(Socket socket) throws IOException {
        NetworkUtil networkUtil = new NetworkUtil(socket);
        new ThreadServer(networkUtil, this);
    }

    public static void main(String[] args) {
        int port = 45045;
        new Server(port);
    }

    synchronized public void sellPlayer(Player player, String newClubName) {
        transferPlayerList.remove(player);
        player.setClub(newClubName);
    }

    synchronized public void addToTransferWindow(Player player) {
        // player will not be null
        transferPlayerList.add(player);
    }

    // returns true if registration is successful, false otherwise
    synchronized public boolean registerClub(String clubName, String password, NetworkUtil networkUtil) {
        for (String clientName:
             clientMap.keySet()) {
            if (clientName.equalsIgnoreCase(clubName)) return false;
        }
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setClubName(clubName);
        clientInfo.setPassword(password);
        clientInfo.setNetworkUtil(networkUtil);
        clientInfo.setLoggedIn(false);
        clientMap.put(clubName, clientInfo);
        return true;
    }

    // returns true if login is successful, false otherwise
    synchronized public boolean loginClub(String username, String password) {
        if (clientMap.containsKey(username) && clientMap.get(username).getPassword().equals(password)) {
            // if the username exists, there must have been set a password during registration
            // so, password cannot be null
            clientMap.get(username).setLoggedIn(true);
            return true;
        }
        return false;
    }

    synchronized public boolean changePassword(String username, String oldPassword, String newPassword) {
        if (clientMap.containsKey(username) && clientMap.get(username).getPassword().equals(oldPassword)
            && clientMap.get(username).isLoggedIn()) {
            // if the username exists, there must have been set a password during registration
            // so, password cannot be null
            clientMap.get(username).setPassword(newPassword);
            return true;
        }
        return false;
    }
}
