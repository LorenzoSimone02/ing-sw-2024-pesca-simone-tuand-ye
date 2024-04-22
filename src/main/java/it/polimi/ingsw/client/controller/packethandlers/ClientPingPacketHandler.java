package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.network.packets.PingPacket;

public class ClientPingPacketHandler extends ClientPacketHandler {

    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        PingPacket infoPacket = (PingPacket) packet;
        System.out.println("Ping packet request received by " + infoPacket.getSender());
        clientManager.getNetworkHandler().sendPacket(new PingPacket());
    }
}
