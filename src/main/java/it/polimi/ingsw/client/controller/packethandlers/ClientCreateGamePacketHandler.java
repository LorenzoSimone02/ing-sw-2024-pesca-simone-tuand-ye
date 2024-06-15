package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.network.packets.JoinPacket;
import it.polimi.ingsw.network.packets.Packet;

/**
 * The class that handles the game creation packets from the server
 */
public class ClientCreateGamePacketHandler extends ClientPacketHandler {

    /**
     * The method handles the game creation packet
     * @param packet the game creation packet
     * @param clientManager the client manager
     */
    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        new Thread(() -> clientManager.getNetworkHandler().sendPacket(new JoinPacket())).start();
    }
}
