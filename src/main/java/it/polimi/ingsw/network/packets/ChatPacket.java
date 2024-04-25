package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientChatPacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerChatPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;

public class ChatPacket extends Packet {

    private final String username;
    private final String recipient;
    private final String message;

    public ChatPacket(String username, String recipient, String message) {
        this.username = username;
        this.recipient = recipient;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientChatPacketHandler();
    }

    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return new ServerChatPacketHandler();
    }
}
