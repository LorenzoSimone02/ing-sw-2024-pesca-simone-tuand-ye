package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.network.packets.TurnCardPacket;
import it.polimi.ingsw.network.socket.SocketClientConnection;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.card.FaceEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ServerTurnCardPacketHandlerTest {

    private GameController controller;
    private ServerNetworkHandler serverNetworkHandler;

    @BeforeEach
    void setUp() {
        serverNetworkHandler = new ServerNetworkHandler("Server", 2000, 6000);
        serverNetworkHandler.setDebug(true);
        serverNetworkHandler.start();

        controller = serverNetworkHandler.getGameController();
        controller.createGame(1);
    }


    @Test
    void handlePacketTest() throws IOException {
        TurnCardPacket packet = new TurnCardPacket(1);
        SocketClientConnection connection = new SocketClientConnection(null,  null);
        connection.setUsername("p1");
        controller.addPlayer("p1");
        controller.instantiateCards();
        controller.getPlayerController("p1").getPlayer().getCardsInHand().add(controller.getCardById(1));
        assertEquals(FaceEnum.FRONT, controller.getCardById(1).getFace());
        packet.getServerPacketHandler().handlePacket(packet, controller, connection);
        assertEquals(FaceEnum.BACK, controller.getCardById(1).getFace());
    }
}