package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.packets.InfoPacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.network.packets.PlayersNumberPacket;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.exceptions.IllegalOperationForStateException;

public class ServerPlayersNumbersPacketHandler extends ServerPacketHandler {

    @Override
    public void handlePacket(Packet packet, GameController controller, ClientConnection connection) {
        PlayersNumberPacket infoPacket = (PlayersNumberPacket) packet;
        int playersNumber = infoPacket.getPlayersNumber();
        if (!packet.getSender().equals(controller.getGame().getInfo().getAdmin().getNickname())) {
            controller.getNetworkHandler().sendPacket(connection, new InfoPacket("You don't have the permission to set the players number."));
            return;
        }
        try {
            controller.setMaxPlayers(playersNumber);
            System.out.println("The administrator set the players number to " + playersNumber + ".");
            controller.getNetworkHandler().sendPacket(connection, new InfoPacket("Players number successfully set to " + playersNumber + "."));
        } catch (IllegalOperationForStateException e) {
            System.err.println("The game is not in the right state to set the players number.");
            controller.getNetworkHandler().sendPacket(connection, new InfoPacket("The game is not in the right state to set the players number."));
        } catch (IllegalArgumentException e) {
            controller.getNetworkHandler().sendPacket(connection, new InfoPacket("Players number must be between 2 and 4."));
        }
    }
}
