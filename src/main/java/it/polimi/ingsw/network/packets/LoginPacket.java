package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientLoginPacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerLoginPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;

public class LoginPacket extends Packet {

    private final String username;

    public LoginPacket(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientLoginPacketHandler();
    }

    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return new ServerLoginPacketHandler();
    }
}