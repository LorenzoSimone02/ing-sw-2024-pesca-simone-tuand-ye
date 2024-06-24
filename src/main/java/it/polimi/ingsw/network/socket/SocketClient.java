package it.polimi.ingsw.network.socket;

import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.packets.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketClient extends ClientNetworkHandler {

    private final String ip;
    private final int port;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    /**
     * Executor service used to send packets
     */
    private final ExecutorService executorService;

    public SocketClient(String ip, int port) throws RemoteException {
        super();
        this.ip = ip;
        this.port = port;
        this.executorService = Executors.newSingleThreadExecutor();
        init();
    }

    public void init() {
        try {
            Socket socket = new Socket(ip, port);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("An error occurred while connecting to the Lobby: " + e.getMessage());
        }

        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Packet packet = (Packet) in.readObject();
                    receivePacket(packet);
                } catch (IOException e) {
                    System.err.println("Lost connection with the server");
                    Thread.currentThread().interrupt();
                } catch (ClassNotFoundException e) {
                    System.err.println("Error reading packet: " + e);
                }
            }
        }).start();
    }

    public synchronized void sendPacket(Packet packet) {
        executorService.submit(() -> {
            try {
                super.sendPacket(packet);
                out.writeObject(packet);
                out.flush();
                out.reset();
            } catch (IOException e) {
                System.err.println("Error sending packet: " + e);
            }
        });
    }
}
