package it.polimi.ingsw.network;

import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.network.rmi.RMIServer;
import it.polimi.ingsw.network.socket.SocketServer;
import it.polimi.ingsw.server.controller.GameController;

import java.io.IOException;
import java.net.Inet4Address;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * ServerNetworkHandler is the class that manages the server-side network
 */
public class ServerNetworkHandler {

    /**
     * The name of the registry
     */
    private final String registryName;

    /**
     * The server-side RMI handler
     */
    private RMIServer rmiServer;

    /**
     * The port of the RMI server
     */
    private final int rmiPort;

    /**
     * The server-side socket handler
     */
    private SocketServer socketServer;

    /**
     * The port of the socket server
     */
    private final int socketPort;

    /**
     * The list of the client connections
     */
    private final ArrayList<ClientConnection> connections;

    /**
     * The queue of packets received by the Clients to be handled
     */
    private final LinkedBlockingQueue<Packet> packetQueue;

    /**
     * The server-side game controller
     */
    private final GameController gameController;

    /**
     * The boolean that indicates if the server is a lobby
     */
    private boolean isLobby;

    /**
     * Constructor of the class
     * @param registryName the name of the registry
     * @param rmiPort the port of the RMI server
     * @param socketPort the port of the socket server
     */
    public ServerNetworkHandler(String registryName, int rmiPort, int socketPort) {
        this.registryName = registryName;
        this.rmiPort = rmiPort;
        this.socketPort = socketPort;
        connections = new ArrayList<>();
        packetQueue = new LinkedBlockingQueue<>();
        isLobby = false;
        gameController = new GameController(this);
    }

    /**
     * The method starts the server
     */
    public void start() {
        try {
            String ipAddress = Inet4Address.getLocalHost().getHostAddress();

            System.out.println("Starting RMI Server...");
            rmiServer = new RMIServer(this, registryName, rmiPort);
            System.out.println("RMI Server started on " + ipAddress + ":" + rmiPort + " with name " + registryName);

            System.out.println("Starting Socket Server...");
            socketServer = new SocketServer(this, socketPort);
            System.out.println("Socket Server started on " + ipAddress + ":" + socketPort);

            Thread packetHandlerThread = new Thread(new ServerPacketHandlerThread(this));
            packetHandlerThread.start();

            Thread clientPingerThread = new Thread(new ClientPingerThread(this));
            clientPingerThread.start();

        } catch (IOException e) {
            System.err.println("Server exception: " + e);
        }
    }

    /**
     * The method stops the server
     */
    public synchronized void stop(){
        connections.clear();
        socketServer.stopServer();
        rmiServer.stopServer();
    }

    /**
     * The method sends a packet to a client connection
     * @param connection the client connection
     * @param packet the packet to send
     */
    public synchronized void sendPacket(ClientConnection connection, Packet packet) {
        packet.setSender(UUID.fromString("00000000-0000-0000-0000-000000000000"));
        connection.receivePacket(packet);
    }

    /**
     * The method sends a packet to all the client connections
     * @param packet the packet to send
     */
    public synchronized void sendPacketToAll(Packet packet) {
        packet.setSender(UUID.fromString("00000000-0000-0000-0000-000000000000"));
        for (ClientConnection connection : connections) {
            connection.receivePacket(packet);
        }
    }

    /**
     * The method handles incoming packets if they are supported by the server
     * @param packet the packet received
     * @param connection the client connection that sent the packet
     */
    public synchronized void receivePacket(Packet packet, ClientConnection connection) throws InterruptedException {
        if (packet.getServerPacketHandler() != null) {
            connection.setLastPing(System.currentTimeMillis());
            packetQueue.put(packet);
        } else {
            System.err.println("Received an unsupported packet");
        }
    }

    /**
     * The method adds a client connection to the list
     * @param connection the client connection to add
     */
    public synchronized void addConnection(ClientConnection connection) {
        if (!connections.contains(connection)) {
            connections.add(connection);
        }
    }

    /**
     * The method removes a client connection from the list
     * @param connection the client connection to remove
     */
    public synchronized void removeConnection(ClientConnection connection) {
        connections.remove(connection);
    }

    /**
     * The method returns the list of the client connections
     * @return the list of the client connections
     */
    public ArrayList<ClientConnection> getConnections() {
        return connections;
    }

    /**
     * The method returns a client connection by its nickname
     * @param username the nickname of the client connection
     * @return the client connection
     */
    public synchronized ClientConnection getConnectionByNickname(String username) {
        if (username == null) return null;
        for (ClientConnection connection : connections) {
            if (connection.getUsername().equalsIgnoreCase(username)) {
                return connection;
            }
        }
        return null;
    }

    /**
     * The method returns a client connection by its UUID
     * @param uuid the UUID of the client connection
     * @return the client connection
     */
    public synchronized ClientConnection getConnectionByUUID(UUID uuid) {
        if (uuid == null) return null;
        for (ClientConnection connection : connections) {
            if (connection.getUUID().equals(uuid)) {
                return connection;
            }
        }
        return null;
    }

    /**
     * The method returns the server-side game controller
     * @return the server-side game controller
     */
    public GameController getGameController() {
        return gameController;
    }

    /**
     * The method returns the map that associates the client connections with their packets
     * @return the map that associates the client connections with their packets
     */
    public synchronized LinkedBlockingQueue<Packet> getPacketQueue() {
        return packetQueue;
    }

    public SocketServer getSocketServer(){
        return socketServer;
    }

    public RMIServer getRmiServer(){
        return rmiServer;
    }

    /**
     * The method returns the boolean that indicates if the server is a lobby
     * @return the boolean that indicates if the server is a lobby
     */
    public boolean isLobby() {
        return isLobby;
    }

    /**
     * The method sets the boolean that indicates if the server is a lobby
     * @param isLobby the boolean that indicates if the server is a lobby
     */
    public void setLobby(boolean isLobby) {
        this.isLobby = isLobby;
    }

}
