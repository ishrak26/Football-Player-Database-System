package server;

import data.database.Club;
import data.database.Player;
import data.network.*;
import util.NetworkUtil;

import java.io.IOException;
import java.util.stream.Collectors;

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
        try {
            while (true) {
                Object obj = networkUtil.read();
                if (obj instanceof Message) {
                    Message msg = (Message) obj;
                    if (msg.getMessageHeader() == MessageHeader.SELL) {
                        String playerName = msg.getMessage();
                        networkUtil.write(server.addToTransferWindow(playerName));
                    } else if (msg.getMessageHeader() == MessageHeader.CLUB_INFO) {
                        String clubName = msg.getMessage();
                        networkUtil.write(server.db.searchClub(clubName));
                    } else if (msg.getMessageHeader() == MessageHeader.TRANSFER_WINDOW) {
                        networkUtil.write(server.getTransferPlayerList());
                    } else if (msg.getMessageHeader() == MessageHeader.LOGOUT) {
                        networkUtil.write(server.logoutClub(msg.getMessage()));
                    } else if (msg.getMessageHeader() == MessageHeader.CLUB_LIST) {
                        networkUtil.write(server.sendClubList());
                    }
                } else if (obj instanceof LoginInfo) {
                    LoginInfo loginInfo = (LoginInfo) obj;
                    if (loginInfo.getMessageHeader() == MessageHeader.REGISTER) {
                        networkUtil.write(server.registerClub(
                                loginInfo.getUsername(), loginInfo.getPassword(), networkUtil));
                    } else if (loginInfo.getMessageHeader() == MessageHeader.LOGIN) {
                        networkUtil.write(server.loginClub(loginInfo.getUsername(), loginInfo.getPassword()));
                    } else if (loginInfo.getMessageHeader() == MessageHeader.CHANGE_PASS) {
                        networkUtil.write(server.changePassword(loginInfo.getUsername(), loginInfo.getPassword(),
                                loginInfo.getNewPassword()));
                    }
                } else if (obj instanceof BuyInfo) {
                    BuyInfo buyInfo = (BuyInfo) obj;
                    if (buyInfo.getMessageHeader() == MessageHeader.BUY) {
                        System.out.println("buy info object received");
                        networkUtil.write(server.sellPlayer(buyInfo.getPlayerName(), buyInfo.getClubName()));
                    }
                }
            }
        }
        catch (IOException | ClassNotFoundException e) {
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

