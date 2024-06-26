package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPeekDeckPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;

/**
 * PeekDeckPacket is a Packet that contains the ID of the top deck card
 */
public class PeekDeckPacket extends Packet {

    /**
     * The ID of the top deck card
     */
    private final int topDeckCardId;

    /**
     * A boolean value that indicates if the deck is the gold deck
     */
    private final boolean gold;

    /**
     * The constructor of the class
     * @param topDeckCardId the ID of the top deck card
     * @param gold true if the deck is the gold deck, false otherwise
     */
    public PeekDeckPacket(int topDeckCardId, boolean gold){
        this.topDeckCardId = topDeckCardId;
        this.gold = gold;
    }

    /**
     * The method returns the ID of the top deck card
     * @return the ID of the top deck card
     */
    public int getTopDeckCardId(){
        return topDeckCardId;
    }

    /**
     * The method returns a boolean value that indicates if the deck is the gold deck
     * @return true if the deck is the gold deck, false otherwise
     */
    public boolean isGold(){
        return gold;
    }

    /**
     * The method returns the client-side deck peeking packets handler
     */
    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientPeekDeckPacketHandler();
    }

    /**
     * The method returns the server-side deck peeking packets handler
     */
    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return null;
    }
}
