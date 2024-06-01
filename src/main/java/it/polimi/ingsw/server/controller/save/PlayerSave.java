package it.polimi.ingsw.server.controller.save;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.player.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerSave implements Serializable {

    private final String username;
    private final int score;
    private final String token;
    private final CardSave[][] cards;
    private final ArrayList<CardSave> cardsInHand;
    private final CardSave starterCard;
    private final CardSave objectiveCard;
    //TODO: Player resources

    public PlayerSave(Player player) {
        this.username = player.getUsername();
        this.score = player.getScore();
        this.token = player.getToken().toString();
        this.cards = new CardSave[81][81];
        for (int i = 0; i < 81; i++) {
            for (int j = 0; j < 81; j++) {
                if (player.getCardAt(i, j).isPresent()) {
                    this.cards[i][j] = new CardSave(player.getCardAt(i, j).get());
                }
            }
        }
        this.cardsInHand = new ArrayList<>(3);
        for (Card cards : player.getCardsInHand()) {
            this.cardsInHand.add(new CardSave(cards));
        }
        this.starterCard = new CardSave(player.getStarterCard());
        this.objectiveCard = new CardSave(player.getObjectiveCard());
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public String getToken() {
        return token;
    }

    public CardSave[][] getCards() {
        return cards;
    }

    public ArrayList<CardSave> getCardsInHand() {
        return cardsInHand;
    }

    public CardSave getStarterCard() {
        return starterCard;
    }

    public CardSave getObjectiveCard() {
        return objectiveCard;
    }
}
