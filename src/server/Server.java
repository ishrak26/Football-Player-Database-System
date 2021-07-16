package server;

import data.database.Database;
import data.database.Player;
import home.FileOperations;
import util.NetworkUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket serverSocket;
    Database db;
    private FileOperations fileOperations;
    List<Player> transferPlayerList;

    public Server(int port) {
        try {
            loadDatabase();
            serverSocket = new ServerSocket(port);
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
        transferPlayerList = new ArrayList<>();
    }

    private void serve(Socket socket) throws IOException {
        NetworkUtil networkUtil = new NetworkUtil(socket);
        new ThreadServer(networkUtil);
    }

    public static void main(String[] args) {
        int port = 45045;
        new Server(port);
    }
}
