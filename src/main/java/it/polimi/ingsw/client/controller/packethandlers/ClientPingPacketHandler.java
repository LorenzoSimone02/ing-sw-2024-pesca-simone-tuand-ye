package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.network.packets.PingPacket;

/**
 * The class that handles the client pinging packets from the server
 */
public class ClientPingPacketHandler extends ClientPacketHandler {

    /**
     * The method handles the client pinging packet
     * @param packet the client pinging packet
     * @param clientManager the client manager
     */
    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        clientManager.getGameState().setLastPing(System.currentTimeMillis());
        new Thread(() -> clientManager.getNetworkHandler().sendPacket(new PingPacket())).start();
    }
}
