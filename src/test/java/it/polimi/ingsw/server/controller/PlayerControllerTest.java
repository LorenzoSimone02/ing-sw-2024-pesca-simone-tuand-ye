
package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.network.socket.SocketClientConnection;
import it.polimi.ingsw.network.socket.SocketServer;
import it.polimi.ingsw.server.controller.exceptions.AlreadyTakenColorException;
import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.server.model.card.corner.Corner;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.TokenColorEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerControllerTest {

    private GameController controller;
    private ArrayList<GoldCard> goldCardArray;
    private ArrayList<StarterCard> starterCardArray;
    private ArrayList<ResourceCard> resourceCardArray;
    private ArrayList<ObjectiveCard> objectiveCardArray;

    @BeforeEach
    void setup() throws IOException {

        ServerNetworkHandler serverNetworkHandler = new ServerNetworkHandler("Server", 1099, 5000);
        serverNetworkHandler.start();

//        SocketServer socketServer = new SocketServer(serverNetworkHandler, 5000);
//        SocketClientConnection clientConnection1 = new SocketClientConnection(new Socket(), socketServer);
//        SocketClientConnection clientConnection2 = new SocketClientConnection(new Socket(), socketServer);
//        SocketClientConnection clientConnection3 = new SocketClientConnection(new Socket(), socketServer);
//        SocketClientConnection clientConnection4 = new SocketClientConnection(new Socket(), socketServer);
//
        controller = serverNetworkHandler.getGameController();
        controller.createGame(1);
//
        controller.addPlayer("p1");
        controller.addPlayer("p2");
        controller.addPlayer("p3");
        controller.addPlayer("p4");

//        controller.getNetworkHandler().addConnection(clientConnection1);
//        controller.getNetworkHandler().addConnection(clientConnection2);
//        controller.getNetworkHandler().addConnection(clientConnection3);
//        controller.getNetworkHandler().addConnection(clientConnection4);

//        clientConnection1.setUsername(controller.getPlayerController("p1").getPlayer().getUsername());
//        clientConnection2.setUsername(controller.getPlayerController("p2").getPlayer().getUsername());
//        clientConnection3.setUsername(controller.getPlayerController("p3").getPlayer().getUsername());
//        clientConnection4.setUsername(controller.getPlayerController("p4").getPlayer().getUsername());
//
        controller.startGame();

        resourceCardArray = new ArrayList<>();
        goldCardArray = new ArrayList<>();
        starterCardArray = new ArrayList<>();

        for(int i = 1; i <= 40; i++) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/assets/resourcecards/resourceCard" + i + ".json"))));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonData = stringBuilder.toString();
            ResourceCard card = new ResourceCard(jsonData);
            resourceCardArray.add(card);
        }

        for(int i = 1; i <= 40; i++) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/assets/goldcards/goldCard" + i + ".json"))));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonData = stringBuilder.toString();
            GoldCard card = new GoldCard(jsonData);
            goldCardArray.add(card);
        }
        for(int i = 1; i <= 6; i++) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/assets/startercards/starterCard" + i + ".json"))));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonData = stringBuilder.toString();
            StarterCard card = new StarterCard(jsonData);
            starterCardArray.add(card);
        }
        for(int i = 1; i <= 16; i++) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/assets/objectivecards/objectiveCard" + i + ".json"))));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonData = stringBuilder.toString();
            ObjectiveCard card = new ObjectiveCard(jsonData);
            objectiveCardArray.add(card);
        }

    }

    @Test
    @DisplayName("Test valid player")
    public void validPlayer() {
        for(Player player : controller.getGame().getPlayers()) {
            assertNotNull(controller.getPlayerController(player).getPlayer());        }
    }

    @Test
    @DisplayName("Test valid player nickname")
    public void validNickname() {
        Player player = new Player("test", null);
        assertNotNull(player.getUsername());
        assertEquals("test", player.getUsername());
    }

    @Test
    @DisplayName("Test valid player token")
    public void validPlayerToken() {

        try {
            controller.getPlayerController(controller.getGame().getPlayers().getFirst()).chooseToken(TokenColorEnum.RED);
            controller.getPlayerController(controller.getGame().getPlayers().get(1)).chooseToken(TokenColorEnum.GREEN);
            controller.getPlayerController(controller.getGame().getPlayers().get(2)).chooseToken(TokenColorEnum.BLUE);
            controller.getPlayerController(controller.getGame().getPlayers().get(3)).chooseToken(TokenColorEnum.YELLOW);
        } catch (AlreadyTakenColorException e) {
            fail("Token color already taken.");
        }

        assertEquals(TokenColorEnum.RED, controller.getGame().getPlayers().getFirst().getToken().getColor());
        assertEquals(TokenColorEnum.GREEN, controller.getGame().getPlayers().get(1).getToken().getColor());
        assertEquals(TokenColorEnum.BLUE, controller.getGame().getPlayers().get(2).getToken().getColor());
        assertEquals(TokenColorEnum.YELLOW, controller.getGame().getPlayers().get(3).getToken().getColor());
    }

    @Test
    @DisplayName("Test valid card placement")
    public void validCardPlacement() {

        ResourceCard drawnCard = resourceCardArray.get(1);
        int numberOfInHandCards = controller.getPlayerController("p1").getPlayer().getCardsInHand().size();
        int numberOfResources = controller.getPlayerController("p1").getPlayer().getResources().size();

        controller.getPlayerController("p1").getPlayer().addCardInHand(resourceCardArray.get(1));
        controller.getPlayerController("p1").setStarterCard(starterCardArray.get(1), starterCardArray.get(1).getFace());

        controller.getPlayerController("p2").placeCard((ResourceCard) controller.getPlayerController("p1").getPlayer().getCardsInHand().getFirst(), 1, 1);

        assertEquals(numberOfInHandCards - 1, controller.getPlayerController("p1").getPlayer().getCardsInHand().size());
        assertNotNull(controller.getPlayerController("p2").getPlayer().getCardAt(1, 1));
        assertEquals(drawnCard, controller.getPlayerController("p2").getPlayer().getCardAt(1, 1).orElse(null));

        for(Corner corner : resourceCardArray.get(1).getCorners()){
            if(corner.isVisible() && corner.getResource() != null && corner.getResource().getType() != null && corner.getFace() == resourceCardArray.get(1).getFace()){
                numberOfResources++;
            }
        }

        assertEquals(numberOfResources, controller.getPlayerController("p2").getPlayer().getResources().size());

    }

    @Test
    @DisplayName("Test valid objective card")
    public void validObjectiveCard() {

        ObjectiveCard chosenCard = objectiveCardArray.get(1);
        controller.getPlayerController("p1").chooseObjectiveCard(objectiveCardArray.get(1));

        assertEquals(chosenCard, controller.getPlayerController("p1").getPlayer().getObjectiveCard());

    }

    @Test
    @DisplayName("Test valid starter card")
    public void validStarterCard() {

        controller.getPlayerController("p1").turnCard(starterCardArray.get(1));

        FaceEnum chosenFace = starterCardArray.get(1).getFace();

        controller.getPlayerController("p1").setStarterCard(starterCardArray.get(1), starterCardArray.get(1).getFace());

        assertNotNull(controller.getPlayerController("p1").getPlayer().getStarterCard());
        assertEquals(chosenFace, controller.getPlayerController("p1").getPlayer().getStarterCard().getFace());

    }
}
