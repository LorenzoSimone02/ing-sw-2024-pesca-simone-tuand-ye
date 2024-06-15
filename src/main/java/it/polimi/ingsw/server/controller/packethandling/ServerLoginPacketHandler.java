package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.packets.InfoPacket;
import it.polimi.ingsw.network.packets.LoginPacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.server.controller.GameController;

/**
 * The class that handles the client login packets from the clients
 */
public class ServerLoginPacketHandler extends ServerPacketHandler {

    /**
     * The method handles the client login packets from the client
     * @param packet the login packet
     * @param controller the game controller
     * @param connection the connection of the client
     */
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
