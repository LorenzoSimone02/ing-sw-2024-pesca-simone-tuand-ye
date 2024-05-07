package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.network.packets.EndTurnPacket;
import it.polimi.ingsw.network.packets.Packet;

public class ClientEndTurnPacketHandler extends ClientPacketHandler {

    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        EndTurnPacket endTurnPacket = (EndTurnPacket) packet;
        clientManager.getGameState().setActivePlayer(endTurnPacket.getActivePlayer());
        System.out.println(Printer.ANSI_CYAN + "It's now " + endTurnPacket.getActivePlayer() + "'s turn!" + Printer.ANSI_RESET);
    }
}
