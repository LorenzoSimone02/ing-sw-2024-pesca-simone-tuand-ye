package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPlaceCardPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPlaceCardPacketHandler;

import java.util.HashMap;

/**
 * PlaceCardPacket is a Packet that contains the information about the placement of a card
 */
public class PlaceCardPacket extends Packet {

    /**
     * The username of the player who is placing the card
     */
    private String player;

    /**
     * The new score of the player who is placing the card
     */
    private int newScore;

    /**
     * The map that associates the players' usernames with their resources
     */
    private HashMap<String, Integer> resources;

    /**
     * The ID of the card that has been placed
     */
    private final int cardId;

    /**
     * The x coordinate of the card that has been placed
     */
    private final int xCoord;

    /**
     * The y coordinate of the card that has been placed
     */
    private final int yCoord;

    /**
     * The face of the card that has been placed
     */
    private String cardFace;

    /**
     * Constructor of the class
     * @param player the username of the player who is placing the card
     * @param newScore the new score of the player who is placing the card
     * @param resources the map that associates the players' usernames with their resources
     * @param cardId the ID of the card that has been placed
     * @param xCoord the x coordinate of the card that has been placed
     * @param yCoord the y coordinate of the card that has been placed
     * @param cardFace the face of the card that has been placed
     */
    public PlaceCardPacket(String player, int newScore, HashMap<String, Integer> resources, int cardId, int xCoord, int yCoord, String cardFace) {
        this.player = player;
        this.newScore = newScore;
        this.resources = resources;
        this.cardId = cardId;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.cardFace = cardFace;
    }

    /**
     * Constructor of the class
     * @param cardId the ID of the card that has been placed
     * @param xCoord the x coordinate of the card that has been placed
     * @param yCoord the y coordinate of the card that has been placed
     */
    public PlaceCardPacket(int cardId, int xCoord, int yCoord) {
        this.cardId = cardId;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    /**
     * The method returns the username of the player who is placing the card
     * @return the username of the player who is placing the card
     */
    public String getPlayer() {
        return player;
    }

    /**
     * The method returns the ID of the card that has been placed
     * @return the ID of the card that has been placed
     */
    public int getCardId() {
        return cardId;
    }

    /**
     * The method returns the x coordinate of the card that has been placed
     * @return the x coordinate of the card that has been placed
     */
    public int getXCoord() {
        return xCoord;
    }

    /**
     * The method returns the y coordinate of the card that has been placed
     * @return the y coordinate of the card that has been placed
     */
    public int getYCoord() {
        return yCoord;
    }

    /**
     * The method returns the new score of the player who is placing the card
     * @return the new score of the player who is placing the card
     */
    public int getNewScore() {
        return newScore;
    }

    /**
     * The method returns the map that associates the players' usernames with their resources
     * @return the map that associates the players' usernames with their resources
     */
    public HashMap<String, Integer> getResources() {
        return resources;
    }

    /**
     * The method returns the face of the card that has been placed
     * @return the face of the card that has been placed
     */
    public String getCardFace() {
        return cardFace;
    }

    /**
     * The method returns the client-side card placing packets handler
     * @return the client-side card placing packets handler
     */
    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientPlaceCardPacketHandler();
    }

    /**
     * The method returns the server-side card placing packets handler
     * @return the server-side card placing packets handler
     */
    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return new ServerPlaceCardPacketHandler();
    }
}
