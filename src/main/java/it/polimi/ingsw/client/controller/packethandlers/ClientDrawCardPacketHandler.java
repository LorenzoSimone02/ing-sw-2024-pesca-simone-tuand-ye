package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.client.view.UserInterface;
import it.polimi.ingsw.client.view.ViewModeEnum;
import it.polimi.ingsw.client.view.gui.GUIClient;
import it.polimi.ingsw.client.view.gui.controllers.GameGuiController;
import it.polimi.ingsw.network.packets.DrawCardPacket;
import it.polimi.ingsw.network.packets.EndTurnPacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.ResourceCard;
import javafx.application.Platform;

/**
 * The class that handles the card drawing packets from the server
 */
public class ClientDrawCardPacketHandler extends ClientPacketHandler {

    /**
     * The method handles the card drawing packet
     *
     * @param packet        the draw card packet
     * @param clientManager the client manager
     */
    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        DrawCardPacket drawCardPacket = (DrawCardPacket) packet;
        ResourceCard card = (ResourceCard) clientManager.getGameState().getCardById(drawCardPacket.getCardID());

        if (drawCardPacket.getNewCardID() > 0) {
            Card newCard = clientManager.getGameState().getCardById(drawCardPacket.getNewCardID());
            clientManager.getGameState().removeCardOnGround(card);
            clientManager.getGameState().addCardOnGround(newCard);
            if (clientManager.getViewMode() == ViewModeEnum.GUI) {
                Platform.runLater(() -> {
                    GUIClient guiClient = (GUIClient) clientManager.getUserInterface();
                    guiClient.updateCurrentScene("removeCardFromGround-" + card.getId());
                    guiClient.updateCurrentScene("addCardOnGround-" + newCard.getId());
                });
            }
        }

        if (clientManager.getGameState().getActivePlayer().equals(clientManager.getGameState().getUsername())) {
            clientManager.getGameState().addCardInHand(card);

            UserInterface userInterface = clientManager.getUserInterface();
            if (clientManager.getViewMode() == ViewModeEnum.GUI) {
                Platform.runLater(() -> {
                    GUIClient guiClient = (GUIClient) userInterface;
                    guiClient.updateCurrentScene("addCardToHand-" + card.getId());
                });
            }
            userInterface.showMessage(Printer.GREEN + "Card drawn successfully, your turn has ended." + Printer.RESET);

            clientManager.getNetworkHandler().sendPacket(new EndTurnPacket(clientManager.getGameState().getUsername()));
        }
    }
}
