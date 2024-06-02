package it.polimi.ingsw.network.packets;

import it.polimi.ingsw.client.controller.packethandlers.ClientChatPacketHandler;
import it.polimi.ingsw.client.controller.packethandlers.ClientPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerChatPacketHandler;
import it.polimi.ingsw.server.controller.packethandling.ServerPacketHandler;

/**
 * ChatPacket is a Packet that contains a message sent by a player to another player
 */
public class ChatPacket extends Packet {

    /**
     * The username of the player who sent the message
     */
    private final String username;

    /**
     * The username of the player who will receive the message
     */
    private final String recipient;

    /**
     * The message sent by the player
     */
    private final String message;

    /**
     * Constructor of the class
     * @param username the username of the player who sent the message
     * @param recipient the username of the player who will receive the message
     * @param message the message sent by the player
     */
    public ChatPacket(String username, String recipient, String message) {
        this.username = username;
        this.recipient = recipient;
        this.message = message;
    }

    /**The method returns the username of the player who sent the message
     * @return the username of the player who sent the message
     */
    public String getUsername() {
        return username;
    }

    /**
     * The method returns the username of the player who will receive the message
     * @return the username of the player who will receive the message
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * The method returns the message sent by the player
     * @return the message sent by the player
     */
    public String getMessage() {
        return message;
    }

    /**
     * The method returns the client-side chat message packets handler
     * @return the client-side chat message packets handler
     */
    @Override
    public ClientPacketHandler getClientPacketHandler() {
        return new ClientChatPacketHandler();
    }

    /**
     * The method returns the server-side chat message packets handler
     * @return the server-side chat message packets handler
     */
    @Override
    public ServerPacketHandler getServerPacketHandler() {
        return new ServerChatPacketHandler();
    }
}
