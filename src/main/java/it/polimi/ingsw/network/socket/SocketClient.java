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

/**
 * SocketClient is the class that represents the client-side socket network handler
 */
public class SocketClient extends ClientNetworkHandler {

    /**
     * The IP address of the server
     */
    private final String ip;

    /**
     * The port of the server
     */
    private final int port;

    /**
     * The output stream of the connection
     */
    private ObjectOutputStream out;

    /**
     * The input stream of the connection
     */
    private ObjectInputStream in;

    /**
     * Executor service used to send packets
     */
    private final ExecutorService executorService;

    /**
     * Constructor of the class
     *
     * @param ip   the IP address of the server
     * @param port the port of the server
     * @throws RemoteException if there is an error with the remote connection
     */
    public SocketClient(String ip, int port) throws RemoteException {
        super();
        this.ip = ip;
        this.port = port;
        this.executorService = Executors.newSingleThreadExecutor();
        init();
    }

    /**
     * The method initializes the connection with the server
     */
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

    /**
     * The method tries to send a packet to the server
     *
     * @param packet the packet to send
     */
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
