package it.polimi.ingsw.client.controller.packethandling;

import it.polimi.ingsw.network.packets.Packet;

public abstract class ClientPacketHandler {

    public abstract void handlePacket(Packet packet);
}
