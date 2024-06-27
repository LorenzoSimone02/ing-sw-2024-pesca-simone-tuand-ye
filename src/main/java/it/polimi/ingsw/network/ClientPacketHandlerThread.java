package it.polimi.ingsw.network;

import it.polimi.ingsw.network.packets.Packet;

public class ClientPacketHandlerThread implements Runnable {

    /**
     * The ClientNetworkHandler that manages the packets
     */
    private final ClientNetworkHandler clientNetworkHandler;

    /**
     * Constructor of the class
     *
     * @param clientNetworkHandler the ClientNetworkHandler
     */
    public ClientPacketHandlerThread(ClientNetworkHandler clientNetworkHandler) {
        this.clientNetworkHandler = clientNetworkHandler;
    }

    /**
     * The method handles the packets received by the server
     */
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Packet packet = clientNetworkHandler.getPacketQueue().take();
                packet.getClientPacketHandler().handlePacket(packet, clientNetworkHandler.getClientManager());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
