package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientDrawCardPacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerDrawCardPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;

/**
 * DrawCardPacket is a Packet that contains the information about the drawing of a card
 */
public class DrawCardPacket extends Packet {

    /**
     * The ID of the card that has been drawn
     */
    private final int cardID;

    /**
     * The ID of the new card that has been drawn
     */
    private int newCardID;

    /**
     * A boolean that is true if the card is a gold card
     */
    private boolean gold;

    /**
     * Constructor of the class
     * @param cardID the ID of the card that has been drawn
     */
    public DrawCardPacket(int cardID) {
        this.cardID = cardID;
    }

    /**
     * Constructor of the class
     * @param cardID the ID of the card that has been drawn
     * @param newCardID the ID of the new card that has been drawn
     */
    public DrawCardPacket(int cardID, int newCardID) {
        this.cardID = cardID;
        this.newCardID = newCardID;
    }

    /**
     * Constructor of the class
     * @param gold a boolean that is true if the card is a gold card
     */
    public DrawCardPacket(boolean gold) {
        cardID = -1;
        newCardID = -1;
        this.gold = gold;
    }

    /**
     * The method returns the ID of the card that has been drawn
     * @return the ID of the card that has been drawn
     */
    public int getCardID() {
        return cardID;
    }

    /**
     * The method returns the ID of the new card that has been drawn
     * @return the ID of the new card that has been drawn
     */
    public int getNewCardID() {
        return newCardID;
    }

    /**
     * The method returns a boolean that is true if the card is a gold card
     * @return a boolean that is true if the card is a gold card
     */
    public boolean isGold() {
        return gold;
    }

    /**
     * The method returns the client-side card drawing packets handler
     * @return the client-side card drawing packets handler
     */
    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientDrawCardPacketHandler();
    }

    /**
     * The method returns the server-side card drawing packets handler
     * @return the server-side card drawing packets handler
     */
    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return new ServerDrawCardPacketHandler();
    }
}
