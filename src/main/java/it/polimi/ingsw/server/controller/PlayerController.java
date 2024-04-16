package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.server.model.card.corner.Corner;
import it.polimi.ingsw.server.model.card.corner.CornerLocationEnum;
import it.polimi.ingsw.server.model.exceptions.IllegalCardPlacementException;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.PlayerToken;
import it.polimi.ingsw.server.model.resources.ResourceTypeEnum;

public record PlayerController(Player player) {

    public Player getPlayer() {
        return player;
    }

    public void placeCard(ResourceCard card, int x, int y) throws IllegalCardPlacementException {
        if (canPlaceCard(x, y)) {
            card.setXCoord(x);
            card.setYCoord(y);
            player.setCard(card, x, y);
            player.removeCardInHand(card);
            for (Corner corner : card.getCorners()) {
                if (corner.isVisible() && corner.getResource() != null && corner.getResource().getType() != ResourceTypeEnum.EMPTY) {
                    player.addResource(corner.getResource());
                }
            }
            player.setScore(player.getScore() + card.getPoints());
        } else {
            throw new IllegalCardPlacementException();
        }
    }

    public boolean canPlaceCard(int x, int y) {
        if (player.getCardAt(x, y).isEmpty()) {
            if (player.getCardAt(x - 1, y - 1).isPresent()) {
                if (x - 1 == 40 && y - 1 == 40)
                    return true;
                ResourceCard neighbour = (ResourceCard) player.getCardAt(x - 1, y - 1).get();
                if (neighbour.getCorner(CornerLocationEnum.BOTTOM_LEFT).isVisible())
                    return true;
            }
            if (player.getCardAt(x + 1, y + 1).isPresent()) {
                if (x + 1 == 40 && y + 1 == 40)
                    return true;
                ResourceCard neighbour = (ResourceCard) player.getCardAt(x + 1, y + 1).get();
                if (neighbour.getCorner(CornerLocationEnum.TOP_RIGHT).isVisible())
                    return true;
            }
            if (player.getCardAt(x - 1, y + 1).isPresent()) {
                if (x - 1 == 40 && y + 1 == 40)
                    return true;
                ResourceCard neighbour = (ResourceCard) player.getCardAt(x - 1, y + 1).get();
                if (neighbour.getCorner(CornerLocationEnum.TOP_LEFT).isVisible())
                    return true;
            }
            if (player.getCardAt(x + 1, y - 1).isPresent()) {
                if (x - 1 == 40 && y + 1 == 40)
                    return true;
                ResourceCard neighbour = (ResourceCard) player.getCardAt(x + 1, y - 1).get();
                return neighbour.getCorner(CornerLocationEnum.BOTTOM_RIGHT).isVisible();
            }
        }
        return false;
    }

    public void chooseToken(PlayerToken token) {
        for (Player player : player.getGame().getPlayers()) {
            if (player.getToken() != null && player.getToken().color().equals(token.color())) {
                // inserire commento da mostrare al player di cambiare colore
                return;
            }
        }
        player.setToken(token);
    }

    public void chooseObjectiveCard(ObjectiveCard objectiveCard) {
        player.setObjectiveCard(objectiveCard);
    }

    public void setStarterCard(StarterCard starterCard) {
        player.setStarterCard(starterCard);
        starterCard.setXCoord(40);
        starterCard.setYCoord(40);
        player.setCard(starterCard, 40, 40);
    }
}
