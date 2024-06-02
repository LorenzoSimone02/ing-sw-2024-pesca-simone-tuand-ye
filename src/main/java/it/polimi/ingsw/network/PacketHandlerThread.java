package it.polimi.ingsw.network;

import it.polimi.ingsw.network.packets.Packet;

import java.util.Iterator;

/**
 * PacketHandlerThread is the class that represents the thread that handles the packets received by the server
 */
public class PacketHandlerThread implements Runnable {

    /**
     * The ServerNetworkHandler that manages the packets
     */
    private final ServerNetworkHandler serverNetworkHandler;

    /**
     * Constructor of the class
     * @param serverNetworkHandler the ServerNetworkHandler
     */
    public PacketHandlerThread(ServerNetworkHandler serverNetworkHandler) {
        this.serverNetworkHandler = serverNetworkHandler;
    }

    /**
     * The method handles the packets received by the server
     */
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (serverNetworkHandler) {
                if (serverNetworkHandler.getPacketQueue().isEmpty()) {
                    try {
                        serverNetworkHandler.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                Iterator<Packet> packetIterator = serverNetworkHandler.getPacketQueue().keySet().iterator();
                Packet packet = packetIterator.next();
                packet.getServerPacketHandler().handlePacket(packet, serverNetworkHandler.getGameController(), serverNetworkHandler.getPacketQueue().get(packet));
                packetIterator.remove();
            }
        }
    }
}
