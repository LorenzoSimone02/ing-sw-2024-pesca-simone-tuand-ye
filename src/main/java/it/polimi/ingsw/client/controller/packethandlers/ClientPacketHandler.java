package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.network.packets.Packet;

public abstract class ClientPacketHandler {

    public abstract void handlePacket(Packet packet);
}
