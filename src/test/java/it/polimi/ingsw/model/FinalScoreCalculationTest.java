package it.polimi.ingsw.model;

import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.exceptions.InvalidObjectiveStrategyException;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.objectives.strategies.*;
import it.polimi.ingsw.server.model.player.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Paths;

public class FinalScoreCalculationTest {
    Game game;
    Player activePlayer;
    File objectiveJson = Paths.get("src/main/resources/assets/objectivecards/resourceCard1.json").toFile();

    @BeforeEach
    void setup() {
        game = new Game(1);
        activePlayer = new Player("Lorenzo", game);
        game.addPlayer(activePlayer);
        game.startGame();

    }

    @Test
    @DisplayName("Test Bottom-Left-L shape final score calculation")
    public void bottomLeftLShapeScore() {

        game.endGame();
        ObjectiveCard objectiveCard = new ObjectiveCard(objectiveJson);
        BottomLeftLShape achievement = new BottomLeftLShape();
        int oldScore = activePlayer.getScore();

        try{
            assertEquals(objectiveCard, activePlayer.getObjectiveCard());
            assertEquals(achievement, objectiveCard.getObjective().getType().getStrategy());
        } catch (InvalidObjectiveStrategyException e) {
            throw new RuntimeException(e);
        }

        activePlayer.setScore(oldScore + achievement.calculatePoints(activePlayer.getCards()));
    }

}
