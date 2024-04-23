package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;
import it.polimi.ingsw.server.model.card.ResourceCard;

public class PlaceCardPacket extends Packet {

    private final ResourceCard card;
    private final int xCoord;
    private final int yCoord;

    public PlaceCardPacket(ResourceCard card, int xCoord, int yCoord) {
        this.card = card;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public ResourceCard getCard() {
        return card;
    }

    public int getXCoord() {
        return xCoord;
    }

    public int getYCoord() {
        return yCoord;
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
