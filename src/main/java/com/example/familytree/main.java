package com.example.familytree;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.*;

public class main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        begining.makecomplexfamilytree(8,3);
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("loginScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hoşgeldiniz");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();


    }

    private static class begining {

        private static final Random random = new Random();

        private static final List<String> names = Arrays.asList(
                "Emre", "Zeynep", "Elif", "Mert", "Deniz", "Burak", "Ayşe", "Mehmet", "Fatma", "Ali",
                "Can", "Cem", "Selin", "Ahmet", "Derya", "Sena", "Baran", "İpek", "Ege", "Hülya",
                "Sibel", "Arda", "Melis", "Onur", "Aslı", "Serkan", "Ezgi", "Cansu", "Gizem", "Kerem"
        );

        private static final List<String> surnames = Arrays.asList(
                "Yanalak", "Kaya", "Demir", "Şahin", "Acar", "Çelik", "Koç", "Polat", "Öztürk", "Şeker",
                "Aslan", "Bozkurt", "Taş", "Yalçın", "Güneş", "Arslan", "Kurt", "Korkmaz", "Türkmen", "Bilgin"
        );

        private static void makecomplexfamilytree(int depth, int maxChildren) {
            boolean[] havechildren = new boolean[depth];
            human root = generateRandomTree(depth, maxChildren, havechildren);
            FamilyTree tree2 = new FamilyTree("hazir",root);
        }

        private static human generateRandomTree(int depth, int maxChildren, boolean[] havechildren) {
            String name = names.get(random.nextInt(names.size()));
            String surname = surnames.get(random.nextInt(surnames.size()));
            int birthyear = 0;
            human person = new human(name, surname, birthyear);

            if (depth > 0) {
                int childCount = random.nextInt(maxChildren + 1);


                if (childCount == 0 && !havechildren[depth - 1]) {            // childcount 0 ve o derinlikte çocuk yoksa
                    childCount = 1;
                }

                for (int i = 0; i < childCount; i++) {
                    human child = generateRandomTree(depth - 1, maxChildren, havechildren);
                    person.addChild(child);
                }

                havechildren[depth - 1] = true;
            }

            return person;
        }


    }



}