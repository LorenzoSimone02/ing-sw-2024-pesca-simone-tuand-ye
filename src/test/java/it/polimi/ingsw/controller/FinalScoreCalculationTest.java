package it.polimi.ingsw.controller;

import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.controller.exceptions.InvalidObjectiveStrategyException;
import it.polimi.ingsw.server.model.objectives.strategies.BottomLeftLShape;
import it.polimi.ingsw.server.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FinalScoreCalculationTest {

    private GameController controller;
    private Player activePlayer;
    private final File objectiveJson = Paths.get("src/main/resources/assets/objectivecards/resourceCard1.json").toFile();

    @BeforeEach
    void setup() {
        controller = new GameController(null);
        controller.createGame(1);
        controller.addPlayer("Lorenzo");
        activePlayer = controller.getPlayerByNick("Lorenzo").orElse(null);
        controller.startGame();

    }

    @Test
    @DisplayName("Test Bottom-Left-L shape final score calculation")
    public void bottomLeftLShapeScore() {

        controller.endGame();
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
