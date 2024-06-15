package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.client.controller.clientstate.PlayerState;
import it.polimi.ingsw.network.packets.GameStartedPacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.server.model.card.ResourceCard;

/**
 * The class that handles the starting game packets from the server
 */
public class ClientGameStartedPacketHandler extends ClientPacketHandler {

    /**
     * The method handles the starting game packet
     * @param packet the game starting packet
     * @param clientManager the client manager
     */
    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        GameStartedPacket gameStartedPacket = (GameStartedPacket) packet;

        clientManager.getGameState().setActivePlayer(gameStartedPacket.getFirstPlayer());
        clientManager.getGameState().setFirstPlayer(gameStartedPacket.getFirstPlayer());
        clientManager.getGameState().getPlayerStates().clear();

        for (Integer cardID : gameStartedPacket.getCardsOnGround()) {
            clientManager.getGameState().addCardOnGround(clientManager.getGameState().getCardById(cardID));
        }

        for (String player : gameStartedPacket.getPlayers()) {
            if (player.equals(clientManager.getGameState().getUsername())) {
                for (Integer cardID : gameStartedPacket.getCardsInHands().keySet()) {
                    if (gameStartedPacket.getCardsInHands().get(cardID).equals(player)) {
                        clientManager.getGameState().addCardInHand((ResourceCard) clientManager.getGameState().getCardById(cardID));
                    }
                }
            } else {
                clientManager.getGameState().addPlayerState(new PlayerState(player));
            }
        }

        if (!gameStartedPacket.isReconnection()) {
            clientManager.getGameState().setClientStatus(ClientStatusEnum.CHOOSING_COLOR);
            System.out.println(Printer.CYAN + "Before playing, choose your Token color with the command /chooseColor <color>" + Printer.RESET);
        }
    }
}
