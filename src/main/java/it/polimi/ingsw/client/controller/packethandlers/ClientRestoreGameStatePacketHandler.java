package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.client.controller.clientstate.PlayerState;
import it.polimi.ingsw.client.view.ViewModeEnum;
import it.polimi.ingsw.client.view.gui.GUIClient;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.network.packets.RestoreGameStatePacket;
import it.polimi.ingsw.server.controller.save.CardSave;
import it.polimi.ingsw.server.controller.save.PlayerSave;
import it.polimi.ingsw.server.model.card.FaceEnum;
import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;

/**
 * The class that handles the game state restoring packets from the server
 */
public class ClientRestoreGameStatePacketHandler extends ClientPacketHandler {

    /**
     * The method handles the game state restoring packet
     *
     * @param packet        the game state restoring packet
     * @param clientManager the client manager
     */
    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        RestoreGameStatePacket restoreGameStatePacket = (RestoreGameStatePacket) packet;

        for (PlayerSave playerSave : restoreGameStatePacket.getPlayerSaves()) {
            if (playerSave.isFirstPlayer()) {
                clientManager.getGameState().setFirstPlayer(playerSave.getUsername());
            }
            if (playerSave.isActive()) {
                clientManager.getGameState().setActivePlayer(playerSave.getUsername());
            }
            if (playerSave.getUsername().equals(clientManager.getGameState().getUsername())) {
                clientManager.getGameState().setScore(playerSave.getScore());
                clientManager.getGameState().setTokenColor(playerSave.getTokenColor());
                StarterCard starterCard = (StarterCard) clientManager.getGameState().getCardById(playerSave.getStarterCard().getId());
                clientManager.getGameState().setStarterCard(starterCard);
                ObjectiveCard objectiveCard = (ObjectiveCard) clientManager.getGameState().getCardById(playerSave.getObjectiveCard().getId());
                clientManager.getGameState().setObjectiveCard(objectiveCard);
                for (CardSave cSave : playerSave.getOrderedCards()) {
                    clientManager.getGameState().getOrderedCardsPlaced().add((ResourceCard) clientManager.getGameState().getCardById(cSave.getId()));
                }
                for (int i = 0; i < 81; i++) {
                    for (int j = 0; j < 81; j++) {
                        CardSave cardSave = playerSave.getCards()[i][j];
                        if (cardSave != null) {
                            ResourceCard card = (ResourceCard) clientManager.getGameState().getCardById(cardSave.getId());
                            card.setFace(FaceEnum.valueOf(cardSave.getFace()));
                            clientManager.getGameState().setCardPlaced(card, i, j);
                        }
                    }
                }
                clientManager.getGameState().getResources().putAll(playerSave.getResourcesAndObjects());
                clientManager.getGameState().setClientStatus(ClientStatusEnum.PLAYING);
            } else {
                PlayerState playerState = clientManager.getGameState().getOrCreatePlayerStateByNick(playerSave.getUsername());
                playerState.setScore(playerSave.getScore());
                playerState.setTokenColor(playerSave.getTokenColor());
                StarterCard starterCard = (StarterCard) clientManager.getGameState().getCardById(playerSave.getStarterCard().getId());
                playerState.setStarterCard(starterCard);
                for (CardSave cSave : playerSave.getOrderedCards()) {
                    playerState.getOrderedCardsPlaced().add((ResourceCard) clientManager.getGameState().getCardById(cSave.getId()));
                }
                for (int i = 0; i < 81; i++) {
                    for (int j = 0; j < 81; j++) {
                        CardSave cardSave = playerSave.getCards()[i][j];
                        if (cardSave != null) {
                            ResourceCard card = (ResourceCard) clientManager.getGameState().getCardById(playerSave.getCards()[i][j].getId());
                            card.setFace(FaceEnum.valueOf(cardSave.getFace()));
                            playerState.setCardPlaced(card, i, j);
                        }
                    }
                }
            }
        }
        if (clientManager.getViewMode() == ViewModeEnum.GUI) {
            GUIClient guiClient = (GUIClient) clientManager.getUserInterface();
            guiClient.changeScene(clientManager.getGameState().getClientStatus());
        }
    }
}
