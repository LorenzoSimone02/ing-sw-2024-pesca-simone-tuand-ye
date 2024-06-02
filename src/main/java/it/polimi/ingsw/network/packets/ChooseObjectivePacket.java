package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientChooseObjectivePacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerChooseObjectivePacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;

/**
 * ChooseObjectivePacket is a Packet that contains the objective card chosen by a player
 */
public class ChooseObjectivePacket extends Packet {

    /**
     * The ID of the first objective card
     */
    private int cardID1;

    /**
     * The ID of the second objective card
     */
    private int cardID2;

    /**
     * The ID of the chosen objective card
     */
    private final int chosenCardID;

    /**
     * Constructor of the class
     * @param cardID1 the ID of the first objective card
     * @param cardID2 the ID of the second objective card
     */
    public ChooseObjectivePacket(int cardID1, int cardID2) {
        this.cardID1 = cardID1;
        this.cardID2 = cardID2;
        this.chosenCardID = -1;
    }

    /**
     * Constructor of the class
     * @param choosenCardID the ID of the chosen objective card
     */
    public ChooseObjectivePacket(int choosenCardID) {
        this.chosenCardID = choosenCardID;
    }

    /**
     * The method returns the ID of the first objective card
     * @return the ID of the first objective card
     */
    public int getCardID1() {
        return cardID1;
    }

    /**
     * The method returns the ID of the second objective card
     * @return the ID of the second objective card
     */
    public int getCardID2() {
        return cardID2;
    }

    /**
     * The method returns the ID of the chosen objective card
     * @return the ID of the chosen objective card
     */
    public int getChosenCardID() {
        return chosenCardID;
    }

    /**
     * The method returns the client-side objective choosing packets handler
     * @return the client-side objective choosing packets handler
     */
    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientChooseObjectivePacketHandler();
    }

    /**
     * The method returns the server-side objective choosing packets handler
     * @return the server-side objective choosing packets handler
     */
    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return new ServerChooseObjectivePacketHandler();
    }
}
