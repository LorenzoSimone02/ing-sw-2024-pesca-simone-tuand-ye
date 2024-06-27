package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.network.packets.PingPacket;
import it.polimi.ingsw.network.socket.SocketClientConnection;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerPingPacketHandlerTest {

    private GameController controller;

    @BeforeEach
    void setUp() {
        ServerNetworkHandler serverNetworkHandler = new ServerNetworkHandler("Server", 2035, 6035);
        serverNetworkHandler.setDebug(true);
        serverNetworkHandler.start();

        controller = serverNetworkHandler.getGameController();
        controller.createGame(1);
    }


    @Test
    void handlePacketTest() {
        PingPacket packet = new PingPacket();
        SocketClientConnection connection = new SocketClientConnection(null, null);
        connection.setUsername("p1");
        controller.addPlayer("p1");
        controller.addPlayer("p2");
        packet.getServerPacketHandler().handlePacket(packet, controller, connection);
        assertTrue(System.currentTimeMillis() - connection.getLastPing() < 5);
    }
}