package it.polimi.ingsw.client.controller.packethandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.client.controller.clientstate.ClientStatusEnum;
import it.polimi.ingsw.client.controller.clientstate.PlayerState;
import it.polimi.ingsw.network.packets.GameEndedPacket;
import it.polimi.ingsw.network.packets.Packet;

public class ClientGameEndedPacketHandler extends ClientPacketHandler {

    @Override
    public void handlePacket(Packet packet, ClientManager clientManager) {
        GameEndedPacket gameEndedPacket = (GameEndedPacket) packet;

        clientManager.getGameState().setScore(gameEndedPacket.getScores().get(clientManager.getGameState().getUsername()));
        for (PlayerState playerState : clientManager.getGameState().getPlayerStates()) {
            playerState.setScore(gameEndedPacket.getScores().get(playerState.getUsername()));
        }
        clientManager.getUserInterface().showMessage(Printer.GREEN + "The game has ended! The winner is: ");
        for (String winner : gameEndedPacket.getWinners()) {
            clientManager.getUserInterface().showMessage(winner);
            clientManager.getGameState().addWinner(winner);
        }
        clientManager.getUserInterface().showMessage("Players Scores:");
        clientManager.getUserInterface().showMessage(Printer.PURPLE + clientManager.getGameState().getUsername() + ": " + Printer.RESET + clientManager.getGameState().getScore() + " points");
        for (PlayerState player : clientManager.getGameState().getPlayerStates()) {
            clientManager.getUserInterface().showMessage(Printer.PURPLE + player.getUsername() + ": " + Printer.RESET + player.getScore() + " points");
        }
        clientManager.getGameState().setClientStatus(ClientStatusEnum.ENDED);
    }
}
