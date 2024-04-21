package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.network.packets.PingPacket;

public class ClientPingPacketHandler extends ClientPacketHandler {

    @Override
    public void handlePacket(Packet packet) {
        PingPacket infoPacket = (PingPacket) packet;
        System.out.println("Ping packet request received by " + infoPacket.getSender());
    }
}
