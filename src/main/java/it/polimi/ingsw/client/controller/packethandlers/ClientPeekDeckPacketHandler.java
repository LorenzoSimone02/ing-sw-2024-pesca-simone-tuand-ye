package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.client.view.UserInterface;
import it.polimi.ingsw.client.view.ViewModeEnum;
import it.polimi.ingsw.client.view.gui.GUIClient;
import it.polimi.ingsw.client.view.gui.controllers.GameGuiController;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.network.packets.PeekDeckPacket;
import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.FaceEnum;
import javafx.application.Platform;

/**
 * The class that handles the client's deck peeking packets from the server
 */
public class ClientPeekDeckPacketHandler extends ClientPacketHandler {

    /**
     * The method handles the client's deck peeking packet
     *
     * @param packet        the deck peeking packet
     * @param clientManager the client manager
     */
    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {

        PeekDeckPacket peekDeckPacket = (PeekDeckPacket) packet;
        int topCardID = peekDeckPacket.getCardId();

        if (topCardID > 0) {
            Card topCard = clientManager.getGameState().getCardById(topCardID);
            topCard.setFace(FaceEnum.BACK);
            if (peekDeckPacket.isGold()) {
                clientManager.getGameState().setTopGoldDeckCard(topCard);
            } else {
                clientManager.getGameState().setTopResourcesDeckCard(topCard);
            }
            if (clientManager.getViewMode() == ViewModeEnum.TUI) {
                Printer.printCard(topCard);
            } else {
                Platform.runLater(() -> {
                    UserInterface userInterface = clientManager.getUserInterface();
                    GameGuiController gameGuiController = (GameGuiController) ((GUIClient) userInterface).getControllersMap().get(clientManager.getGameState().getClientStatus());
                    gameGuiController.updateTopDeckCards();
                });
            }
            topCard.setFace(FaceEnum.FRONT);
        } else {
            System.err.println(Printer.GREEN + "Invalid card." + Printer.RESET);
        }
    }
}
