package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.network.packets.CreateGamePacket;
import it.polimi.ingsw.network.packets.InfoPacket;
import it.polimi.ingsw.network.packets.Packet;
import it.polimi.ingsw.server.ServerMain;
import it.polimi.ingsw.server.controller.GameController;

/**
 * The class that handles the game creation packets from the clients
 */
public class ServerCreateGamePacketHandler extends ServerPacketHandler {

    /**
     * The method handles the game creation packets from the client
     * @param packet the game creation packet
     * @param controller the game controller
     * @param connection the connection of the client
     */
    @Override
    public void handlePacket(Packet packet, GameController controller, ClientConnection connection) {
        CreateGamePacket createGamePacket = (CreateGamePacket) packet;
        if (!controller.getNetworkHandler().isLobby()) {
            controller.getNetworkHandler().sendPacket(connection, new InfoPacket("You can't create a Game while you are in another Game."));
            return;
        }
        int playersNumber = createGamePacket.getPlayersNumber();
        if (playersNumber < 2 || playersNumber > 4) {
            controller.getNetworkHandler().sendPacket(connection, new InfoPacket("Players number must be between 2 and 4."));
            throw new IllegalArgumentException("Players number must be between 2 and 4.");
        }
        int gameID = ServerMain.getNextGameId();
        try {
            System.out.println("Created a new Game with ID " + gameID + " and " + playersNumber + " players.");
            ServerNetworkHandler newNetworkHandler = new ServerNetworkHandler("Game" + gameID, ServerMain.getRmiPort() + gameID, ServerMain.getSocketPort() + gameID);
            newNetworkHandler.start();
            newNetworkHandler.getGameController().createGame(gameID);
            newNetworkHandler.getGameController().setMaxPlayers(playersNumber);
            ServerMain.addMatch(newNetworkHandler);
            controller.getNetworkHandler().sendPacket(connection, new InfoPacket("Game successfully created!"));
            controller.getNetworkHandler().sendPacket(connection, new CreateGamePacket(createGamePacket.getPlayersNumber()));
        } catch (Exception e) {
            controller.getNetworkHandler().sendPacket(connection, new InfoPacket("Error while creating the Game."));
        }
    }
}
