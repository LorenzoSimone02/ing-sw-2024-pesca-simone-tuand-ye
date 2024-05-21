package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPlaceCardPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPlaceCardPacketHandler;
import it.polimi.ingsw.server.model.resources.ResourceTypeEnum;

import java.util.HashMap;

public class PlaceCardPacket extends Packet {

    private String player;
    private int newScore;
    private HashMap<String, Integer> resources;
    private final int cardId;
    private final int xCoord;
    private final int yCoord;

    public PlaceCardPacket(String player, int newScore, HashMap<String, Integer> resources, int cardId, int xCoord, int yCoord) {
        this.player = player;
        this.newScore = newScore;
        this.resources = resources;
        this.cardId = cardId;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public PlaceCardPacket(int cardId, int xCoord, int yCoord) {
        this.cardId = cardId;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public String getPlayer() {
        return player;
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

    public int getNewScore() {
        return newScore;
    }

    public HashMap<String, Integer> getResources() {
        return resources;
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
