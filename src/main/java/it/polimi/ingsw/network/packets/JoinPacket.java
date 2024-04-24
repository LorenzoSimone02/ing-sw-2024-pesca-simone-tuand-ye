package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientJoinPacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerJoinPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;

public class JoinPacket extends Packet {

    int gameID;

    public JoinPacket() {
        this.gameID = 0;
    }

    public JoinPacket(int gameID) {
        this.gameID = gameID;
    }

    public int getGameID() {
        return gameID;
    }

    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientJoinPacketHandler();
    }

    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return new ServerJoinPacketHandler();
    }
}
