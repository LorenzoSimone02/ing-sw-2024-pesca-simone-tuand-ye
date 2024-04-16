package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.server.controller.GameController;

public abstract class ServerPacketHandler {

    public abstract void handlePacket(Packet packet, GameController controller);
}
