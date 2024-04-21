package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.network.packets.PingPacket;
import it.polimi.ingsw.server.controller.GameController;

public class ServerPingPacketHandler extends ServerPacketHandler {

    @Override
    public void handlePacket(Packet packet, GameController controller, ClientConnection connection) {
        PingPacket infoPacket = (PingPacket) packet;
        System.out.println("Ping packet response received by " + infoPacket.getSender());
    }
}