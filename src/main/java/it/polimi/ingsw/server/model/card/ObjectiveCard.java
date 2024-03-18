package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.server.model.objectives.Objective;

public class ObjectiveCard extends Card{
    private final Objective objective;

    public ObjectiveCard(Objective objective) {
        this.objective = objective;
    }

    public Objective getObjective() {
        return objective;
    }
}
