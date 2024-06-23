package it.polimi.ingsw.client.view.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ControllerEndGameScreen {

    @FXML Label player1Name;
    @FXML Label player2Name;
    @FXML Label player3Name;
    @FXML Label player4Name;

    @FXML Label player1Points;
    @FXML Label player2Points;
    @FXML Label player3Points;
    @FXML Label player4Points;

    @FXML Pane pane;
    @FXML Button menuButton;

    public void initialize(
            String[] player1,
            String[] player2,
            String[] player3,
            String[] player4){

        ArrayList<String[]> players = new ArrayList<String[]>();

        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);

        String[][] p = players.toArray(new String[players.size()][]);

        Arrays.sort(p, new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                return Double.valueOf(o2[1]).compareTo(Double.valueOf(o1[1]));
            }
        });

        for(String[] p1 : p){
            System.out.println(p1[0] + " " + p1[1]);
        }

        if(!p[0][0].isBlank()){
            player1Name.setText(p[0][0]);
            player1Points.setText(p[0][1] + " points");
        }
        if(!p[1][0].isBlank()){
            player2Name.setText(p[1][0]);
            player2Points.setText(p[1][1] + " points");
        }
        if(!p[2][0].isBlank()){
            player3Name.setText(p[2][0]);
            player3Points.setText(p[2][1] + " points");
        }
        if(!p[3][0].isBlank()){
            player4Name.setText(p[3][0]);
            player4Points.setText(p[3][1] + " points");
        }

        pane.setStyle("-fx-background-image: url('/background.jpg');");
    }

    public void goToMenu(ActionEvent event){
        //Go to menu
    }

}
