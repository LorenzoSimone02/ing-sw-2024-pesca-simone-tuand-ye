package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.network.packets.ChatPacket;
import it.polimi.ingsw.network.packets.JoinPacket;
import it.polimi.ingsw.network.socket.SocketClientConnection;
import it.polimi.ingsw.server.ServerMain;
import it.polimi.ingsw.server.controller.GameController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerJoinPacketHandlerTest {

    private GameController controller;

    @BeforeEach
    void setUp() {
        ServerNetworkHandler serverNetworkHandler = new ServerNetworkHandler("Server", 2830, 6830);
        serverNetworkHandler.setDebug(true);
        serverNetworkHandler.start();

        controller = serverNetworkHandler.getGameController();
        controller.createGame(1);
    }


    @Test
    void handlePacketTest() {
        JoinPacket packet = new JoinPacket();
        SocketClientConnection connection = new SocketClientConnection(null, null);
        connection.setUsername("p1");
        controller.addPlayer("p1");
        controller.addPlayer("p2");
        packet.getServerPacketHandler().handlePacket(packet, controller, connection);
        assertNull( ServerMain.getMatches());
    }
}