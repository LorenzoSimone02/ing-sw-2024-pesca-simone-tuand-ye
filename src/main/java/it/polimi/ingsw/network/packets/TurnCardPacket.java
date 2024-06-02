package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientTurnCardPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerTurnCardPacketHandler;

/**
 * TurnCardPacket is a Packet that contains the information about the turning of a card
 */
public class TurnCardPacket extends Packet {

    /**
     * The ID of the card that has been turned
     */
    private final int cardId;

    /**
     * Constructor of the class
     * @param cardId the ID of the card that has been turned
     */
    public TurnCardPacket(int cardId) {
        this.cardId = cardId;
    }

    /**
     * The method returns the ID of the card that has been turned
     * @return the ID of the card that has been turned
     */
    public int getCardId() {
        return cardId;
    }

    /**
     * The method returns the client-side card turning packets handler
     * @return the client-side card turning packets handler
     */
    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientTurnCardPacketHandler();
    }

    /**
     * The method returns the server-side card turning packets handler
     * @return the server-side card turning packets handler
     */
    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return new ServerTurnCardPacketHandler();
    }
}
