package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.network.packets.PlaceCardPacket;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.exceptions.IllegalCardPlacementException;

public class ServerPlaceCardPacketHandler extends ServerPacketHandler {

    @Override
    public void handlePacket(Packet packet, GameController controller, ClientConnection clientConnection) {
        PlaceCardPacket placeCardPacket = (PlaceCardPacket) packet;
        try {
            //TODO Get card by id
            //controller.getPlayerController(clientConnection.getUsername()).placeCard(placeCardPacket.getCardId(), placeCardPacket.getXCoord(), placeCardPacket.getYCoord());
        } catch (IllegalCardPlacementException ex) {

        }
    }
}
