package data.network;

import data.database.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlayerListRequest implements Serializable {
    private String clubName;
    private String type;
    private List<Player> playerList;

    public PlayerListRequest() {
        playerList = new ArrayList<>();
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
