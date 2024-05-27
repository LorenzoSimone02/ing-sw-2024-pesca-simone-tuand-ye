package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.packets.DrawCardPacket;
import it.polimi.ingsw.network.packets.InfoPacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.GoldCard;
import it.polimi.ingsw.server.model.card.ResourceCard;

public class ServerDrawCardPacketHandler extends ServerPacketHandler {

    @Override
    public void handlePacket(Packet packet, GameController controller, ClientConnection clientConnection) {
        DrawCardPacket drawCardPacket = (DrawCardPacket) packet;
        if (!controller.getGame().getInfo().getActivePlayer().getUsername().equals(clientConnection.getUsername()) || controller.getPlayerController(clientConnection.getUsername()).getPlayer().getCardsInHand().size() != 2) {
            controller.getNetworkHandler().sendPacket(clientConnection, new InfoPacket(Printer.RED + "You can't draw a Card now." + Printer.RESET));
            return;
        }
        if (drawCardPacket.getCardID() > 0) {
            Card card = controller.getCardById(drawCardPacket.getCardID());
            Card newCard = null;
            if (controller.getGame().getTable().getCardsOnGround().contains(card)) {
                controller.getGame().getTable().getCardsOnGround().remove(card);
                if (card instanceof GoldCard) {
                    newCard = controller.getGame().getTable().getGoldDeck().drawCard();
                    controller.getGame().getTable().getCardsOnGround().add(newCard);
                } else if (card instanceof ResourceCard) {
                    newCard = controller.getGame().getTable().getResourceDeck().drawCard();
                    controller.getGame().getTable().getCardsOnGround().add(newCard);
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
            card = controller.getGame().getTable().getGoldDeck().drawCard();
        } else {
            card = controller.getGame().getTable().getResourceDeck().drawCard();
        }
        controller.getNetworkHandler().sendPacket(clientConnection, new DrawCardPacket(card.getId()));
        controller.getPlayerController(clientConnection.getUsername()).getPlayer().addCardInHand(card);
    }
}
