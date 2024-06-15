package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.packets.InfoPacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.network.packets.TurnCardPacket;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.PlayerController;
import it.polimi.ingsw.server.model.card.Card;

/**
 * The class that handles the card face turning packets from the clients
 */
public class ServerTurnCardPacketHandler extends ServerPacketHandler {

    /**
     * The method handles the card face turning packets from the client
     * @param packet the turn card packet
     * @param controller the game controller
     * @param connection the connection of the client
     */
    @Override
    public void handlePacket(Packet packet, GameController controller, ClientConnection connection) {
        TurnCardPacket turnCardPacket = (TurnCardPacket) packet;
        Card card = controller.getCardById(turnCardPacket.getCardId());
        PlayerController playerController = controller.getPlayerController(connection.getUsername());
        if (!playerController.getPlayer().getCardsInHand().contains(card)) {
            controller.getNetworkHandler().sendPacket(connection, new InfoPacket(Printer.RED + "Invalid Card." + Printer.RESET));
            return;
        }
        playerController.turnCard(card);
        controller.getNetworkHandler().sendPacket(connection, new TurnCardPacket(card.getId()));
    }
}
