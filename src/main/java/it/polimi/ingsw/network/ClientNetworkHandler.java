package it.polimi.ingsw.network;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.network.packets.Packet;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * ClientNetworkHandler is the class that handles the network communication on the client side
 */
public class ClientNetworkHandler extends UnicastRemoteObject {

    /**
     * The client manager that contains every information about the client
     */
    private ClientManager clientManager;

    /**
     * The queue of packets received by the Server to be handled
     */
    private final LinkedBlockingQueue<Packet> packetQueue;

    /**
     * Constructor of the class
     *
     * @throws RemoteException if there is an error with the remote connection
     */
    public ClientNetworkHandler() throws RemoteException {
        super();
        packetQueue = new LinkedBlockingQueue<>();

        Thread packetHandlerThread = new Thread(new ClientPacketHandlerThread(this));
        packetHandlerThread.start();
    }

    /**
     * The method tries to send a packet to the server
     *
     * @param packet the packet to send
     */
    public synchronized void sendPacket(Packet packet) {
        packet.setSender(clientManager.getGameState().getUuid());
    }

    /**
     * The method tries to receive a packet from the server
     *
     * @param packet the packet received
     */
    public synchronized void receivePacket(Packet packet) {
        if (packet.getClientPacketHandler() != null) {
            clientManager.getGameState().setLastPing(System.currentTimeMillis());
            packetQueue.add(packet);
        } else {
            System.err.println("Received an unsupported packet");
        }
    }

    /**
     * The method returns the client manager
     *
     * @return the client manager
     */
    public ClientManager getClientManager() {
        return clientManager;
    }

    /**
     * The method sets the client manager
     *
     * @param clientManager the client manager
     */
    public void setClientManager(ClientManager clientManager) {
        this.clientManager = clientManager;
    }

    /**
     * The method returns the queue of packets
     *
     * @return the queue of packets
     */
    public LinkedBlockingQueue<Packet> getPacketQueue() {
        return packetQueue;
    }
}
