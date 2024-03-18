package it.polimi.ingsw.server.model.game;

public class ScoreTrack {
    private final int[] points;

    public ScoreTrack(){
        this.points = new int[28];
        for(int i = 0; i < 28; i++){
            this.points[i] = i;
        }
    }

    public int[] getPoints() {
        return points;
    }
}
