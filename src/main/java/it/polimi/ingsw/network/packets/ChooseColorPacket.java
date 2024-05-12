package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientChooseColorPacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerChooseColorPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;

public class ChooseColorPacket extends Packet {

    private final String username;
    private final String color;

    public ChooseColorPacket(String username, String color) {
        this.username = username;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientChooseColorPacketHandler();
    }

    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return new ServerChooseColorPacketHandler();
    }
}
