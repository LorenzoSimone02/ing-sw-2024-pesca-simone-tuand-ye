package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientDrawCardPacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerDrawCardPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;

public class DrawCardPacket extends Packet {

    private final int cardID;
    private int newCardID;
    private boolean gold;

    public DrawCardPacket(int cardID) {
        this.cardID = cardID;
    }

    public DrawCardPacket(int cardID, int newCardID) {
        this.cardID = cardID;
        this.newCardID = newCardID;
    }

    public DrawCardPacket(boolean gold) {
        cardID = -1;
        newCardID = -1;
        this.gold = gold;
    }

    public int getCardID() {
        return cardID;
    }

    public int getNewCardID() {
        return newCardID;
    }

    public boolean isGold() {
        return gold;
    }

    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientDrawCardPacketHandler();
    }

    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return new ServerDrawCardPacketHandler();
    }
}
