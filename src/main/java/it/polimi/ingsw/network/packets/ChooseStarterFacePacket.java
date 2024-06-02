package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientChooseStarterFacePacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerChooseStarterFacePacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;
import it.polimi.ingsw.server.model.card.FaceEnum;

/**
 * ChooseStarterFacePacket is a Packet that contains the starter card face chosen by a player
 */
public class ChooseStarterFacePacket extends Packet{

    /**
     * The starter card face chosen by the player
     */
    private final FaceEnum chosenStarterFace;

    /**
     * The ID of the starter card
     */
    private final int starterID;

    /**
     * The username of the player who chose the starter card face
     */
    private final String username;

    /**
     * Constructor of the class
     * @param chosenStarterFace the starter card face chosen by the player
     * @param starterID the ID of the starter card
     * @param username the username of the player who chose the starter card face
     */
    public ChooseStarterFacePacket(FaceEnum chosenStarterFace, int starterID, String username) {
        this.chosenStarterFace = chosenStarterFace;
        this.starterID = starterID;
        this.username = username;
    }

    /**
     * Constructor of the class
     * @param starterID the ID of the starter card
     * @param username the username of the player who chose the starter card face
     */
    public ChooseStarterFacePacket(int starterID, String username) {
        this.starterID = starterID;
        this.chosenStarterFace = null;
        this.username = username;
    }

    /**
     * The method returns the client-side starter card face choosing packets handler
     * @return the client-side starter card face choosing packets handler
     */
    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientChooseStarterFacePacketHandler();
    }

    /**
     * The method returns the server-side starter card face choosing packets handler
     * @return the server-side starter card face choosing packets handler
     */
    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return new ServerChooseStarterFacePacketHandler();
    }

    /**
     * The method returns the starter card face chosen by the player
     * @return the starter card face chosen by the player
     */
    public FaceEnum getChosenStarterFace() {
        return chosenStarterFace;
    }

    /**
     * The method returns the ID of the starter card
     * @return the ID of the starter card
     */
    public int getStarterID() {
        return starterID;
    }

    /**
     * The method returns the username of the player who chose the starter card face
     * @return the username of the player who chose the starter card face
     */
    public String getUsername() {
        return username;
    }
}
