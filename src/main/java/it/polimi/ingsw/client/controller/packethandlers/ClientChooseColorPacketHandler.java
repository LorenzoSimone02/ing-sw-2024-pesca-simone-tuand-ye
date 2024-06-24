package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.client.controller.clientstate.PlayerState;
import it.polimi.ingsw.client.view.ViewModeEnum;
import it.polimi.ingsw.client.view.gui.GUIClient;
import it.polimi.ingsw.network.packets.ChooseColorPacket;
import it.polimi.ingsw.network.packets.Packet;

/**
 * The class that handles the token color choosing packets from the server
 */
public class ClientChooseColorPacketHandler extends ClientPacketHandler {

    /**
     * The method handles the token color choosing packet
     *
     * @param packet        the choose color packet
     * @param clientManager the client manager
     */
    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        ChooseColorPacket chooseColorPacket = (ChooseColorPacket) packet;
        clientManager.getUserInterface().showMessage(Printer.GREEN + chooseColorPacket.getUsername() + " has chosen the color " + chooseColorPacket.getColor() + Printer.RESET);
        if (chooseColorPacket.getUsername().equals(clientManager.getGameState().getUsername())) {
            if (clientManager.getViewMode() == ViewModeEnum.GUI) {
                GUIClient userInterface = (GUIClient) clientManager.getUserInterface();
                userInterface.updateCurrentScene(null);
            }
            clientManager.getGameState().setTokenColor(chooseColorPacket.getColor());
        } else {
            for (PlayerState playerState : clientManager.getGameState().getPlayerStates()) {
                if (playerState.getUsername().equals(chooseColorPacket.getUsername())) {
                    playerState.setTokenColor(chooseColorPacket.getColor());
                }
            }
        }
    }
}
