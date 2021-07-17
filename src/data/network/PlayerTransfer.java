package data.network;

import data.database.Player;

public class PlayerTransfer {
    private Player player;
    private String request;

    public PlayerTransfer() {
    }

    public PlayerTransfer(Player player, String request) {
        this.player = player;
        this.request = request;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
