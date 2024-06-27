package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.network.packets.CreateGamePacket;
import it.polimi.ingsw.network.socket.SocketClientConnection;
import it.polimi.ingsw.server.ServerMain;
import it.polimi.ingsw.server.controller.GameController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerCreateGamePacketHandlerTest {

    private GameController controller;
    private ServerNetworkHandler serverNetworkHandler;

    @BeforeEach
    void setUp() {
        serverNetworkHandler = new ServerNetworkHandler("Server", 2010, 6010);
        serverNetworkHandler.setDebug(true);
        serverNetworkHandler.setLobby(true);
        serverNetworkHandler.start();

        controller = serverNetworkHandler.getGameController();
    }

    @Test
    void handlePacketTest() {
        CreateGamePacket packet = new CreateGamePacket(3);
        SocketClientConnection connection = new SocketClientConnection(null, null);

        packet.getServerPacketHandler().handlePacket(packet, controller, connection);
        assertEquals(2, ServerMain.getNextGameId());
    }
}