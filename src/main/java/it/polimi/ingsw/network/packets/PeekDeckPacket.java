package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPeekDeckPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPeekDeckPacketHandler;

public class PeekDeckPacket extends Packet {

    int cardId;
    boolean gold;

    public PeekDeckPacket(boolean gold) {
        this.gold = gold;
    }

    public PeekDeckPacket(int cardId) {
        this.cardId = cardId;
    }

    public int getCardId() {
        return cardId;
    }

    public boolean isGold() {
        return gold;
    }

    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientPeekDeckPacketHandler();
    }

    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return new ServerPeekDeckPacketHandler();
    }
}
