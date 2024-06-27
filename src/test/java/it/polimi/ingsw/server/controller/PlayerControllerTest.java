
package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.server.model.card.corner.Corner;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.TokenColorEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerControllerTest {

    private ServerNetworkHandler serverNetworkHandler;
    private GameController controller;
    private ArrayList<GoldCard> goldCardArray;
    private ArrayList<StarterCard> starterCardArray;
    private ArrayList<ResourceCard> resourceCardArray;
    private ArrayList<ObjectiveCard> objectiveCardArray;
    private static int id = 70;

    @BeforeEach
    void setup() throws IOException {

        serverNetworkHandler = new ServerNetworkHandler("Server", 1099 + id, 5001);
        serverNetworkHandler.start();

        controller = serverNetworkHandler.getGameController();
        controller.createGame(1);
        controller.addPlayer("p1");
        controller.addPlayer("p2");
        controller.addPlayer("p3");
        controller.addPlayer("p4");

        controller.startGame();

        resourceCardArray = new ArrayList<>();
        goldCardArray = new ArrayList<>();
        starterCardArray = new ArrayList<>();
        objectiveCardArray = new ArrayList<>();

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
        }

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
        }
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
        }
        for (int i = 1; i <= 16; i++) {
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
        id++;
    }

    @AfterEach
    void tearDown() {
        serverNetworkHandler.stop();
    }

    @Test
    @DisplayName("Test valid player")
    public void validPlayer() {
        for (Player player : controller.getGame().getPlayers()) {
            assertNotNull(controller.getPlayerController(player).getPlayer());
        }
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

        controller.getPlayerController(controller.getGame().getPlayers().getFirst()).chooseToken(TokenColorEnum.RED);
        controller.getPlayerController(controller.getGame().getPlayers().get(1)).chooseToken(TokenColorEnum.RED);
        controller.getPlayerController(controller.getGame().getPlayers().get(1)).chooseToken(TokenColorEnum.GREEN);
        controller.getPlayerController(controller.getGame().getPlayers().get(2)).chooseToken(TokenColorEnum.BLUE);
        controller.getPlayerController(controller.getGame().getPlayers().get(3)).chooseToken(TokenColorEnum.YELLOW);


        assertEquals(TokenColorEnum.RED, controller.getGame().getPlayers().getFirst().getToken().getColor());
        assertEquals(TokenColorEnum.GREEN, controller.getGame().getPlayers().get(1).getToken().getColor());
        assertEquals(TokenColorEnum.BLUE, controller.getGame().getPlayers().get(2).getToken().getColor());
        assertEquals(TokenColorEnum.YELLOW, controller.getGame().getPlayers().get(3).getToken().getColor());
    }

    @Test
    @DisplayName("Test valid card placement")
    public void validCardPlacement() {

        ResourceCard drawnCard = resourceCardArray.get(1);
        int numberOfResources = controller.getPlayerController("p1").getPlayer().getResources().size();

        controller.getPlayerController("p1").getPlayer().addCardInHand(resourceCardArray.get(1));
        controller.getPlayerController("p1").setStarterCard(starterCardArray.get(1), starterCardArray.get(1).getFace());

        controller.getPlayerController("p1").placeCard((ResourceCard) controller.getPlayerController("p1").getPlayer().getCardsInHand().getFirst(), 39, 39);

        assertNotNull(controller.getPlayerController("p1").getPlayer().getCardAt(40, 40));
        assertEquals(drawnCard.getId(), Objects.requireNonNull(controller.getPlayerController("p1").getPlayer().getCardAt(39, 39).orElse(null)).getId());

        for (Corner corner : resourceCardArray.get(1).getCorners()) {
            if (corner.isVisible() && corner.getResource() != null && corner.getResource().getType() != null && corner.getFace() == resourceCardArray.get(1).getFace()) {
                numberOfResources++;
            }
        }

        assertEquals(5, controller.getPlayerController("p1").getPlayer().getResources().size());
        assertEquals(3, numberOfResources);
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

    @Test
    public void turnCardTest(){
        PlayerController playerController = controller.getPlayerController("p1");
        playerController.getPlayer().addCardInHand(resourceCardArray.get(1));
        resourceCardArray.get(1).setFace(FaceEnum.BACK);
        playerController.turnCard(playerController.getPlayer().getCardsInHand().getFirst());
        assertEquals(FaceEnum.FRONT, playerController.getPlayer().getCardsInHand().getFirst().getFace());
    }

    @Test
    public void canPlaceCardTest(){
        PlayerController playerController = controller.getPlayerController("p1");
        playerController.getPlayer().addCardInHand(resourceCardArray.get(1));
        playerController.setStarterCard(starterCardArray.get(1), starterCardArray.get(1).getFace());
        playerController.placeCard((ResourceCard) playerController.getPlayer().getCardsInHand().getFirst(), 39, 39);
        assertFalse(playerController.canPlaceCard((ResourceCard) playerController.getPlayer().getCardsInHand().getFirst(), 39, 39));
        playerController.getPlayer().addCardInHand(resourceCardArray.get(2));
        playerController.getPlayer().addCardInHand(goldCardArray.get(1));
        assertTrue(playerController.canPlaceCard((ResourceCard) playerController.getPlayer().getCardsInHand().get(1), 41, 41));
        assertFalse(playerController.canPlaceCard((GoldCard) playerController.getPlayer().getCardsInHand().get(2), 39, 41));
    }
}
