package it.polimi.ingsw.server;

import it.polimi.ingsw.network.ServerNetworkHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServerMain {

    private static List<ServerNetworkHandler> matches;
    private static final int RMI_PORT = 1099;
    private static final int SOCKET_PORT = 5000;

    public static void main(String[] args) {
        matches = new ArrayList<>();

        //System.setProperty("java.rmi.server.hostname", ipAddress);

        ServerNetworkHandler lobby = new ServerNetworkHandler("Server", RMI_PORT, SOCKET_PORT);
        lobby.setLobby(true);
        lobby.start();

        System.out.println("Lobby ready");
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
        return matches.isEmpty() ? Optional.empty() : Optional.of(matches.get(id - 1));
    }

    public static int getRmiPort() {
        return RMI_PORT;
    }

    public static int getSocketPort() {
        return SOCKET_PORT;
    }
}
