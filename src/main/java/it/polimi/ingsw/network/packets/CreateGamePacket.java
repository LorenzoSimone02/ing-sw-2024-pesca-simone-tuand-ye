package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientCreateGamePacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerCreateGamePacketHandler;

public class CreateGamePacket extends Packet{

    private final int playersNumber;

    public CreateGamePacket(int playersNumber) {
        this.playersNumber = playersNumber;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientCreateGamePacketHandler();
    }

    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return new ServerCreateGamePacketHandler();
    }
}
