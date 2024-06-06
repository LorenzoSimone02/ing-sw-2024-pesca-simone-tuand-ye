package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.packets.InfoPacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.network.packets.PlaceCardPacket;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.PlayerController;
import it.polimi.ingsw.server.controller.exceptions.IllegalCardPlacementException;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.game.GameStatusEnum;
import it.polimi.ingsw.server.model.resources.Object;
import it.polimi.ingsw.server.model.resources.Resource;

import java.util.HashMap;

public class ServerPlaceCardPacketHandler extends ServerPacketHandler {

    @Override
    public void handlePacket(Packet packet, GameController controller, ClientConnection clientConnection) {
        PlaceCardPacket placeCardPacket = (PlaceCardPacket) packet;
        if (!controller.getGame().getInfo().getActivePlayer().getUsername().equals(clientConnection.getUsername()) || controller.getPlayerByNick(clientConnection.getUsername()).orElseThrow().getCardsInHand().size() != 3) {
            controller.getNetworkHandler().sendPacket(clientConnection, new InfoPacket(Printer.RED + "You can't place a Card now." + Printer.RESET));
            return;
        }
        if(controller.getGame().getInfo().getGameStatus() == GameStatusEnum.WAITING_FOR_PLAYERS && controller.getGame().getInfo().getPlayersNumber() == 1){
            controller.getNetworkHandler().sendPacket(clientConnection, new InfoPacket(Printer.RED + "You are the only Player connected, wait for someone else to connect." + Printer.RESET));
            return;
        }
        try {
            ResourceCard card = (ResourceCard) controller.getCardById(placeCardPacket.getCardId());
            PlayerController playerController = controller.getPlayerController(clientConnection.getUsername());
            if (playerController.getPlayer().getCardsInHand().contains(card)) {
                playerController.placeCard(card, placeCardPacket.getXCoord(), placeCardPacket.getYCoord());
                playerController.getPlayer().removeCardInHand(card);
                HashMap<String, Integer> resources = new HashMap<>();
                for (Resource resource : playerController.getPlayer().getResources()) {
                    resources.put(resource.getType().name(), resources.getOrDefault(resource.getType().name(), 0) + 1);
                }
                for (Object object : playerController.getPlayer().getObjects()) {
                    resources.put(object.getType().name(), resources.getOrDefault(object.getType().name(), 0) + 1);
                }
                controller.getNetworkHandler().sendPacketToAll(new PlaceCardPacket(clientConnection.getUsername(), playerController.getPlayer().getScore(), resources, placeCardPacket.getCardId(), placeCardPacket.getXCoord(), placeCardPacket.getYCoord(), card.getFace().toString()));
            } else {
                controller.getNetworkHandler().sendPacket(clientConnection, new InfoPacket(Printer.RED + "You don't have that Card." + Printer.RESET));
            }
        } catch (IllegalCardPlacementException ex) {
            controller.getNetworkHandler().sendPacket(clientConnection, new InfoPacket(Printer.RED + "You can't place that Card here." + Printer.RESET));
        }
    }
}
