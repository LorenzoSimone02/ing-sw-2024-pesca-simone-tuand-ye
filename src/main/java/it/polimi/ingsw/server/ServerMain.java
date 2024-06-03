package it.polimi.ingsw.server;

import it.polimi.ingsw.network.ServerNetworkHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServerMain {

    private static List<ServerNetworkHandler> matches;
    private static int nextGameId = 0;
    private static final int RMI_PORT = 1099;
    private static final int SOCKET_PORT = 5000;

    public static void main(String[] args) {
        if (args.length == 1)
            System.setProperty("java.rmi.server.hostname", args[0]);

        matches = new ArrayList<>();

        ServerNetworkHandler lobby = new ServerNetworkHandler("CodexNaturalisServer", RMI_PORT, SOCKET_PORT);
        lobby.setLobby(true);
        lobby.start();

        System.out.println("Lobby Server ready");
    }

    public static List<ServerNetworkHandler> getMatches() {
        return matches;
    }

    public static void addMatch(ServerNetworkHandler match) {
        matches.add(match);
    }

    public static void removeMatch(ServerNetworkHandler match) {
        matches.remove(match);
    }

    public static Optional<ServerNetworkHandler> getMatch(int id) {
        return matches.stream().filter(match -> match.getGameController().getGame().getInfo().getId() == id).findFirst();
    }

    public static int getNextGameId() {
        return ++nextGameId;
    }

    public static int getRmiPort() {
        return RMI_PORT;
    }

    public static int getSocketPort() {
        return SOCKET_PORT;
    }
}
