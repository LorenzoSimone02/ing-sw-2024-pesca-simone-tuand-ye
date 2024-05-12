package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientConnectionEventPacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;

public class ConnectionEventPacket extends Packet {

    private final String username;
    private final boolean isDisconnection;
    private final boolean isReconnection;

    public ConnectionEventPacket(String username, boolean isDisconnection, boolean isReconnection) {
        this.username = username;
        this.isDisconnection = isDisconnection;
        this.isReconnection = isReconnection;
    }

    public String getUsername() {
        return username;
    }

    public boolean isDisconnection() {
        return isDisconnection;
    }

    public boolean isReconnection() {
        return isReconnection;
    }

    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientConnectionEventPacketHandler();
    }

    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return null;
    }
}
