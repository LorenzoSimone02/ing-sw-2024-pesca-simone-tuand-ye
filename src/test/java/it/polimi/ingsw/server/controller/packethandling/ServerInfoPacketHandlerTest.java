package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.network.packets.InfoPacket;
import it.polimi.ingsw.network.socket.SocketClientConnection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerInfoPacketHandlerTest {

    @Test
    void handlePacket() {
        InfoPacket packet = new InfoPacket("Test Info Message");
        SocketClientConnection connection = new SocketClientConnection(null, null);
        packet.getServerPacketHandler().handlePacket(packet, null, connection);
        assertEquals("Test Info Message", packet.getData());
    }
}