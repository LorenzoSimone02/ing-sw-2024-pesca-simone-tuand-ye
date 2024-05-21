package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientTurnCardPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerTurnCardPacketHandler;

public class TurnCardPacket extends Packet {

    private final int cardId;

    public TurnCardPacket(int cardId) {
        this.cardId = cardId;
    }

    public int getCardId() {
        return cardId;
    }

    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientTurnCardPacketHandler();
    }

    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return new ServerTurnCardPacketHandler();
    }
}
