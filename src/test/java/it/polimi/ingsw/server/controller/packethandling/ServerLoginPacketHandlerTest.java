package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.network.packets.LoginPacket;
import it.polimi.ingsw.network.socket.SocketClientConnection;
import it.polimi.ingsw.server.controller.GameController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ServerLoginPacketHandlerTest {

    private GameController controller;
    private ServerNetworkHandler serverNetworkHandler;

    @BeforeEach
    void setUp() {
        serverNetworkHandler = new ServerNetworkHandler("Server", 2040, 6040);
        serverNetworkHandler.setDebug(true);
        serverNetworkHandler.setLobby(true);
        serverNetworkHandler.start();

        controller = serverNetworkHandler.getGameController();
    }


    @Test
    void handlePacketTest() {
        LoginPacket packet = new LoginPacket("testName");
        SocketClientConnection connection = new SocketClientConnection(null,  null);
        assertNull(connection.getUsername());
        packet.getServerPacketHandler().handlePacket(packet, controller, connection);
        assertEquals("testName", connection.getUsername());
    }

    @Test
    void handlePacketTestNotInLobby() {
        serverNetworkHandler.setLobby(false);
        LoginPacket packet = new LoginPacket("testName");
        SocketClientConnection connection = new SocketClientConnection(null,  null);
        assertNull(connection.getUsername());
        packet.getServerPacketHandler().handlePacket(packet, controller, connection);
        assertNull(connection.getUsername());
    }

}