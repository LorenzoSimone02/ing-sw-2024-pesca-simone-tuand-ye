package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.packets.InfoPacket;
import it.polimi.ingsw.network.packets.LoginPacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.server.controller.GameController;

public class ServerLoginPacketHandler extends ServerPacketHandler {

    @Override
    public void handlePacket(Packet packet, GameController controller, ClientConnection connection) {
        LoginPacket loginPacket = (LoginPacket) packet;
        if (!controller.getNetworkHandler().isLobby()) {
            controller.getNetworkHandler().sendPacket(connection, new InfoPacket("You can't login outside of the Lobby."));
            return;
        }
        connection.setUsername(loginPacket.getUsername());
        controller.getNetworkHandler().sendPacket(connection, new LoginPacket(connection.getUsername()));
    }
}
