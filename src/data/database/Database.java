package data.database;

import java.util.ArrayList;
import java.util.List;

/*
    * Database class maintains a list of all the
      players, countries, and clubs associated
      and executes necessary search and insert
      query methods.
      * All sorts of string searching have been
        kept case-insensitive.
 */

public class Database {
    private List<Player> playerList;
    private List<Country> countryList;
    private List<Club> clubList;

    public Database() {
        playerList = new ArrayList<>();
        countryList = new ArrayList<>();
        clubList = new ArrayList<>();
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    public List<Club> getClubList() {
        return clubList;
    }

    public void setClubList(List<Club> clubList) {
        this.clubList = clubList;
    }

    // returns null if not found
    public Player searchPlayerByName(String name) {
        for (Player p : playerList) {
            if (p.getName().equalsIgnoreCase(name)) return p;
        }
        return null;
    }

    public List<Player> searchPlayerByCountryAndClub(String countryName, String clubName) {
        List<Player> players = searchPlayerByCountry(countryName);
        if (clubName.equalsIgnoreCase("any")) return players;
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            if (!p.getClub().equalsIgnoreCase(clubName)) {
                players.remove(i);
                i--;
            }
        }
        return players;
    }

    public List<Player> searchPlayerByCountry(String countryName) {
        List<Player> players = new ArrayList<>();
        if (countryName.equalsIgnoreCase("any")) {
            players.addAll(this.playerList);
        }
        else {
            for (Country c: this.countryList
            ) {
                if (countryName.equalsIgnoreCase(c.getName())) {
                    players.addAll(c.getPlayerList());
                    break;
                }
            }
        }
        return players;
    }

    public List<Player> searchPlayerByPosition(String position) {
        List<Player> players = new ArrayList<>();
        for (Player p : this.playerList) {
            if (p.getPosition().equalsIgnoreCase(position)) players.add(p);
        }
        return players;
    }

    // returns -2 if club already has player's number
    // returns -1 if player's club is full
    // returns 0 if player is already in the list
    // returns 1 if player is added successfully
    public int addPlayer(Player player) {
        Player p = searchPlayerByName(player.getName());
        if (p == null) {
            // player is not in the list

            // check club boundary
            boolean b = checkClubValidity(player.getClub());
            if (!b) return -1;

            // check if the number exists in the club to be assigned
            b = checkDuplicateNumber(player.getClub(), player.getNumber());
            if (b) return -2;

            // modify user input for club and country
            // set them to already existing names
            modifyClubName(player);
            modifyCountryName(player);

            playerList.add(player);
            updateCountryList(player);
            updateClubList(player);
            return 1;
        }
        return 0;
    }

    public void addPlayer(List<Player> playerList) {
        for (Player player:
             playerList) {
            addPlayer(player);
        }
    }

    private void updateCountryList(Player player) {
        for (Country c: countryList
             ) {
            if (c.getName().equalsIgnoreCase(player.getCountry())) {
                c.addPlayer(player);
                return;
            }
        }
        // add a new country
        Country c = new Country(player);
        countryList.add(c);
    }

    // returns false if club already has maximum number of players
    public boolean checkClubValidity(String club) {
        for (Club c: clubList
             ) {
            if (c.getName().equalsIgnoreCase(club) && c.getPlayerCount() == c.getMaxPlayerLimit()) {
                return false;
            }
        }
        return true;
    }

    private void updateClubList(Player player) {
        String club = player.getClub();
        for (Club c: clubList
        ) {
            if (c.getName().equalsIgnoreCase(club)) {
                c.addPlayer(player);
                return;
            }
        }
        // new club has to be added to list
        Club c = new Club(player);
        clubList.add(c);
    }

    // returns null if no club with the given name is found
    public Club searchClub(String clubName) {
        for (Club c: clubList
             ) {
            if (c.getName().equalsIgnoreCase(clubName)) return c;
        }
        return null;
    }

    private void modifyClubName(Player player) {
        for (Club club: clubList
             ) {
            if (club.getName().equalsIgnoreCase(player.getClub())) {
                player.setClub(club.getName());
                return;
            }
        }
    }

    private void modifyCountryName(Player player) {
        for (Country country: countryList
             ) {
            if (country.getName().equalsIgnoreCase(player.getCountry())) {
                player.setCountry(country.getName());
                return;
            }
        }
    }

    // returns true if given number already exists in the club
    public boolean checkDuplicateNumber(String club, int number) {
        for (Club c: clubList
             ) {
            if (c.getName().equalsIgnoreCase(club)) {
                return c.checkNumber(number);
            }
        }
        return false;
    }

    public void showCountryCount() {
        for (Country c: countryList
        ) {
            System.out.println(c);
        }
        System.out.println();
    }

    public List<Player> searchPlayerByAge(int lo, int hi) {
        List<Player> playerList = new ArrayList<>();
        for (Player p : this.playerList) {
            int age = p.getAge();
            if (age >= lo && age <= hi) playerList.add(p);
        }
        return playerList;
    }

    public List<Player> searchPlayerByHeight(double lo, double hi) {
        List<Player> playerList = new ArrayList<>();
        for (Player p : this.playerList) {
            double height = p.getHeight();
            if (height >= lo && height <= hi) playerList.add(p);
        }
        return playerList;
    }

    public List<Player> searchPlayerBySalary(double lo, double hi) {
        List<Player> players = new ArrayList<>();
        for (Player p : this.playerList) {
            double salary = p.getSalary();
            if (salary >= lo && salary <= hi) players.add(p);
        }
        return players;
    }

    public void removePlayerFromClub(String playerName) {
        for (Player player:
                this.playerList) {
            if (player.getName().equalsIgnoreCase(playerName)) {
                Club c = searchClub(player.getClub());
                c.removePlayer(playerName);
                return;
            }
        }
    }
}
