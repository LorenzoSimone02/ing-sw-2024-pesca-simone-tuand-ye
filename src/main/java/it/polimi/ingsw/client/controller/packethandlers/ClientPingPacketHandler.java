package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.network.packets.PingPacket;

public class ClientPingPacketHandler extends ClientPacketHandler {

    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        clientManager.getNetworkHandler().sendPacket(new PingPacket());
    }
}
