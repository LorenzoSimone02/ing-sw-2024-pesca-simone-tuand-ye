package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.packets.ChatPacket;
import it.polimi.ingsw.network.packets.InfoPacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.server.controller.GameController;

public class ServerChatPacketHandler extends ServerPacketHandler {

    @Override
    public void handlePacket(Packet packet, GameController controller, ClientConnection clientConnection) {
        ChatPacket chatPacket = (ChatPacket) packet;
        if (chatPacket.getRecipient() != null) {
            ClientConnection recipientConnection = controller.getNetworkHandler().getConnectionByNickname(chatPacket.getRecipient());
            if (recipientConnection != null) {
                controller.getNetworkHandler().sendPacket(clientConnection, chatPacket);
                controller.getNetworkHandler().sendPacket(recipientConnection, chatPacket);
            } else {
                controller.getNetworkHandler().sendPacket(clientConnection, new InfoPacket("User not found"));
            }
        } else {
            controller.getNetworkHandler().sendPacketToAll(chatPacket);
        }
    }
}
