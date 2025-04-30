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
        private static final String familytreenamerandom= "hazir";

        private static final Random random = new Random();

        private static final List<String> boys = Arrays.asList(
                "Emre","Mert", "Burak", "Mehmet", "Ali","Mucip", "Can", "Cem", "Ahmet", "Baran", "Ege", "Arda", "Onur", "Serkan", "Kerem"
        );
        private static final List<String> girls = Arrays.asList(
                "Zeynep", "Elif","Ayşe","Fatma", "Selin",  "Derya", "Sena", "İpek", "Hülya","Sibel", "Melis","Aslı", "Ezgi", "Cansu", "Gizem"
        );

        private static final List<String> surnames = Arrays.asList(
                "Yanalak", "Kaya", "Demir", "Şahin", "Acar", "Çelik", "Koç", "Polat", "Öztürk", "Şeker",
                "Aslan", "Bozkurt", "Taş", "Yalçın", "Güneş", "Arslan", "Kurt", "Korkmaz", "Türkmen", "Bilgin"
        );

        private static void makecomplexfamilytree(int depth, int maxChildren) {
            boolean[] havechildren = new boolean[depth];
            human root = generateRandomTree(depth, maxChildren, havechildren);
            FamilyTree tree2 = new FamilyTree(familytreenamerandom,root);
            correctSurnames(root);
            randomizeMarriedStatus(root);

        }
        private static void correctSurnames(human root) {
            if (root == null) return;

            for (human child : root.childlist) {
                if (child.cinsiyet == 'E') {
                    child.surname = root.surname;
                    child.partner.surname = child.surname;
                }
                correctSurnames(child);
            }
        }



        //////////////////////////////////////7 burda hata varrrrrrrrrrrr
        private static void randomizeMarriedStatus(human root) {
            if (root == null) return;

            if (root.childlist.isEmpty() && random.nextBoolean()) {
                if (root.partner != null) {
                    root.partner.partner = null;
                }
                root.partner = null;
            }

            for (human child : root.childlist) {
                randomizeMarriedStatus(child);
            }
        }
        ////////////////////////////////////////////////


        private static human generateRandomTree(int depth, int maxChildren, boolean[] havechildren) {
            int randomtemp = random.nextInt(2);
            String name;
            human partner = new human("aa","aa",0,'a');
            char cinsiyet;
            String surname = surnames.get(random.nextInt(surnames.size()));


            if (randomtemp==1){          // eğer 1 ise erkek
                name = boys.get(random.nextInt(boys.size()));
                cinsiyet = 'E';
                partner.name=girls.get(random.nextInt(girls.size()));
                partner.cinsiyet='K';



            }else {
                name = girls.get(random.nextInt(girls.size()));
                cinsiyet = 'K';
                partner.name=boys.get(random.nextInt(boys.size()));
                partner.cinsiyet='E';
            }

            int birthyear = 0;
            human person = new human(name, surname, birthyear,cinsiyet);

            partner.surname=person.surname;
            person.partner=partner;

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