package server;

import data.database.Club;
import data.database.Player;
import data.network.PlayerListRequest;
import data.network.PlayerTransfer;
import util.NetworkUtil;

import java.io.IOException;

public class ThreadServer implements Runnable {
    private NetworkUtil networkUtil;
    private Thread thread;
    private Server server;

    public ThreadServer(NetworkUtil networkUtil, Server server) {
        this.networkUtil = networkUtil;
        this.server = server;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Object obj = networkUtil.read();
                if (obj instanceof String) {
                    // send club to client
                    String clubName = (String) obj;
                    Club club = server.db.searchClub(clubName);
                    if (club != null) {
                        networkUtil.write(club);
                    }
                } else if (obj instanceof PlayerTransfer) {
                  PlayerTransfer playerTransfer = (PlayerTransfer) obj;
                  if (playerTransfer.getRequest().equals("sell")) {
                      // insert in transfer window
                      server.transferPlayerList.add(playerTransfer.getPlayer());
                  } else {
                      // move player to requesting club

                  }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    networkUtil.closeConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
