package client;

import util.NetworkUtil;

import java.io.IOException;

public class ReadThreadClient implements Runnable {
    private NetworkUtil networkUtil;
    private Thread thread;
    private Client client;

    public ReadThreadClient(Client client) {
        this.client = client;
        this.networkUtil = client.getNetworkUtil();
    }

    @Override
    public void run() {
//        while (true) {
//            try {
//                Object obj = networkUtil.read();
//
//            } catch (IOException | ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
