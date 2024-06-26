package it.polimi.ingsw.network.socket;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.packets.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * SocketClientConnection is the class that represents the server-side socket client connection
 */
public class SocketClientConnection extends ClientConnection implements Runnable {

    /**
     * The socket of the connection
     */
    private final Socket socket;

    /**
     * The server-side socket handler
     */
    private final SocketServer socketServer;

    /**
     * The output stream of the connection
     */
    private ObjectOutputStream out;

    /**
     * The input stream of the connection
     */
    private ObjectInputStream in;

    /**
     * Constructor of the class
     * @param socket the socket of the connection
     * @param socketServer the server-side socket handler
     */
    public SocketClientConnection(Socket socket, SocketServer socketServer) {
        this.socket = socket;
        this.socketServer = socketServer;
    }

    /**
     * The method runs the connection
     */
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
                ClientConnection conn = socketServer.getServerNetworkHandler().getConnectionByUUID(packet.getSender());
                if (conn != null) {
                    if (conn == this) {
                        socketServer.getServerNetworkHandler().receivePacket(packet, this);
                    } else {
                        socketServer.getServerNetworkHandler().removeConnection(conn);
                        setUsername(conn.getUsername());
                        setUUID(packet.getSender());
                        socketServer.getServerNetworkHandler().addConnection(this);
                        socketServer.getServerNetworkHandler().receivePacket(packet, this);
                    }
                } else {
                    setUUID(packet.getSender());
                    socketServer.getServerNetworkHandler().addConnection(this);
                    socketServer.getServerNetworkHandler().receivePacket(packet, this);
                }
            } catch (IOException e) {
                if (getUsername() != null) {
                    System.err.println("Lost connection with the Client " + getUsername() + " due to " + e.getMessage());
                    socketServer.getServerNetworkHandler().getGameController().onDisconnect(getUsername());
                }
                socketServer.getServerNetworkHandler().removeConnection(this);
                Thread.currentThread().interrupt();
            } catch (ClassNotFoundException | InterruptedException e) {
                System.err.println("Could not read the packet " + e);
            }
        }
    }

    /**
     * The method tries to receive a packet from the server
     * @param packet the packet to receive
     */
    @Override
    public synchronized void receivePacket(Packet packet) {
        try {
            out.writeObject(packet);
            out.flush();
            out.reset();
        } catch (IOException e) {
            System.err.println("Error sending packet: " + e);
        }
    }
}
