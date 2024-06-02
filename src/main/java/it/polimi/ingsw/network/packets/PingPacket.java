package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPingPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPingPacketHandler;

/**
 * PingPacket is a Packet that contains a ping sent by the server to the client
 */
public class PingPacket extends Packet {

    /**
     * The method returns the client-side ping packets handler
     */
    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientPingPacketHandler();
    }

    /**
     * The method returns the server-side ping packets handler
     */
    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return new ServerPingPacketHandler();
    }
}
