package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.network.packets.InfoPacket;
import it.polimi.ingsw.network.packets.JoinPacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.server.ServerMain;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.game.GameStatusEnum;

public class ServerJoinPacketHandler extends ServerPacketHandler {

    @Override
    public void handlePacket(Packet packet, GameController controller, ClientConnection connection) {
        if (!controller.getNetworkHandler().isLobby()) {
            controller.getNetworkHandler().sendPacket(connection, new InfoPacket("You can't join a Game while you are in another Game."));
            return;
        }

        int gameID = 1;
        ServerNetworkHandler foundMatch = null;
        while (gameID <= ServerMain.getMatches().size()) {
            if (ServerMain.getMatch(gameID).isPresent()) {
                ServerNetworkHandler temp = ServerMain.getMatch(gameID).get();
                if (temp.getConnections().size() < temp.getGameController().getGame().getInfo().getMaxPlayers() && temp.getGameController().getGame().getInfo().getGameStatus() == GameStatusEnum.WAITING_FOR_PLAYERS) {
                    foundMatch = temp;
                    break;
                }
            }
            gameID++;
        }

        if (foundMatch != null) {
            ServerNetworkHandler networkHandler = ServerMain.getMatch(gameID).get();
            controller.getNetworkHandler().removeConnection(connection);
            networkHandler.addConnection(connection);
            networkHandler.sendPacket(connection, new JoinPacket(gameID));
        } else {
            controller.getNetworkHandler().sendPacket(connection, new InfoPacket("There are no available Games at the moment. Use /createGame to create one or /join to try again."));
        }
    }
}
