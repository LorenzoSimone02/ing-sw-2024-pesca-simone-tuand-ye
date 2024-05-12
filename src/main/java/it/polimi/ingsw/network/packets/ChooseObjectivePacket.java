package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientChooseObjectivePacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerChooseObjectivePacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;

public class ChooseObjectivePacket extends Packet {

    public ChooseObjectivePacket() {

    }

    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientChooseObjectivePacketHandler();
    }

    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return new ServerChooseObjectivePacketHandler();
    }
}
