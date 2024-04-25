package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.gamestate.ClientStatusEnum;
import it.polimi.ingsw.network.packets.ChatPacket;

public class ChatCommand extends Command {

    public ChatCommand() {
        commandName = "/chat";
        description = "  Shows the Chat messages or send a new one \n  Usage: /chat <message> /chat -to=player <message> or /chat to see the Chat messages";
        addValidStatus(ClientStatusEnum.LOGGED);
        addValidStatus(ClientStatusEnum.PLAYING);
        addValidStatus(ClientStatusEnum.ENDING);
    }

    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        if (isExecutable(clientManager)) {
            if (input.trim().isEmpty()) {
                System.out.println("Chat messages:");
                for (String msg : clientManager.getGameState().getChatMessages()) {
                    System.out.println(msg);
                }
            } else {
                if (input.split(" ")[0].startsWith("-to=")) {
                    String[] split = input.split(" ");
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

    public boolean isExecutable(ClientManager clientManager) {
        return getValidStatuses().contains(clientManager.getGameState().getClientStatus());
    }
}
