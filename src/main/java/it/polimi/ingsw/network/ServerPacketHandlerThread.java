package it.polimi.ingsw.network;

import it.polimi.ingsw.network.packets.Packet;

import java.util.Iterator;

/**
 * ServerPacketHandlerThread is the class that represents the thread that handles the packets received by the server
 */
public class ServerPacketHandlerThread implements Runnable {

    /**
     * The ServerNetworkHandler that manages the packets
     */
    private final ServerNetworkHandler serverNetworkHandler;

    /**
     * Constructor of the class
     *
     * @param serverNetworkHandler the ServerNetworkHandler
     */
    public ServerPacketHandlerThread(ServerNetworkHandler serverNetworkHandler) {
        this.serverNetworkHandler = serverNetworkHandler;
    }

    /**
     * The method handles the packets received by the server
     */
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Packet packet = serverNetworkHandler.getPacketQueue().take();
                ClientConnection connection = serverNetworkHandler.getConnectionByUUID(packet.getSender());
                packet.getServerPacketHandler().handlePacket(packet, serverNetworkHandler.getGameController(), connection);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
