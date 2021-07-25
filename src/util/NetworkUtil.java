package util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkUtil {
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public NetworkUtil(Socket socket) throws IOException {
        this.socket = socket;
        openStream();
    }

    public NetworkUtil(String address, int port) throws IOException {
        socket = new Socket(address, port);
        openStream();
    }

    private void openStream() throws IOException {
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    public Object read() throws IOException, ClassNotFoundException {
        return objectInputStream.readUnshared();
    }

    public void write(Object obj) throws IOException {
        objectOutputStream.writeUnshared(obj);
        objectOutputStream.reset();
        objectOutputStream.flush();
    }

    public void closeConnection() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
    }
}
