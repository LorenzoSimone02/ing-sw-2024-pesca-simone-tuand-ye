package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.network.packets.ChatPacket;
import it.polimi.ingsw.network.packets.Packet;

/**
 * The class that handles the chat packets from the server
 */
public class ClientChatPacketHandler extends ClientPacketHandler {

    /**
     * The method handles the chat packet
     * @param packet the chat packet
     * @param clientManager the client manager
     */
    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        ChatPacket chatPacket = (ChatPacket) packet;
        if (chatPacket.getRecipient() != null) {
            if (chatPacket.getRecipient().equalsIgnoreCase(clientManager.getGameState().getUsername()) || chatPacket.getUsername().equalsIgnoreCase(clientManager.getGameState().getUsername())) {
                System.out.println(Printer.CYAN + chatPacket.getUsername() + " -> " + chatPacket.getRecipient() + ": " + Printer.RESET + chatPacket.getMessage());
                clientManager.getGameState().addChatMessage(chatPacket.getUsername() + " -> " + chatPacket.getRecipient(), chatPacket.getMessage());
            }
        } else {
            System.out.println(Printer.CYAN + chatPacket.getUsername() + ": " + Printer.RESET + chatPacket.getMessage());
            clientManager.getGameState().addChatMessage(chatPacket.getUsername(), chatPacket.getMessage());
        }

    }
}
