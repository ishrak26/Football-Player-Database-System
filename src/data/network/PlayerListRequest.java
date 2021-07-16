package data.network;

import data.database.Player;

import java.io.Serializable;
import java.util.List;

public class PlayerListRequest implements Serializable {
    private String sender;
    private List<Player> playerList;

}
