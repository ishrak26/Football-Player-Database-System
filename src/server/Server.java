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
    List<ClientInfo> clientList;

    public Server(int port) {
        clientList = new ArrayList<>();
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

    synchronized public boolean registerClub(String clubName, String password, NetworkUtil networkUtil) {
        List<String> clubs = clientList.stream().map(ClientInfo::getClubName).filter(
                e -> e.equalsIgnoreCase(clubName)
        ).collect(Collectors.toList());
        if (clubs.size() == 0) {
            ClientInfo clientInfo = new ClientInfo();
            clientInfo.setClubName(clubName);
            clientInfo.setPassword(password);
            clientInfo.setNetworkUtil(networkUtil);
            clientInfo.setLoggedIn(false);
            clientList.add(clientInfo);
            return true;
        }
        return false;
//        Optional<String> club = clientList.stream().map(ClientInfo::getClubName).filter(
//                e -> e.equalsIgnoreCase(clubName)
//        ).findFirst();
//        if (!club.isPresent())
    }

    synchronized public boolean loginClub(String clubName, String password) {
        return false;
    }
}
