package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.network.packets.EndTurnPacket;
import it.polimi.ingsw.network.packets.Packet;

/**
 * The class that handles the ending turn packets from the server
 */
public class ClientEndTurnPacketHandler extends ClientPacketHandler {

    /**
     * The method handles the ending turn packet
     * @param packet the end turn packet
     * @param clientManager the client manager
     */
    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        EndTurnPacket endTurnPacket = (EndTurnPacket) packet;
        clientManager.getGameState().setActivePlayer(endTurnPacket.getActivePlayer());
        System.out.println(Printer.CYAN + "It's now " + endTurnPacket.getActivePlayer() + "'s turn!" + Printer.RESET);
    }
}
