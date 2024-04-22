package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.server.model.card.corner.Corner;
import it.polimi.ingsw.server.model.card.corner.CornerLocationEnum;
import it.polimi.ingsw.server.controller.exceptions.AlreadyTakenColorException;
import it.polimi.ingsw.server.controller.exceptions.IllegalCardPlacementException;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.PlayerToken;
import it.polimi.ingsw.server.model.resources.ResourceTypeEnum;
import it.polimi.ingsw.server.model.resources.ObjectTypeEnum;

public record PlayerController(Player player) {

    public Player getPlayer() {
        return player;
    }

    public synchronized void placeCard(ResourceCard card, int x, int y) throws IllegalCardPlacementException {
        if (canPlaceCard(x, y)) {
            card.setXCoord(x);
            card.setYCoord(y);
            player.setCard(card, x, y);
            player.removeCardInHand(card);
            for (Corner corner : card.getCorners()) {
                if (corner.isVisible() && corner.getResource() != null && corner.getResource().getType() != ResourceTypeEnum.EMPTY) {
                    player.addResource(corner.getResource());
                }
                if (corner.isVisible() && corner.getObject() != null && corner.getObject().getType() != ObjectTypeEnum.EMPTY) {
                    player.addObject(corner.getObject());
                }
            }
            if (card instanceof GoldCard) {
                player.setScore(player.getScore() + ((GoldCard) card).getPointsStrategy().getStrategy().calculatePoints(player, x, y));
            } else {
                player.setScore(player.getScore() + card.getPoints());
            }
        } else {
            throw new IllegalCardPlacementException();
        }
    }

    public synchronized boolean canPlaceCard(int x, int y) {
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

    public synchronized void chooseToken(PlayerToken token) {
        for (Player player : player.getGame().getPlayers()) {
            if (player.getToken() != null && player.getToken().color().equals(token.color())) {
                // inserire commento da mostrare al player di cambiare colore
                throw new AlreadyTakenColorException(token.getColor());
            }
        }
        player.setToken(token);
    }

    public synchronized void chooseObjectiveCard(ObjectiveCard objectiveCard) {
        player.setObjectiveCard(objectiveCard);
    }

    public synchronized void setStarterCard(StarterCard starterCard) {
        player.setStarterCard(starterCard);
        starterCard.setXCoord(40);
        starterCard.setYCoord(40);
        player.setCard(starterCard, 40, 40);
    }

    public synchronized void turnCard(Card card) {
        if (card.getFace() == FaceEnum.FRONT) {
            card.setFace(FaceEnum.BACK);
        } else if (card.getFace() == FaceEnum.BACK) {
            card.setFace(FaceEnum.FRONT);
        }
    }
}
