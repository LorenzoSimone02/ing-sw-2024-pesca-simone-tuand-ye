package it.polimi.ingsw.network;

import it.polimi.ingsw.network.packets.ClientPacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.network.packets.PingRequestPacket;
import it.polimi.ingsw.network.rmi.RMIServer;
import it.polimi.ingsw.network.socket.SocketServer;

import java.io.IOException;
import java.util.ArrayList;

public class ServerNetworkHandler {

    private final String registryName;
    private final int rmiPort;
    private final int socketPort;
    private final ArrayList<ClientConnection> connections;

    public ServerNetworkHandler(String registryName, int rmiPort, int socketPort) {
        this.registryName = registryName;
        this.rmiPort = rmiPort;
        this.socketPort = socketPort;
        connections = new ArrayList<>();
    }

    public void start() {
        try {
            System.out.println("Starting RMI Server...");
            new RMIServer(this, registryName, rmiPort);
            System.out.println("RMI Server started on port " + rmiPort + " with name " + registryName);

            System.out.println("Starting Socket Server...");
            new SocketServer(this, socketPort);
            System.out.println("Socket Server started on port " + socketPort);

            //Pinger thread
            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(3000);
                        for (ClientConnection connection : connections) {
                            sendPacket(connection, new PingRequestPacket());
                        }
                    } catch (InterruptedException e) {
                        System.err.println("Ping thread interrupted");
                    }
                }
            }).start();

        } catch (IOException e) {
            System.err.println("Server exception: " + e);
        }
    }

    public void sendPacket(ClientConnection connection, Packet packet) {
        connection.receivePacket(packet);
    }

    public void receivePacket(ClientPacket packet) {
        packet.handle();
    }

    public void addConnection(ClientConnection connection) {
        if (!connections.contains(connection)) {
            connections.add(connection);
        }
    }

    public void removeConnection(ClientConnection connection) {
        connections.remove(connection);
    }

    public ArrayList<ClientConnection> getConnections() {
        return connections;
    }
}
