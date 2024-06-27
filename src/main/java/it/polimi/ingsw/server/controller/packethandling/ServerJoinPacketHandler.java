package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.client.controller.Printer;
import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.network.packets.*;
import it.polimi.ingsw.server.ServerMain;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.save.GameSave;
import it.polimi.ingsw.server.model.game.GameStatusEnum;

/**
 * The class that handles the joining client packets from the clients
 */
public class ServerJoinPacketHandler extends ServerPacketHandler {

    /**
     * The method handles the joining client packets from the client
     *
     * @param packet     the join packet
     * @param controller the game controller
     * @param connection the connection of the client
     */
    @Override
    public void handlePacket(Packet packet, GameController controller, ClientConnection connection) {
        if (!controller.getNetworkHandler().isLobby()) {
            controller.getNetworkHandler().sendPacket(connection, new InfoPacket("You can't join a Game while you are in another Game."));
            return;
        }

        int gameID;
        ServerNetworkHandler oldMatch = null;
        for (gameID = 1; gameID <= ServerMain.getLastGameId(); gameID++) {
            if (ServerMain.getMatch(gameID).isPresent()) {
                ServerNetworkHandler temp = ServerMain.getMatch(gameID).get();
                if (temp.getConnections().size() < temp.getGameController().getGame().getInfo().getMaxPlayers()
                        && temp.getGameController().hasDisconnected(connection.getUsername())) {
                    oldMatch = temp;
                    break;
                }
            }
        }

        if (oldMatch != null) {
            ServerNetworkHandler networkHandler = ServerMain.getMatch(gameID).get();
            controller.getNetworkHandler().removeConnection(connection);
            networkHandler.addConnection(connection);

            oldMatch.getGameController().reconnectPlayer(connection.getUsername());
            GameSave gameSave = new GameSave(oldMatch.getGameController().getGame());
            networkHandler.sendPacket(connection, new JoinPacket(gameID));
            networkHandler.sendPacket(connection, new GameStartedPacket(oldMatch.getGameController().getGame(), true));
            networkHandler.sendPacket(connection, new PeekDeckPacket(oldMatch.getGameController().getGame().getTable().getResourceDeck().getCards().peek().getId(), false));
            networkHandler.sendPacket(connection, new PeekDeckPacket(oldMatch.getGameController().getGame().getTable().getGoldDeck().getCards().peek().getId(), true));
            networkHandler.sendPacketToAll(new RestoreGameStatePacket(gameSave.getPlayerSaves()));
            System.out.println(Printer.YELLOW + "Player " + connection.getUsername() + " has reconnected to the game " + oldMatch.getGameController().getGame().getInfo().getId() + Printer.RESET);
            return;
        }

        ServerNetworkHandler foundNewMatch = null;
        for (gameID = 1; gameID <= ServerMain.getLastGameId(); gameID++) {
            if (ServerMain.getMatch(gameID).isPresent()) {
                ServerNetworkHandler temp = ServerMain.getMatch(gameID).get();
                if (temp.getConnections().size() < temp.getGameController().getGame().getInfo().getMaxPlayers()
                        && temp.getGameController().getGame().getInfo().getGameStatus() == GameStatusEnum.WAITING_FOR_PLAYERS
                        && temp.getGameController().getPlayerByNick(connection.getUsername()).isEmpty()) {
                    foundNewMatch = temp;
                    break;
                }
            }
        }

        if (foundNewMatch != null) {
            ServerNetworkHandler networkHandler = ServerMain.getMatch(gameID).get();
            controller.getNetworkHandler().removeConnection(connection);
            System.out.println("Removed connection from old lobby with username " + connection.getUsername());
            networkHandler.addConnection(connection);

            foundNewMatch.getGameController().addPlayer(connection.getUsername());
            System.out.println(Printer.YELLOW + "Player " + connection.getUsername() + " has joined the game " + foundNewMatch.getGameController().getGame().getInfo().getId() + Printer.RESET);
            networkHandler.sendPacket(connection, new JoinPacket(gameID));
            networkHandler.sendPacketToAll(new ConnectionEventPacket(connection.getUsername(), false, false));

            foundNewMatch.getGameController().checkStartCondition();
        } else {
            controller.getNetworkHandler().sendPacket(connection, new InfoPacket("There are no available Games at the moment. Create a new Game or try again."));
        }
    }
}
