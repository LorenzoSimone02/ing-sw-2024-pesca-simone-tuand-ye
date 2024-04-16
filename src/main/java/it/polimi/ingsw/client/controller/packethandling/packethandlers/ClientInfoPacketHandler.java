package it.polimi.ingsw.client.controller.packethandling.packethandlers;

import it.polimi.ingsw.client.controller.packethandling.ClientPacketHandler;
import it.polimi.ingsw.network.packets.InfoPacket;
import it.polimi.ingsw.network.packets.Packet;

public class ClientInfoPacketHandler extends ClientPacketHandler {

    @Override
    public void handlePacket(Packet packet) {
        InfoPacket infoPacket = (InfoPacket) packet;
        System.out.println("Info packet received by " + infoPacket.getSender() + " with data: " + infoPacket.getData());
    }
}
