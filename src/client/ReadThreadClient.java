package client;

import util.NetworkUtil;

import java.io.IOException;

public class ReadThreadClient implements Runnable {
    private NetworkUtil networkUtil;
    private Thread thread;

    public ReadThreadClient(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Object obj = networkUtil.read();


            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    networkUtil.closeConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
