package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.packets.ChatPacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.server.controller.GameController;

public class ServerChatPacketHandler extends ServerPacketHandler {

    @Override
    public void handlePacket(Packet packet, GameController controller, ClientConnection clientConnection) {
        ChatPacket chatPacket = (ChatPacket) packet;
        controller.getNetworkHandler().sendPacketToAll(chatPacket);
    }
}
