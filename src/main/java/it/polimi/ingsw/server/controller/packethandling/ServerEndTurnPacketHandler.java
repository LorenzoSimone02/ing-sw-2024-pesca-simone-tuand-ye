package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.packets.EndTurnPacket;
import it.polimi.ingsw.network.packets.InfoPacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.server.controller.GameController;

public class ServerEndTurnPacketHandler extends ServerPacketHandler {

    @Override
    public void handlePacket(Packet packet, GameController controller, ClientConnection clientConnection) {
        EndTurnPacket endTurnPacket = (EndTurnPacket) packet;
        if (controller.getGame().getInfo().getActivePlayer().getUsername().equals(endTurnPacket.getActivePlayer())) {
            controller.nextTurn();
        } else {
            controller.getNetworkHandler().sendPacket(clientConnection, new InfoPacket("It's not your turn!"));
        }
    }
}
