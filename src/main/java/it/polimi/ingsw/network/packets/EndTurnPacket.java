package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientEndTurnPacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerEndTurnPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;

public class EndTurnPacket extends Packet {

    private final String activePlayer;

    public EndTurnPacket(String activePlayer) {
        this.activePlayer = activePlayer;
    }

    public String getActivePlayer() {
        return activePlayer;
    }

    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientEndTurnPacketHandler();
    }

    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return new ServerEndTurnPacketHandler();
    }
}
