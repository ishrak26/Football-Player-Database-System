package data.database;

import java.util.ArrayList;
import java.util.List;

public class Club {
    private String name;
    private int playerCount;
    private Player[] players;
    private double budget;

    private final int MAX_PLAYER_LIMIT = 7;

    public Club() {
        players = new Player[MAX_PLAYER_LIMIT];
    }

    public Club(Player player) {
        players = new Player[MAX_PLAYER_LIMIT];
        name = player.getClub();
        addPlayer(player);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public int getMaxPlayerLimit() {
        return MAX_PLAYER_LIMIT;
    }

    public void addPlayer(Player player) {
        players[playerCount++] = player;
    }

    public List<Player> getMaxSalaryPlayers() {
        List<Player> playerList = new ArrayList<>();

        // find maximum value of salary
        double salary = players[0].getSalary();
        for (int i = 1; i < playerCount; i++) {
            if (players[i].getSalary() > salary) {
                salary = players[i].getSalary();
            }
        }

        // find players with maximum salary
        double eps = 0.000001; // precision range
        for (int i = 0; i < playerCount; i++) {
            if (Math.abs(salary - players[i].getSalary()) < eps) {
                playerList.add(players[i]);
            }
        }
        return playerList;
    }

    public List<Player> getMaxAgePlayers() {
        List<Player> playerList = new ArrayList<>();

        // find maximum age
        int age = players[0].getAge();
        for (int i = 1; i < playerCount; i++) {
            if (players[i].getAge() > age) {
                age = players[i].getAge();
            }
        }

        // find players with maximum age
        for (int i = 0; i < playerCount; i++) {
            if (players[i].getAge() == age) {
                playerList.add(players[i]);
            }
        }
        return playerList;
    }

    public List<Player> getMaxHeightPlayers() {
        List<Player> playerList = new ArrayList<>();

        // find maximum height
        double height = players[0].getHeight();
        for (int i = 1; i < playerCount; i++) {
            if (players[i].getHeight() > height) {
                height = players[i].getHeight();
            }
        }

        // find players with maximum height
        double eps = 0.000001;
        for (int i = 0; i < playerCount; i++) {
            if (Math.abs(height - players[i].getHeight()) < eps) {
                playerList.add(players[i]);
            }
        }
        return playerList;
    }

    public double getTotalYearlySalary() {
        double sum = 0;
        for (int i = 0; i < playerCount; i++) {
            sum += players[i].getSalary();
        }
        sum *= 52; // 52 weeks in a year
        return sum;
    }

    // returns true if number already exists in the club
    public boolean checkNumber(int number) {
        for (int i = 0; i < playerCount; i++) {
            if (players[i].getNumber() == number) return true;
        }
        return false;
    }
}
