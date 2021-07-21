package data.database;

import java.io.Serializable;
import java.util.*;

public class Club implements Serializable {
    private String name;
    private List<Player> players;
    private double budget;
    private String imgSource;

    private final int MAX_PLAYER_LIMIT = 7;

    public Club() {
        players = new ArrayList<>();
    }

    public Club(Player player) {
        players = new ArrayList<>();
        setName(player.getClub());
        addPlayer(player);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setImgSource("/images/logo/" + name.replace(' ', '_') + ".png");
    }

    public int getPlayerCount() {
        return players.size();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public String getImgSource() {
        return imgSource;
    }

    public void setImgSource(String imgSource) {
        this.imgSource = imgSource;
    }

    public List<String> getCountryList() {
        Set<String> countrySet = new LinkedHashSet<>();
        this.players.forEach(e -> countrySet.add(e.getCountry()));
        return new ArrayList<>(countrySet);
    }

    public List<String> getPositionList() {
        Set<String> positionSet = new LinkedHashSet<>();
        this.players.forEach(e -> positionSet.add(e.getPosition()));
        return new ArrayList<>(positionSet);
    }

    public int getMaxPlayerLimit() {
        return MAX_PLAYER_LIMIT;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public List<Player> getMaxSalaryPlayers() {
        List<Player> playerList = new ArrayList<>();

        // find maximum value of salary
        double salary = players.get(0).getSalary();
        for (int i = 1; i < players.size(); i++) {
            if (players.get(i).getSalary() > salary) {
                salary = players.get(i).getSalary();
            }
        }

        // find players with maximum salary
        double eps = 0.000001; // precision range
        for (Player player : players) {
            if (Math.abs(salary - player.getSalary()) < eps) {
                playerList.add(player);
            }
        }
        return playerList;
    }

    public List<Player> getMaxAgePlayers() {
        List<Player> playerList = new ArrayList<>();

        // find maximum age
        int age = players.get(0).getAge();
        for (int i = 1; i < players.size(); i++) {
            if (players.get(i).getAge() > age) {
                age = players.get(i).getAge();
            }
        }

        // find players with maximum age
        for (Player player : players) {
            if (player.getAge() == age) {
                playerList.add(player);
            }
        }
        return playerList;
    }

    public List<Player> getMaxHeightPlayers() {
        List<Player> playerList = new ArrayList<>();

        // find maximum height
        double height = players.get(0).getHeight();
        for (int i = 1; i < players.size(); i++) {
            if (players.get(i).getHeight() > height) {
                height = players.get(i).getHeight();
            }
        }

        // find players with maximum height
        double eps = 0.000001;
        for (Player player : players) {
            if (Math.abs(height - player.getHeight()) < eps) {
                playerList.add(player);
            }
        }
        return playerList;
    }

    public double getTotalYearlySalary() {
        double sum = 0;
        for (Player player : players) {
            sum += player.getSalary();
        }
        sum *= 52; // 52 weeks in a year
        return sum;
    }

    // returns true if number already exists in the club
    public boolean checkNumber(int number) {
        for (Player player : players) {
            if (player.getNumber() == number) return true;
        }
        return false;
    }

    public void removePlayer(String playerName) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getName().equalsIgnoreCase(playerName)) {
                players.remove(i);
                return;
            }
        }
    }
}
