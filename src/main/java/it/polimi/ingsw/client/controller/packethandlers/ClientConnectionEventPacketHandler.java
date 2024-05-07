package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.network.packets.ConnectionEventPacket;
import it.polimi.ingsw.network.packets.Packet;

public class ClientConnectionEventPacketHandler extends ClientPacketHandler {

    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        ConnectionEventPacket connectionEventPacket = (ConnectionEventPacket) packet;
        String username = connectionEventPacket.getUsername();
        if (connectionEventPacket.isDisconnection()) {
            System.out.println(Printer.ANSI_YELLOW + "Player " + username + " has disconnected from the Game." + Printer.ANSI_RESET);
        } else if (connectionEventPacket.isReconnection()) {
            System.out.println(Printer.ANSI_YELLOW + "Player " + username + " has reconnected to the Game." + Printer.ANSI_RESET);
        } else {
            System.out.println(Printer.ANSI_YELLOW + "Player " + username + " has joined the Game." + Printer.ANSI_RESET);
        }
    }
}
