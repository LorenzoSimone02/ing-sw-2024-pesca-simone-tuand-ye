package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.packets.InfoPacket;
import it.polimi.ingsw.network.packets.LoginRequestPacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.exceptions.DuplicatePlayerException;
import it.polimi.ingsw.server.model.exceptions.FullLobbyException;

public class ServerLoginRequestPacketHandler extends ServerPacketHandler {

    @Override
    public void handlePacket(Packet packet, GameController controller, ClientConnection clientConnection) {
        LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
        try {
            if (controller.getGame() == null)
                controller.createGame(1);

            controller.addPlayer(loginRequestPacket.getUsername());
            clientConnection.setNickname(loginRequestPacket.getUsername());

            System.out.println("Player " + loginRequestPacket.getUsername() + " has joined the game.");
            controller.getNetworkHandler().sendPacket(clientConnection, new InfoPacket("You have joined the game"));
        } catch (DuplicatePlayerException e) {
            System.err.println("Recieved a Login request with an already existing username.");
            controller.getNetworkHandler().sendPacket(clientConnection, new InfoPacket("The username you are trying to use is already taken."));
        } catch (FullLobbyException e) {
            System.err.println("Recieved a Login request with a full lobby.");
            controller.getNetworkHandler().sendPacket(clientConnection, new InfoPacket("The lobby you are trying to join is full."));
        }
    }
}
