package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.packets.InfoPacket;
import it.polimi.ingsw.network.packets.LoginPacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.exceptions.DuplicatePlayerException;
import it.polimi.ingsw.server.controller.exceptions.FullLobbyException;
import it.polimi.ingsw.server.controller.exceptions.IllegalOperationForStateException;
import it.polimi.ingsw.server.model.player.Player;

public class ServerLoginPacketHandler extends ServerPacketHandler {

    @Override
    public void handlePacket(Packet packet, GameController controller, ClientConnection connection) {
        LoginPacket loginRequestPacket = (LoginPacket) packet;
        try {
            if (controller.getGame() == null)
                controller.createGame(1);

            Player newPlayer = controller.addPlayer(loginRequestPacket.getUsername());
            connection.setUsername(loginRequestPacket.getUsername());

            System.out.println(Printer.ANSI_YELLOW + "Player " + loginRequestPacket.getUsername() + " has joined the game." + Printer.ANSI_RESET);
            controller.getNetworkHandler().sendPacket(connection, new LoginPacket(newPlayer.getUsername()));

            if (controller.getGame().getInfo().getPlayersNumber() == 1) {
                controller.getGame().getInfo().setAdmin(newPlayer);
                controller.getNetworkHandler().sendPacket(connection, new InfoPacket("You are the first Player so you have been granted admin privilges" +
                        "\nSelect the number of players with the command /playersNumber <number>"));
            }
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
