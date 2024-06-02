package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientInfoPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerInfoPacketHandler;

/**
 * InfoPacket is a Packet that contains information during the game
 */
public class InfoPacket extends Packet {

    /**
     * The data contained in the packet
     */
    private final String data;

    /**
     * Constructor of the class
     * @param data the data contained in the packet
     */
    public InfoPacket(String data) {
        this.data = data;
    }

    /**
     * The method returns the data contained in the packet
     * @return the data contained in the packet
     */
    public String getData() {
        return data;
    }

    /**
     * The method returns the client-side info packets handler
     * @return the client-side info packets handler
     */
    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientInfoPacketHandler();
    }

    /**
     * The method returns the server-side info packets handler
     * @return the server-side info packets handler
     */
    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return new ServerInfoPacketHandler();
    }
}
