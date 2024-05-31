package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.client.controller.clientstate.PlayerState;
import it.polimi.ingsw.network.packets.ConnectionEventPacket;
import it.polimi.ingsw.network.packets.Packet;

public class ClientConnectionEventPacketHandler extends ClientPacketHandler {

    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        ConnectionEventPacket connectionEventPacket = (ConnectionEventPacket) packet;
        String username = connectionEventPacket.getUsername();


        if (connectionEventPacket.isDisconnection()) {
            System.out.println(Printer.YELLOW + "Player " + username + " has disconnected from the Game." + Printer.RESET);
            clientManager.getGameState().removePlayerStateByNick(username);
        } else if (connectionEventPacket.isReconnection()) {
            if (!username.equals(clientManager.getGameState().getUsername())) {
                clientManager.getGameState().addPlayerState(new PlayerState(username));
            }
            System.out.println(Printer.YELLOW + "Player " + username + " has reconnected to the Game." + Printer.RESET);
        } else {
            if (!username.equals(clientManager.getGameState().getUsername())) {
                clientManager.getGameState().addPlayerState(new PlayerState(username));
            }
            System.out.println(Printer.YELLOW + "Player " + username + " has joined the Game." + Printer.RESET);
        }
    }
}
