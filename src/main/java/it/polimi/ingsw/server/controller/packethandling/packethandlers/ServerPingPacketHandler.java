package it.polimi.ingsw.server.controller.packethandling.packethandlers;

import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.network.packets.PingPacket;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;

public class ServerPingPacketHandler extends ServerPacketHandler {

    @Override
    public void handlePacket(Packet packet, GameController controller) {
        PingPacket infoPacket = (PingPacket) packet;
        System.out.println("Ping packet response received by " + infoPacket.getSender());
    }
}