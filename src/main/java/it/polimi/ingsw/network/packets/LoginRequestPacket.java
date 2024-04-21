package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerLoginRequestPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;

public class LoginRequestPacket extends Packet {

    private final String username;

    public LoginRequestPacket(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return null;
    }

    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return new ServerLoginRequestPacketHandler();
    }
}