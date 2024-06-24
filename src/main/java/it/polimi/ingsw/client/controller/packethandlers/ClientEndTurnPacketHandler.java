package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.client.view.ViewModeEnum;
import it.polimi.ingsw.client.view.gui.GUIClient;
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
        if (clientManager.getGameState().getActivePlayer() == null && ClientManager.getInstance().getViewMode() == ViewModeEnum.GUI) {
            ((GUIClient) clientManager.getUserInterface()).changeScene(ClientManager.getInstance().getGameState().getClientStatus());
        }
        clientManager.getGameState().setActivePlayer(endTurnPacket.getActivePlayer());
        clientManager.getUserInterface().showMessage(Printer.CYAN + "It's now " + endTurnPacket.getActivePlayer() + "'s turn!" + Printer.RESET);
    }
}
