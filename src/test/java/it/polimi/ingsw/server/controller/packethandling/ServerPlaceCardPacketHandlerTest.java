package it.polimi.ingsw.server.controller.packethandling;

import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.network.packets.EndTurnPacket;
import it.polimi.ingsw.network.packets.PlaceCardPacket;
import it.polimi.ingsw.network.socket.SocketClientConnection;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.TokenColorEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ServerPlaceCardPacketHandlerTest {

    private GameController controller;
    private ArrayList<GoldCard> goldCardArray;
    private ArrayList<StarterCard> starterCardArray;
    private ArrayList<ResourceCard> resourceCardArray;

    @BeforeEach
    void setup() throws IOException {

        ServerNetworkHandler serverNetworkHandler = new ServerNetworkHandler("CodexNaturalisServer", 1500, 5500);
        serverNetworkHandler.setDebug(true);
        serverNetworkHandler.start();

        controller = new GameController(serverNetworkHandler);
        controller.createGame(1);

        resourceCardArray = new ArrayList<>();
        goldCardArray = new ArrayList<>();
        starterCardArray = new ArrayList<>();

        Deck resourceDeck = new Deck();
        for (int i = 1; i <= 40; i++) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/assets/resourcecards/resourceCard" + i + ".json"))));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonData = stringBuilder.toString();
            ResourceCard card = new ResourceCard(jsonData);
            resourceCardArray.add(card);
            resourceDeck.addCard(card);
        }
        controller.getGame().getTable().setResourceDeck(resourceDeck);

        Deck goldDeck = new Deck();
        for (int i = 1; i <= 40; i++) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/assets/goldcards/goldCard" + i + ".json"))));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonData = stringBuilder.toString();
            GoldCard card = new GoldCard(jsonData);
            goldCardArray.add(card);
            goldDeck.addCard(card);
        }
        controller.getGame().getTable().setGoldDeck(goldDeck);
        Deck starterDeck = new Deck();
        for (int i = 1; i <= 6; i++) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/assets/startercards/starterCard" + i + ".json"))));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonData = stringBuilder.toString();
            StarterCard card = new StarterCard(jsonData);
            starterCardArray.add(card);
            starterDeck.addCard(card);
        }
        controller.getGame().getTable().setStarterDeck(starterDeck);
        Deck objectiveDeck = new Deck();
        for (int i = 1; i <= 16; i++) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/assets/objectivecards/objectiveCard" + i + ".json"))));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonData = stringBuilder.toString();
            ObjectiveCard card = new ObjectiveCard(jsonData);
            objectiveDeck.addCard(card);
        }
        controller.getGame().getTable().setObjectiveDeck(objectiveDeck);
    }


    @Test
    void handlePacketTest() {
        controller.addPlayer("p1");
        controller.addPlayer("p2");
        Player player = controller.getPlayerByNick("p1").orElse(null);
        controller.getPlayerController("p1").chooseToken(TokenColorEnum.RED);
        controller.getPlayerController("p2").chooseToken(TokenColorEnum.BLUE);
        controller.getPlayerController("p1").setStarterCard(starterCardArray.getFirst(), FaceEnum.FRONT);
        controller.getPlayerController("p2").setStarterCard(starterCardArray.get(1), FaceEnum.FRONT);
        controller.getPlayerController("p1").chooseObjectiveCard((ObjectiveCard) controller.getGame().getTable().getObjectiveDeck().drawCard());
        controller.getPlayerController("p2").chooseObjectiveCard((ObjectiveCard) controller.getGame().getTable().getObjectiveDeck().drawCard());
        controller.startGame();
        controller.getGame().getInfo().setFirstPlayer(player);
        controller.getGame().getInfo().setActivePlayer(player);
        Card card = controller.getPlayerController("p1").getPlayer().getCardsInHand().getFirst();
        card.setFace(FaceEnum.BACK);
        PlaceCardPacket packet = new PlaceCardPacket(card.getId(), 39, 39);
        SocketClientConnection connection = new SocketClientConnection(null,  null);
        connection.setUsername("p1");
        assert player != null;
        assertNull(player.getCardAt(39, 39).orElse(null));
        packet.getServerPacketHandler().handlePacket(packet, controller, connection);
        assertEquals(card.getId(), Objects.requireNonNull(player.getCardAt(39, 39).orElse(null)).getId());
    }
}