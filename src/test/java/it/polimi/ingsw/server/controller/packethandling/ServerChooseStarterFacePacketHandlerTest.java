package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.network.packets.ChooseStarterFacePacket;
import it.polimi.ingsw.network.socket.SocketClientConnection;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.card.FaceEnum;
import it.polimi.ingsw.server.model.game.GameStatusEnum;
import it.polimi.ingsw.server.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ServerChooseStarterFacePacketHandlerTest {

    private GameController controller;

    @BeforeEach
    void setUp() {
        ServerNetworkHandler serverNetworkHandler = new ServerNetworkHandler("Server", 2046, 6046);
        serverNetworkHandler.setDebug(true);
        serverNetworkHandler.start();

        controller = serverNetworkHandler.getGameController();
        controller.createGame(1);
    }


    @Test
    void handlePacketTest() throws IOException {
        ChooseStarterFacePacket packet = new ChooseStarterFacePacket(FaceEnum.FRONT, 82, "p1");
        ChooseStarterFacePacket packet2 = new ChooseStarterFacePacket(FaceEnum.BACK, -1, "p1");
        SocketClientConnection connection = new SocketClientConnection(null, null);
        connection.setUsername("p1");
        controller.addPlayer("p1");
        controller.addPlayer("p2");
        controller.instantiateCards();
        Player p1 = controller.getPlayerController("p1").getPlayer();
        assertNull(p1.getStarterCard());
        packet.getServerPacketHandler().handlePacket(packet, controller, connection);
        assertNull(p1.getStarterCard());
        packet2.getServerPacketHandler().handlePacket(packet2, controller, connection);
        assertNull(p1.getObjectiveCard());
        controller.getGame().getInfo().setGameStatus(GameStatusEnum.CHOOSING_STARTER_FACE);
        packet.getServerPacketHandler().handlePacket(packet, controller, connection);
        assertNotNull(p1.getStarterCard());
        assertEquals(82, p1.getStarterCard().getId());
        assertEquals(FaceEnum.FRONT, p1.getStarterCard().getFace());
    }
}