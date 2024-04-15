package model;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.GoldCard;
import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.game.GameStatusEnum;
import it.polimi.ingsw.server.model.player.Player;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameTest {
    Game game;
    File objectiveJson = new File("/resources/assets/objectivecards");

    @BeforeEach
    void setup() {
        game = new Game(1);
    }

    @Test
    @DisplayName("Test valid players management")
    public void validPlayerList(){

        for (int i = 0; i < 4; i++) {

            Player p = new Player("p"+i, game);
            try {
                game.addPlayer(p);
            } catch (PlayerNotAddedException e) {
                fail("Player not added");
            }
            if(game.getInfo().getPlayersNumber() != i+1){
                fail("Tried to add a player but the number of players is not correct");
            }
            if(!game.getPlayers().get(i).equals(p)){
                fail("Wrong player added");
            }
            assertEquals(game.getPlayers().get(i), game.getPlayerByNick("p"+i));

        }

        assertThrows(DuplicatePlayerException.class, () -> game.addPlayer(new Player("p0", game)));
        assertThrows(DuplicatePlayerException.class, () -> game.addPlayer(new Player("p1", game)));
        assertThrows(DuplicatePlayerException.class, () -> game.addPlayer(new Player("p2", game)));
        assertThrows(DuplicatePlayerException.class, () -> game.addPlayer(new Player("p3", game)));
        assertThrows(FullLobbyException.class, () -> game.addPlayer(new Player("p4", game)));

    }

    @Test
    @DisplayName("Test valid players management")
    public void validPlayerManagement() {

        try{
            game.addPlayer(new Player("p1", game));
            game.addPlayer(new Player("p2", game));
            game.addPlayer(new Player("p3", game));
            game.addPlayer(new Player("p4", game));
        } catch (FullLobbyException | DuplicatePlayerException e) {
            throw new RuntimeException(e);
        }

        assertEquals(4, game.getInfo().getPlayersNumber());
        game.removePlayer(game.getPlayers().get(1));
        for (int i=0; i<game.getInfo().getPlayersNumber(); i++) {
            assertNotEquals("test2", game.getPlayers().get(i).getNickname());
        }
        assertEquals(3, game.getInfo().getPlayersNumber());

    }

    @Test
    @DisplayName("Test valid common objective cards")
    public void validCommonObjectives(){

        ObjectiveCard objective1 = new ObjectiveCard(objectiveJson);
        ObjectiveCard objective2 = new ObjectiveCard(objectiveJson);

        game.addObjectiveCard(objective1);
        game.addObjectiveCard(objective2);

        assertTrue(game.getObjectiveCards().contains(objective1));
        assertTrue(game.getObjectiveCards().contains(objective2));

        try {
            ObjectiveCard extra = new ObjectiveCard(objectiveJson);
            game.addObjectiveCard(extra);
        } catch (IllegalObjectiveException e) {
            fail("Illegal objective card added.");
        }

    }

    //QUESTI SONO TEST CHE SI FANNO NELLA FASE STARTING DEL GAME
    @Test
    @DisplayName("Test valid initial player cards in hand")
    public void validInitialPlayerCardsInHand() {

        game.startGame();

        int numOfGoldCards = 0;
        int numOfResourceCards = 0;

        for (Player player : game.getPlayers()) {
            for (int i=0; i<player.getCardsInHand().size(); i++) {
                if (player.getCardsInHand().get(i) instanceof GoldCard) {
                    numOfGoldCards++;
                } else if (player.getCardsInHand().get(i) instanceof ResourceCard) {
                    numOfResourceCards++;
                }
            }
        }

        assertEquals(1, numOfGoldCards);
        assertEquals(2, numOfResourceCards);

    }

    @Test
    @DisplayName("Valid initial on ground cards")
    public void validInitialOnGroundCards() {

        game.startGame();

        int numOfGoldCards = 0;
        int numOfResourceCards = 0;

        for(Card card : game.getTable().getCardsOnGround()) {
            if (card instanceof GoldCard) {
                numOfGoldCards++;
            } else if (card instanceof ResourceCard) {
                numOfResourceCards++;
            }
        }

        assertEquals(2, numOfGoldCards);
        assertEquals(2, numOfResourceCards);

    }

    //TODO: "meglio suddividere ciascuno status in test singloli?"
    @Test
    @DisplayName("Test valid game status")
    public void validGameStatus(){

        assertEquals(game.getInfo().getGameStatus(), GameStatusEnum.WAITING_FOR_PLAYERS);

        try {
            game.addPlayer(new Player("p1", game));
            game.addPlayer(new Player("p2", game));
            game.addPlayer(new Player("p3", game));
            game.addPlayer(new Player("p4", game));
        } catch (FullLobbyException | DuplicatePlayerException e) {
            throw new RuntimeException(e);
        }

        try {
            game.startGame();
            game.getInfo().setGameStatus(GameStatusEnum.STARTING);
            assertEquals(game.getInfo().getGameStatus(), GameStatusEnum.STARTING);
        } catch (StartGameException e) {
            game.getInfo().setGameStatus(GameStatusEnum.ERROR);
            assertEquals(game.getInfo().getGameStatus(), GameStatusEnum.ERROR);
            fail("Game not correctly started.");
        }

        for (int i=0; i<4; i++) {
            assertNotNull(game.getPlayers().get(i).getToken());
            try{
                game.nextTurn();
            } catch (EndGameException e) {
                throw new RuntimeException(e);
            }
        }

        game.getInfo().setGameStatus(GameStatusEnum.CHOOSING_COLOR);
        assertEquals(game.getInfo().getGameStatus(), GameStatusEnum.CHOOSING_COLOR);

        for (int i=0; i<4; i++) {
            assertNotNull(game.getPlayers().get(i).getObjectiveCard());
            try {
                game.nextTurn();
            } catch (EndGameException e) {
                throw new RuntimeException(e);
            }
        }

        game.getInfo().setGameStatus(GameStatusEnum.CHOOSING_PERSONAL_OBJECTIVE);
        assertEquals(game.getInfo().getGameStatus(), GameStatusEnum.CHOOSING_PERSONAL_OBJECTIVE);

        for (int i=0; i<4; i++) {
            // TODO: implement personal turn move choices
            try {
                game.nextTurn();
            } catch (EndGameException e) {
                throw new RuntimeException(e);
            }
        }

        game.getInfo().setGameStatus(GameStatusEnum.PLAYING);
        assertEquals(game.getInfo().getGameStatus(), GameStatusEnum.PLAYING);

        game.endGame();

        game.getInfo().setGameStatus(GameStatusEnum.ENDING);
        assertEquals(game.getInfo().getGameStatus(), GameStatusEnum.ENDING);

    }
}
