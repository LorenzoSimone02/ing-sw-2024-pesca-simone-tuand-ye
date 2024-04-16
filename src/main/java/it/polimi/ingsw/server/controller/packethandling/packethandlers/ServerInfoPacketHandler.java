package it.polimi.ingsw.server.controller.packethandling.packethandlers;

import it.polimi.ingsw.network.packets.InfoPacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;

public class ServerInfoPacketHandler extends ServerPacketHandler {

    @Override
    public void handlePacket(Packet packet, GameController controller) {
        InfoPacket infoPacket = (InfoPacket) packet;
        System.out.println("Info packet received by " + infoPacket.getSender() + " with data: " + infoPacket.getData());
    }
}
