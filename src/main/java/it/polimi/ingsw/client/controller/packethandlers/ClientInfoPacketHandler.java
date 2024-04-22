package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.network.packets.InfoPacket;
import it.polimi.ingsw.network.packets.Packet;

public class ClientInfoPacketHandler extends ClientPacketHandler {

    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        InfoPacket infoPacket = (InfoPacket) packet;
        System.out.println(infoPacket.getData());
    }
}
