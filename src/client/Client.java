package client;

import util.NetworkUtil;

import java.io.IOException;

public class Client {
    public Client(String address, int port) {
        try {
            NetworkUtil networkUtil = new NetworkUtil(address, port);
            new ReadThreadClient(networkUtil);
            new WriteThreadClient(networkUtil);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final String address = "127.0.0.1";
        final int port = 45045;
        new Client(address, port);
    }
}
