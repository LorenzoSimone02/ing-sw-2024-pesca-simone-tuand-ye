package it.polimi.ingsw.network;

import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.network.rmi.RMIServer;
import it.polimi.ingsw.network.socket.SocketServer;
import it.polimi.ingsw.server.controller.GameController;

import java.io.IOException;
import java.util.*;

public class ServerNetworkHandler {

    private final String registryName;
    private RMIServer rmiServer;
    private final int rmiPort;
    private SocketServer socketServer;
    private final int socketPort;
    private final ArrayList<ClientConnection> connections;
    private final Map<Packet, ClientConnection> packetQueue;
    private final GameController gameController;
    private boolean isLobby;

    public ServerNetworkHandler(String registryName, int rmiPort, int socketPort) {
        this.registryName = registryName;
        this.rmiPort = rmiPort;
        this.socketPort = socketPort;
        connections = new ArrayList<>();
        packetQueue = Collections.synchronizedMap(new LinkedHashMap<>());
        isLobby = false;
        gameController = new GameController(this);
    }

    public void start() {
        try {
            System.out.println("Starting RMI Server...");
            rmiServer = new RMIServer(this, registryName, rmiPort);
            System.out.println("RMI Server started on port " + rmiPort + " with name " + registryName);

            System.out.println("Starting Socket Server...");
            socketServer = new SocketServer(this, socketPort);
            System.out.println("Socket Server started on port " + socketPort);

            Thread packetHandlerThread = new Thread(new PacketHandlerThread(this));
            packetHandlerThread.start();

            Thread clientPingerThread = new Thread(new ClientPingerThread(this));
            clientPingerThread.start();

        } catch (IOException e) {
            System.err.println("Server exception: " + e);
        }
    }

    public synchronized void stop(){
        socketServer.stopServer();
        rmiServer.stopServer();
    }

    public synchronized void sendPacket(ClientConnection connection, Packet packet) {
        packet.setSender(UUID.fromString("00000000-0000-0000-0000-000000000000"));
        connection.receivePacket(packet);
    }

    public synchronized void sendPacketToAll(Packet packet) {
        packet.setSender(UUID.fromString("00000000-0000-0000-0000-000000000000"));
        for (ClientConnection connection : connections) {
            connection.receivePacket(packet);
        }
    }

    public synchronized void receivePacket(Packet packet, ClientConnection connection) {
        if (packet.getServerPacketHandler() != null) {
            connection.setLastPing(System.currentTimeMillis());
            packetQueue.put(packet, connection);
            notifyAll();
        } else {
            System.err.println("Received an unsupported packet");
        }
    }

    public synchronized void addConnection(ClientConnection connection) {
        if (!connections.contains(connection)) {
            connections.add(connection);
        }
    }

    public synchronized void removeConnection(ClientConnection connection) {
        connections.remove(connection);
    }

    public ArrayList<ClientConnection> getConnections() {
        return connections;
    }

    public synchronized ClientConnection getConnectionByNickname(String username) {
        if (username == null) return null;
        for (ClientConnection connection : connections) {
            if (connection.getUsername().equalsIgnoreCase(username)) {
                return connection;
            }
        }
        return null;
    }

    public synchronized ClientConnection getConnectionByUUID(UUID uuid) {
        if (uuid == null) return null;
        for (ClientConnection connection : connections) {
            if (connection.getUUID().equals(uuid)) {
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

    public boolean isLobby() {
        return isLobby;
    }

    public void setLobby(boolean isLobby) {
        this.isLobby = isLobby;
    }
}
