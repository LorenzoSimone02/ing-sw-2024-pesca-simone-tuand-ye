package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.network.packets.ChooseColorPacket;
import it.polimi.ingsw.network.packets.TurnCardPacket;
import it.polimi.ingsw.network.socket.SocketClientConnection;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.card.FaceEnum;
import it.polimi.ingsw.server.model.game.GameStatusEnum;
import it.polimi.ingsw.server.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ServerChooseColorPacketHandlerTest {

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
        ChooseColorPacket packet = new ChooseColorPacket("p1",  "YELLOW");
        ChooseColorPacket packet2 = new ChooseColorPacket("p1",  null);
        SocketClientConnection connection = new SocketClientConnection(null,  null);
        connection.setUsername("p1");
        controller.addPlayer("p1");
        controller.addPlayer("p2");
        Player p1 = controller.getPlayerController("p1").getPlayer();
        assertNull(p1.getToken());
        packet.getServerPacketHandler().handlePacket(packet, controller, connection);
        assertNull(p1.getToken());
        packet2.getServerPacketHandler().handlePacket(packet2, controller, connection);
        assertNull(p1.getToken());
        controller.getGame().getInfo().setGameStatus(GameStatusEnum.CHOOSING_COLOR);
        packet.getServerPacketHandler().handlePacket(packet, controller, connection);
        assertNotNull(p1.getToken());
        assertEquals("YELLOW", p1.getToken().getColor().toString());
    }
}