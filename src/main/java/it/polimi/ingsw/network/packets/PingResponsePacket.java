package it.polimi.ingsw.network.packets;

public class PingResponsePacket extends ClientPacket {

    public PingResponsePacket() {
    }

    @Override
    public void handle() {
        System.out.println("Ping response packet received by " + getSender());
    }
}
