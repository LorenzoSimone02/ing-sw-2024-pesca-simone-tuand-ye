package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.network.packets.ChatPacket;
import it.polimi.ingsw.network.packets.Packet;

public class ClientChatPacketHandler extends ClientPacketHandler {

    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        ChatPacket chatPacket = (ChatPacket) packet;
        if (chatPacket.getRecipient() != null) {
            if (chatPacket.getRecipient().equalsIgnoreCase(clientManager.getGameState().getUsername()) || chatPacket.getUsername().equalsIgnoreCase(clientManager.getGameState().getUsername())) {
                System.out.println(Printer.ANSI_CYAN + chatPacket.getUsername() + " -> " + chatPacket.getRecipient() + ": " + Printer.ANSI_RESET + chatPacket.getMessage());
                clientManager.getGameState().addChatMessage(chatPacket.getUsername() + " -> " + chatPacket.getRecipient(), chatPacket.getMessage());
            }
        } else {
            System.out.println(Printer.ANSI_CYAN + chatPacket.getUsername() + ": " + Printer.ANSI_RESET + chatPacket.getMessage());
            clientManager.getGameState().addChatMessage(chatPacket.getUsername(), chatPacket.getMessage());
        }

    }
}
