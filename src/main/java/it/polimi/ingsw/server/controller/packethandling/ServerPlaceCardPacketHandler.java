package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.packets.InfoPacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.network.packets.PlaceCardPacket;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.exceptions.IllegalCardPlacementException;
import it.polimi.ingsw.server.model.card.ResourceCard;

public class ServerPlaceCardPacketHandler extends ServerPacketHandler {

    @Override
    public void handlePacket(Packet packet, GameController controller, ClientConnection clientConnection) {
        PlaceCardPacket placeCardPacket = (PlaceCardPacket) packet;
        try {
            ResourceCard card = (ResourceCard) controller.getCardById(placeCardPacket.getCardId());
            controller.getPlayerController(clientConnection.getUsername()).placeCard(card, placeCardPacket.getXCoord(), placeCardPacket.getYCoord());
        } catch (IllegalCardPlacementException ex) {
            controller.getNetworkHandler().sendPacket(clientConnection, new InfoPacket(Printer.RED + "You can't place that Card here." + Printer.RESET));
        }
    }
}
