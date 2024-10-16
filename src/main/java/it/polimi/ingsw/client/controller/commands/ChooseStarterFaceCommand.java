package it.polimi.ingsw.client.controller.commands;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.network.packets.ChooseStarterFacePacket;
import it.polimi.ingsw.server.model.card.FaceEnum;

/**
 * The class represents the command that allows a player to choose the face of the StarterCard
 */
public class ChooseStarterFaceCommand extends Command{

    /**
     * The constructor of the class
     */
    public ChooseStarterFaceCommand() {
        commandName = "/chooseStarterFace";
        description = "  Choose the face of the StarterCard\n  Usage: /chooseStarterFace <1 or 2>";
        addValidStatus(ClientStatusEnum.CHOOSING_STARTER_FACE);
    }

    /**
     * The method executes the command that allows a player to choose the face of the StarterCard
     * @param input the face to choose
     * @param clientManager the client manager
     */
    @Override
    public void executeCommand(String input, ClientManager clientManager) {
        if (isExecutable(clientManager)) {
            if (input.split(" ").length == 1) {
                String face = input.split(" ")[0];
                if (face.equals("1")) {
                    clientManager.getNetworkHandler().sendPacket(new ChooseStarterFacePacket(FaceEnum.FRONT, clientManager.getGameState().getGivenStarter().getId(), clientManager.getGameState().getUsername()));
                } else if (face.equals("2")) {
                    clientManager.getNetworkHandler().sendPacket(new ChooseStarterFacePacket(FaceEnum.BACK, clientManager.getGameState().getGivenStarter().getId(), clientManager.getGameState().getUsername()));
                } else {
                    System.err.println("Invalid face. Type 1 (for Front) or 2 (for Back)");
                }

            } else {
                System.err.println("Usage: /chooseStarterFace <1 or 2>");
            }
        } else {
            System.err.println("You can't choose your StarterCard's face now.");
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
