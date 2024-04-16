package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandling.ClientPacketHandler;
import it.polimi.ingsw.client.controller.packethandling.packethandlers.ClientInfoPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.packethandlers.ServerInfoPacketHandler;

public class InfoPacket extends Packet {

    private final String data;

    public InfoPacket(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientInfoPacketHandler();
    }

    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return new ServerInfoPacketHandler();
    }
}
