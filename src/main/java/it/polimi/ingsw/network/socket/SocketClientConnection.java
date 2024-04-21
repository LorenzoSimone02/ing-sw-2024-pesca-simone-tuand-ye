package it.polimi.ingsw.network.socket;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.packets.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClientConnection extends ClientConnection implements Runnable {

    private final Socket socket;
    private final SocketServer socketServer;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public SocketClientConnection(Socket socket, SocketServer socketServer) {
        this.socket = socket;
        this.socketServer = socketServer;
    }

    @Override
    public void run() {
        try {
            synchronized (socket) {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
            }
        } catch (IOException e) {
            System.err.println("Could not create the streams " + e);
        }
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Packet packet = (Packet) in.readObject();
                socketServer.getServerNetworkHandler().addConnection(this);
                socketServer.getServerNetworkHandler().receivePacket(packet, this);
            } catch (IOException e) {
                System.err.println("Lost connection with a client");
                socketServer.getServerNetworkHandler().removeConnection(this);
                socketServer.getServerNetworkHandler().getGameController().onDisconnect(this.getNickname());
                Thread.currentThread().interrupt();
            } catch (ClassNotFoundException e) {
                System.err.println("Could not read the packet " + e);
            }
        }
    }

    @Override
    public void receivePacket(Packet packet) {
        new Thread(() -> {
            try {
                out.writeObject(packet);
                out.flush();
            } catch (IOException e) {
                System.err.println("Error sending packet: " + e);
            }
        }).start();
    }
}
