package it.polimi.ingsw.server.model.objectives;

public class Objective {
    private int points;
    private final ObjectiveStrategy strategy;

    public Objective(ObjectiveStrategy strategy) {
        this.strategy = strategy;

    }

    public int getPoints() {
        return points;
    }

}
