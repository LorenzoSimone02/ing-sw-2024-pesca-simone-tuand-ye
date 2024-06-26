package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.packets.DrawCardPacket;
import it.polimi.ingsw.network.packets.InfoPacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.network.packets.PeekDeckPacket;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.GoldCard;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.game.GameStatusEnum;

/**
 * The class that handles the card drawing packets from the clients
 */
public class ServerDrawCardPacketHandler extends ServerPacketHandler {

    /**
     * The method handles the card drawing packets from the client
     * @param packet the draw card packet
     * @param controller the game controller
     * @param clientConnection the connection of the client
     */
    @Override
    public void handlePacket(Packet packet, GameController controller, ClientConnection clientConnection) {
        DrawCardPacket drawCardPacket = (DrawCardPacket) packet;
        if (!controller.getGame().getInfo().getActivePlayer().getUsername().equals(clientConnection.getUsername()) || controller.getPlayerController(clientConnection.getUsername()).getPlayer().getCardsInHand().size() != 2) {
            controller.getNetworkHandler().sendPacket(clientConnection, new InfoPacket(Printer.RED + "You can't draw a Card now." + Printer.RESET));
            return;
        }
        if(controller.getGame().getInfo().getGameStatus() == GameStatusEnum.WAITING_FOR_PLAYERS && controller.getGame().getInfo().getPlayersNumber() == 1){
            controller.getNetworkHandler().sendPacket(clientConnection, new InfoPacket(Printer.RED + "You are the only Player connected, wait for someone else to connect." + Printer.RESET));
            return;
        }
        if (drawCardPacket.getCardID() > 0) {
            Card card = controller.getCardById(drawCardPacket.getCardID());
            Card newCard = null;
            if (controller.getGame().getTable().getCardsOnGround().contains(card)) {
                controller.getGame().getTable().removeCardOnGround(card);
                if (card instanceof GoldCard) {
                    newCard = controller.getGame().getTable().getGoldDeck().drawCard();
                    controller.getGame().getTable().getCardsOnGround().add(newCard);
                    controller.getNetworkHandler().sendPacketToAll(new PeekDeckPacket(controller.getGame().getTable().getGoldDeck().getCards().peek().getId(), true));
                } else if (card instanceof ResourceCard) {
                    newCard = controller.getGame().getTable().getResourceDeck().drawCard();
                    controller.getGame().getTable().getCardsOnGround().add(newCard);
                    controller.getNetworkHandler().sendPacketToAll(new PeekDeckPacket(controller.getGame().getTable().getResourceDeck().getCards().peek().getId(), false));
                }
                controller.getNetworkHandler().sendPacketToAll(new DrawCardPacket(card.getId(), newCard.getId()));
                controller.getPlayerController(clientConnection.getUsername()).getPlayer().addCardInHand(card);
            } else {
                controller.getNetworkHandler().sendPacket(clientConnection, new InfoPacket(Printer.RED + "Invalid Card." + Printer.RESET));
            }
            return;
        }
        Card card;
        if (drawCardPacket.isGold()) {
            if(controller.getGame().getTable().getGoldDeck().getCards().isEmpty()) {
                controller.getNetworkHandler().sendPacket(clientConnection, new InfoPacket(Printer.RED + "That Deck is empty." + Printer.RESET));
                return;
            }
            card = controller.getGame().getTable().getGoldDeck().drawCard();
            controller.getNetworkHandler().sendPacketToAll(new PeekDeckPacket(controller.getGame().getTable().getGoldDeck().getCards().peek().getId(), true));
        } else {
            if(controller.getGame().getTable().getResourceDeck().getCards().isEmpty()) {
                controller.getNetworkHandler().sendPacketToAll(new InfoPacket(Printer.RED + "That Deck is empty." + Printer.RESET));
                return;
            }
            card = controller.getGame().getTable().getResourceDeck().drawCard();
            controller.getNetworkHandler().sendPacketToAll(new PeekDeckPacket(controller.getGame().getTable().getResourceDeck().getCards().peek().getId(), false));
        }
        controller.getNetworkHandler().sendPacket(clientConnection, new DrawCardPacket(card.getId()));
        controller.getPlayerController(clientConnection.getUsername()).getPlayer().addCardInHand(card);
    }
}
