package it.polimi.ingsw.network.socket;

import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.packets.ClientPacket;
import it.polimi.ingsw.network.packets.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

public class SocketClient extends ClientNetworkHandler {

    private Socket socket;
    private final String ip;
    private final int port;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public SocketClient(String ip, int port) throws RemoteException {
        super();
        this.ip = ip;
        this.port = port;
        init();
    }

    public void init() {
        try {
            socket = new Socket(ip, port);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Socket network error: " + e);
        }

        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Packet packet = (Packet) in.readObject();
                    receivePacket(packet);
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println("Error receiving packet: " + e);
                }
            }
        }).start();

    }

    public void sendPacket(ClientPacket packet) {
        new Thread(() -> {
            try {
                super.sendPacket(packet);
                out.writeObject(packet);
                out.flush();
            } catch (IOException e) {
                System.err.println("Error sending packet: " + e);
            }
        }).start();
    }
}
