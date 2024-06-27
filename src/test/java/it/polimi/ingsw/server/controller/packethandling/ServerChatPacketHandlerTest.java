package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.network.packets.ChatPacket;
import it.polimi.ingsw.network.socket.SocketClientConnection;
import it.polimi.ingsw.server.controller.GameController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ServerChatPacketHandlerTest {

    private GameController controller;

    @BeforeEach
    void setUp() {
        ServerNetworkHandler serverNetworkHandler = new ServerNetworkHandler("Server", 2850, 6850);
        serverNetworkHandler.setDebug(true);
        serverNetworkHandler.start();

        controller = serverNetworkHandler.getGameController();
        controller.createGame(1);
    }


    @Test
    void handlePacketTest() {
        ChatPacket packet = new ChatPacket("p1", "p2", "Test Message");
        SocketClientConnection connection = new SocketClientConnection(null, null);
        connection.setUsername("p1");
        controller.addPlayer("p1");
        controller.addPlayer("p2");
        packet.getServerPacketHandler().handlePacket(packet, controller, connection);
        assertNotNull(packet.getRecipient());
        assertEquals("p2", packet.getRecipient());
        assertNotNull(packet.getMessage());
        assertEquals("Test Message", packet.getMessage());
    }
}