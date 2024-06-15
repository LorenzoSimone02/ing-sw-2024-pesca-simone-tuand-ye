package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.network.packets.ChatPacket;

/**
 * The class represents the command that allows a player to send a message in the chat
 */
public class ChatCommand extends Command {

    /**
     * The constructor of the class
     */
    public ChatCommand() {
        commandName = "/chat";
        description = "  Shows the Chat messages or send a new one \n  Usage: /chat <message> /chat -to=player <message> or /chat to see the Chat messages";
        addValidStatus(ClientStatusEnum.PLAYING);
        addValidStatus(ClientStatusEnum.LAST_TURN);
        addValidStatus(ClientStatusEnum.ENDED);
    }

    /**
     * The method executes the command that allows a player to send a message in the chat
     * @param input the message to send
     * @param clientManager the client manager
     */
    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        if (isExecutable(clientManager)) {
            if (input.trim().isEmpty()) {
                System.out.println("Chat messages:");
                for (String msg : clientManager.getGameState().getChatMessages()) {
                    System.out.println(msg);
                }
            } else {
                String[] split = input.split(" ");
                if (split[0].startsWith("-to=")) {
                    if (split.length < 2) {
                        System.err.println("Invalid command. Usage: /chat -to=player <message>");
                        return;
                    }
                    String recipient = split[0].substring(4);
                    if (recipient.equalsIgnoreCase(clientManager.getGameState().getUsername())) {
                        System.err.println("You can't send a message to yourself.");
                        return;
                    }
                    String message = input.substring(4 + recipient.length());
                    clientManager.getNetworkHandler().sendPacket(new ChatPacket(clientManager.getGameState().getUsername(), recipient, message));
                } else {
                    clientManager.getNetworkHandler().sendPacket(new ChatPacket(clientManager.getGameState().getUsername(), null, input));
                }
            }
        } else {
            System.err.println("You can't send a chat message now.");
        }
    }

    /**
     * The method checks if the command is executable
     * @param clientManager the client manager
     * @return true if the command is executable, false otherwise
     */
    public boolean isExecutable(ClientManager clientManager) {
        return getValidStatuses().contains(clientManager.getGameState().getClientStatus());
    }
}
