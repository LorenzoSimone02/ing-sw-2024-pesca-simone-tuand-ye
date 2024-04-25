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
        if (controller.getNetworkHandler().isLobby()) {
            controller.getNetworkHandler().sendPacket(connection, new InfoPacket("You can't login in the Lobby."));
            return;
        }
        try {
            connection.setUsername(loginRequestPacket.getUsername());
            Player newPlayer = controller.addPlayer(loginRequestPacket.getUsername());

            System.out.println(Printer.ANSI_YELLOW + "Player " + loginRequestPacket.getUsername() + " has joined the game " + controller.getGame().getInfo().getId() + Printer.ANSI_RESET);
            controller.getNetworkHandler().sendPacket(connection, new LoginPacket(newPlayer.getUsername()));

            controller.checkStartCondition();
        } catch (DuplicatePlayerException e) {
            System.err.println("Recieved a Login request with an already existing username.");
            connection.setUsername("Unknown");
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
