package it.polimi.ingsw.server;

import it.polimi.ingsw.network.ServerNetworkHandler;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The class represents the main server of the game
 */
public class ServerMain {

    /**
     * The server's lobby
     */
    private static ServerNetworkHandler lobby;

    /**
     * The server's list of matches
     */
    private static List<ServerNetworkHandler> matches;

    /**
     * The next game id
     */
    private static int nextGameId = 0;

    /**
     * The server's RMI port
     */
    private static final int RMI_PORT = 1099;

    /**
     * The server's socket port
     */
    private static final int SOCKET_PORT = 4900;

    /**
     * The main method of the server
     * @param args the arguments of the main method
     * @throws UnknownHostException if the host is unknown
     */
    public static void main(String[] args) throws UnknownHostException {
        String ipAddress = Inet4Address.getLocalHost().getHostAddress();
        System.setProperty("java.rmi.server.hostname", ipAddress);

        matches = new ArrayList<>();

        lobby = new ServerNetworkHandler("CodexNaturalisServer", RMI_PORT, SOCKET_PORT);
        lobby.setLobby(true);
        lobby.start();

        System.out.println("Lobby Server ready");
    }

    /**
     * The method returns the server's lobby
     * @return the server's lobby
     */
    public static ServerNetworkHandler getLobby() {
        return lobby;
    }

    /**
     * The method returns the server's list of matches
     * @return the server's list of matches
     */
    public static List<ServerNetworkHandler> getMatches() {
        return matches;
    }

    /**
     * The method adds a match to the server's list of matches
     * @param match the match to add
     */
    public static void addMatch(ServerNetworkHandler match) {
        matches.add(match);
    }

    /**
     * The method removes a match from the server's list of matches
     * @param match the match to remove
     */
    public static void removeMatch(ServerNetworkHandler match) {
        matches.remove(match);
    }

    /**
     * The method returns a match from the server's list of matches if there's a match with the given id
     * @param id the id of the match to return
     * @return the match from the server's list of matches
     */
    public static Optional<ServerNetworkHandler> getMatch(int id) {
        return matches.stream().filter(match -> match.getGameController().getGame().getInfo().getId() == id).findFirst();
    }

    /**
     * The method returns the last game's id
     * @return the last game id
     */
    public static int getLastGameId() {
        return nextGameId;
    }

    /**
     * The method returns the next game's id
     * @return the next game id
     */
    public static int getNextGameId() {
        return ++nextGameId;
    }

    /**
     * The method returns the server's RMI port
     * @return the server's RMI port
     */
    public static int getRmiPort() {
        return RMI_PORT;
    }

    /**
     * The method returns the server's socket port
     * @return the server's socket port
     */
    public static int getSocketPort() {
        return SOCKET_PORT;
    }
}
