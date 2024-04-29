package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;

public class ChooseColorPacket extends Packet {

    private final String color;

    public ChooseColorPacket(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return null;
    }

    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return null;
    }
}
