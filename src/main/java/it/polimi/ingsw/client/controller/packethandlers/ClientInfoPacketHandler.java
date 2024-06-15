package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.network.packets.InfoPacket;
import it.polimi.ingsw.network.packets.Packet;

/**
 * The class that handles the info packets from the server
 */
public class ClientInfoPacketHandler extends ClientPacketHandler {

    /**
     * The method handles the info packet
     * @param packet the info packet
     * @param clientManager the client manager
     */
    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        InfoPacket infoPacket = (InfoPacket) packet;
        clientManager.getUserInterface().showMessage(infoPacket.getData());
    }
}
