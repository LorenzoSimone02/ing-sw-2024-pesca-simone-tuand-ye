package it.polimi.ingsw.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class ClientMain extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setTitle("Codex Naturalis");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/logo.png"))));
        stage.setScene(scene);
        stage.requestFocus();
        stage.show();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String selected;

        try {
            do {
                System.out.println("Seleziona l'interfaccia di visualizzazione:");
                System.out.println("[1] CLI - [2] GUI");
                selected = scanner.nextLine();

                if (selected.equals("1") || selected.equals("CLI")) {
                   System.out.println("Hai selezionato la CLI.");
                } else if (selected.equals("2") || selected.equals("GUI")) {
                    System.out.println("Hai selezionato la GUI.");
                    launch();
                }else{
                    System.out.println("L'interfaccia selezionata non è valida.");
                }

            } while (!(selected.equals("1") || selected.equals("2") || selected.equals("CLI") || selected.equals("GUI")));
        } catch (Exception e) {
            System.err.println("Si è verificato un errore!");
        }
    }
}