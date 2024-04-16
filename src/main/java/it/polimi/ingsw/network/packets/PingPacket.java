package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandling.ClientPacketHandler;
import it.polimi.ingsw.client.controller.packethandling.packethandlers.ClientPingPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.packethandlers.ServerPingPacketHandler;

public class PingPacket extends Packet {

    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientPingPacketHandler();
    }

    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return new ServerPingPacketHandler();
    }
}
