package client;

import data.database.Club;
import data.database.Player;
import data.network.PlayerTransfer;
import javafx.collections.ObservableList;
import util.NetworkUtil;

import java.io.IOException;

public class Client {
    Club club;
    private NetworkUtil networkUtil;

    public Client(String address, int port) {
        try {
            networkUtil = new NetworkUtil(address, port);

            new ReadThreadClient(networkUtil);
            new WriteThreadClient(networkUtil);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sellPlayers(ObservableList<Player> playerObservableList) {
        for (Player player:
             playerObservableList) {
            club.removePlayer(player.getName());
            PlayerTransfer playerTransfer = new PlayerTransfer(player, "sell");
            try {
                networkUtil.write(playerTransfer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        final String address = "127.0.0.1";
        final int port = 45045;
        new Client(address, port);
    }
}
