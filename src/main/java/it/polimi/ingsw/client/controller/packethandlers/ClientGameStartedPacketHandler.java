package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.client.controller.clientstate.PlayerState;
import it.polimi.ingsw.network.packets.GameStartedPacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;

public class ClientGameStartedPacketHandler extends ClientPacketHandler {

    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        GameStartedPacket gameStartedPacket = (GameStartedPacket) packet;

        clientManager.getGameState().setGameID(gameStartedPacket.getGameID());
        clientManager.getGameState().setActivePlayer(gameStartedPacket.getFirstPlayer());
        clientManager.getGameState().setFirstPlayer(gameStartedPacket.getFirstPlayer());

        for (Integer cardID : gameStartedPacket.getCardsOnGround()) {
            clientManager.getGameState().addCardOnGround(clientManager.getGameState().getCardById(cardID));
        }

        for (String player : gameStartedPacket.getPlayers()) {
            if (!player.equals(clientManager.getGameState().getUsername())) {
                PlayerState playerState = new PlayerState(player);
                clientManager.getGameState().addPlayerState(playerState);
            } else {
                for (Integer cardID : gameStartedPacket.getStarterCards().keySet()) {
                    if (gameStartedPacket.getStarterCards().get(cardID).equals(player)) {
                        clientManager.getGameState().setStarterCard((StarterCard) clientManager.getGameState().getCardById(cardID));
                    }
                }
                for (Integer cardID : gameStartedPacket.getCardsInHands().keySet()) {
                    if (gameStartedPacket.getCardsInHands().get(cardID).equals(player)) {
                        clientManager.getGameState().addCardInHand((ResourceCard) clientManager.getGameState().getCardById(cardID));
                    }
                }
            }
        }

        clientManager.getGameState().setClientStatus(ClientStatusEnum.CHOOSING_COLOR);
        System.out.println(Printer.CYAN + "The game has started.\n" +
                "Before playing, choose your Token color with the command /chooseColor <color>" + Printer.RESET);
    }
}
