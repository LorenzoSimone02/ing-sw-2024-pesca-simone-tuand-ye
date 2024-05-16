package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPlaceCardPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPlaceCardPacketHandler;

public class PlaceCardPacket extends Packet {

    private final int cardId;
    private final int xCoord;
    private final int yCoord;

    public PlaceCardPacket(int cardId, int xCoord, int yCoord) {
        this.cardId = cardId;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public int getCardId() {
        return cardId;
    }

    public int getXCoord() {
        return xCoord;
    }

    public int getYCoord() {
        return yCoord;
    }

    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientPlaceCardPacketHandler();
    }

    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return new ServerPlaceCardPacketHandler();
    }
}
