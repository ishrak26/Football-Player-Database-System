package data.database;

import java.util.ArrayList;
import java.util.List;

public class Country {
    private String name;
    private List<Player> playerList;

    public Country() {
        playerList = new ArrayList<>();
    }

    public Country(Player player) {
        playerList = new ArrayList<>();
        name = player.getCountry();
        addPlayer(player);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public void addPlayer(Player p) {
        playerList.add(p);
    }

    @Override
    public String toString() {
        return "Country: " + name + ", Player count: " + playerList.size();
    }
}
