package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.packets.InfoPacket;
import it.polimi.ingsw.network.packets.LoginRequestPacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.exceptions.DuplicatePlayerException;
import it.polimi.ingsw.server.model.exceptions.FullLobbyException;
import it.polimi.ingsw.server.model.exceptions.IllegalOperationForStateException;
import it.polimi.ingsw.server.model.player.Player;

public class ServerLoginRequestPacketHandler extends ServerPacketHandler {

    @Override
    public void handlePacket(Packet packet, GameController controller, ClientConnection connection) {
        LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
        try {
            if (controller.getGame() == null)
                controller.createGame(1);

            Player newPlayer = controller.addPlayer(loginRequestPacket.getUsername());
            connection.setNickname(loginRequestPacket.getUsername());

            System.out.println("Player " + loginRequestPacket.getUsername() + " has joined the game.");

            String welcomeString = "You have joined the game";

            if (controller.getGame().getInfo().getPlayersNumber() == 1) {
                controller.getGame().getInfo().setAdmin(newPlayer);
                welcomeString += "\nYou are the first Player so you have been granted admin privilges" +
                        "\nSelect the number of players with the command /playersNumber <number>";
            }
            controller.getNetworkHandler().sendPacket(connection, new InfoPacket(welcomeString));
        } catch (DuplicatePlayerException e) {
            System.err.println("Recieved a Login request with an already existing username.");
            controller.getNetworkHandler().sendPacket(connection, new InfoPacket("The username you are trying to use is already taken."));
        } catch (FullLobbyException e) {
            System.err.println("Recieved a Login request with a full lobby.");
            controller.getNetworkHandler().sendPacket(connection, new InfoPacket("The lobby you are trying to join is full."));
        } catch (IllegalOperationForStateException e) {
            System.err.println("Recieved a Login request while the game is already started.");
            controller.getNetworkHandler().sendPacket(connection, new InfoPacket("The game you are trying  to connect is already started"));
        }
    }
}
