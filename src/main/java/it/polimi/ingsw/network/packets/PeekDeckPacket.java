package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPeekDeckPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPeekDeckPacketHandler;

/**
 * PeekDeckPacket is a Packet that contains the information about the peeking of a card from the deck
 */
public class PeekDeckPacket extends Packet {

    /**
     * The ID of the card that has been peeked
     */
    int cardId;

    /**
     * A boolean that is true if the card is a gold card
     */
    boolean gold;

    /**
     * Constructor of the class
     * @param gold a boolean that is true if the card is a gold card
     */
    public PeekDeckPacket(boolean gold) {
        this.gold = gold;
    }

    /**
     * Constructor of the class
     * @param cardId the ID of the card that has been peeked
     */
    public PeekDeckPacket(int cardId) {
        this.cardId = cardId;
    }

    /**
     * The method returns the ID of the card that has been peeked
     * @return the ID of the card that has been peeked
     */
    public int getCardId() {
        return cardId;
    }

    /**
     * The method returns a boolean that is true if the card is a gold card
     * @return a boolean that is true if the card is a gold card
     */
    public boolean isGold() {
        return gold;
    }

    /**
     * The method returns the client-side deck peeking packets handler
     * @return the client-side deck peeking packets handler
     */
    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientPeekDeckPacketHandler();
    }

    /**
     * The method returns the server-side deck peeking packets handler
     * @return the server-side deck peeking packets handler
     */
    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return new ServerPeekDeckPacketHandler();
    }
}
