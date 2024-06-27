package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.client.view.ViewModeEnum;
import it.polimi.ingsw.client.view.gui.GUIClient;
import it.polimi.ingsw.client.view.gui.controllers.GameGuiController;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.network.packets.TurnCardPacket;
import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.FaceEnum;
import javafx.application.Platform;

/**
 * The class that handles the card face turning packets from the server
 */
public class ClientTurnCardPacketHandler extends ClientPacketHandler {

    /**
     * The method handles the card face turning packet
     *
     * @param packet        the card turning packet
     * @param clientManager the client manager
     */
    public void handlePacket(Packet packet, ClientManager clientManager) {
        TurnCardPacket turnCardPacket = (TurnCardPacket) packet;
        Card card = clientManager.getGameState().getCardById(turnCardPacket.getCardId());
        if (card.getFace() == FaceEnum.FRONT) {
            card.setFace(FaceEnum.BACK);
        } else {
            card.setFace(FaceEnum.FRONT);
        }
        if (clientManager.getViewMode() == ViewModeEnum.GUI) {
            Platform.runLater(() -> {
                GUIClient guiClient = (GUIClient) clientManager.getUserInterface();
                guiClient.updateCurrentScene("turnCard-" + card.getId());
            });
        } else {
            System.out.println(Printer.GREEN + "Card turned successfully." + Printer.RESET);
        }
    }
}
