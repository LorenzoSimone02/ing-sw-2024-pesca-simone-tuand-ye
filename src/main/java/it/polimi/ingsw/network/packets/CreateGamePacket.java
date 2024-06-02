package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientCreateGamePacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerCreateGamePacketHandler;

/**
 * ConnectionEventPacket is a Packet that contains the information about the creation of a new game
 */
public class CreateGamePacket extends Packet{

    /**
     * The number of players that will play the game
     */
    private final int playersNumber;

    /**
     * Constructor of the class
     * @param playersNumber the number of players that will play the game
     */
    public CreateGamePacket(int playersNumber) {
        this.playersNumber = playersNumber;
    }

    /**
     * The method returns the number of players that will play the game
     * @return the number of players that will play the game
     */
    public int getPlayersNumber() {
        return playersNumber;
    }

    /**
     * The method returns the client-side game creation packets handler
     * @return the client-side game creation packets handler
     */
    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientCreateGamePacketHandler();
    }

    /**
     * The method returns the server-side game creation packets handler
     * @return the server-side game creation packets handler
     */
    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return new ServerCreateGamePacketHandler();
    }
}
