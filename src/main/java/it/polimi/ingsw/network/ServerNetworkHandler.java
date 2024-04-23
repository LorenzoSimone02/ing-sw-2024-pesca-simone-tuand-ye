package it.polimi.ingsw.network;

import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.network.rmi.RMIServer;
import it.polimi.ingsw.network.socket.SocketServer;
import it.polimi.ingsw.server.controller.GameController;

import java.io.IOException;
import java.util.*;

public class ServerNetworkHandler {

    private final String registryName;
    private final int rmiPort;
    private final int socketPort;
    private final ArrayList<ClientConnection> connections;
    private final Map<Packet, ClientConnection> packetQueue;
    private final GameController gameController;

    public ServerNetworkHandler(String registryName, int rmiPort, int socketPort) {
        this.registryName = registryName;
        this.rmiPort = rmiPort;
        this.socketPort = socketPort;
        connections = new ArrayList<>();
        packetQueue = Collections.synchronizedMap(new LinkedHashMap<>());
        gameController = new GameController(this);
    }

    public void start() {
        try {
            System.out.println("Starting RMI Server...");
            new RMIServer(this, registryName, rmiPort);
            System.out.println("RMI Server started on port " + rmiPort + " with name " + registryName);

            System.out.println("Starting Socket Server...");
            new SocketServer(this, socketPort);
            System.out.println("Socket Server started on port " + socketPort);

            Thread packetHandlerThread = new Thread(new PacketHandlerThread(this));
            packetHandlerThread.start();

            ClientPinger clientPinger = new ClientPinger(this);
            //clientPinger.run();

        } catch (IOException e) {
            System.err.println("Server exception: " + e);
        }
    }

    public synchronized void sendPacket(ClientConnection connection, Packet packet) {
        packet.setSender("Server");
        connection.receivePacket(packet);
    }

    public synchronized void sendPacketToAll(Packet packet) {
        packet.setSender("Server");
        for (ClientConnection connection : connections) {
            connection.receivePacket(packet);
        }
    }

    public synchronized void receivePacket(Packet packet, ClientConnection connection) {
        if (packet.getServerPacketHandler() != null) {
            packetQueue.put(packet, connection);
        } else {
            System.err.println("Received an unsupported packet");
        }
    }

    public synchronized void addConnection(ClientConnection connection) {
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

    public synchronized ClientConnection getConnectionByNickname(String nickname) {
        for (ClientConnection connection : connections) {
            if (connection.getUsername().equals(nickname)) {
                return connection;
            }
        }
        return null;
    }

    public GameController getGameController() {
        return gameController;
    }

    public Map<Packet, ClientConnection> getPacketQueue() {
        return packetQueue;
    }
}
