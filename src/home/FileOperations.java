package home;

import data.database.Database;
import data.database.Player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

/*
    FileOperations class handles reading from and writing to a file.
 */

public class FileOperations {
    private String inputFileName;
    private String outputFileName;

    public FileOperations() {
        inputFileName = outputFileName = "players.txt";
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public void readFromFile(Database db) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(inputFileName));

        while (true) {
            String line = br.readLine();
            if (line == null) break;
            String[] tokens = line.split(",");

            Player player = new Player();
            player.setName(tokens[0]);
            player.setCountry(tokens[1]);
            player.setAge(Integer.parseInt(tokens[2]));
            player.setHeight(Double.parseDouble(tokens[3]));
            player.setClub(tokens[4]);
            player.setPosition(tokens[5]);
            player.setNumber(Integer.parseInt(tokens[6]));
            player.setSalary(Double.parseDouble(tokens[7]));

            int check = db.addPlayer(player);
            // check if player is added successfully
        }
        br.close();
    }

    public void writeToFile(List<Player> players) throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName));

        for (Player player : players) {
            bw.write(player.getName() + ",");
            bw.write(player.getCountry() + ",");
            bw.write(player.getAge() + ",");
            bw.write(player.getHeight() + ",");
            bw.write(player.getClub() + ",");
            bw.write(player.getPosition() + ",");
            bw.write(player.getNumber() + ",");
            bw.write(player.getSalary() + "");
            bw.write("\n");
        }
        bw.close();
    }
}
