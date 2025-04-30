package com.example.familytree;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.ArrayList;

import java.util.Collections;

import static com.example.familytree.FamilyTree.getRoot;

public class mainScreenController {
    private static String currentFamilyName;                           // çıkış işlemlerinde burayı null yapmamız gerekebilir.

    @FXML
    private ScrollPane scrollPane_familytree;


    @FXML
    public void ciz(){
        Pane canvas = new Pane();
        human root = getRoot(currentFamilyName);

        // bu komut deneme komutudur








        TreeDrawer.nextX[0] = 50; // başlangıç pozisyonunu sıfırla

        TreeDrawer.drawTree(canvas, root, 50, 100);  // sadece 4 parametre: Pane, root, y, xSpacing


        scrollPane_familytree.setContent(canvas);
    }

    private static class TreeDrawer {
        private static final double[] nextX = {50};  // başlangıç x pozisyonu


        public static double drawTree(Pane pane, human root, double y, double xSpacing) {
            if (root.childlist == null || root.childlist.isEmpty()) {
                // Yaprak düğüm (child yok), bu noktaya çiz
                double x = nextX[0];
                placePerson(pane, root, x, y);
                nextX[0] += xSpacing;
                return x;
            }

            // Çocukları çiz ve ortalamayı hesapla
            List<Double> childXs = new ArrayList<>();
            for (human child : root.childlist) {
                double childX = drawTree(pane, child, y + 80, xSpacing);
                childXs.add(childX);
            }

            // Bu kişiyi çocuklarının ortasına yerleştir
            double minX = Collections.min(childXs);
            double maxX = Collections.max(childXs);
            double x = (minX + maxX) / 2;

            placePerson(pane, root, x, y);

            // Çocuklarla çizgi çiz
            for (double childX : childXs) {
                Line line = new Line(x, y + 15, childX, y + 80);
                pane.getChildren().add(line);
            }

            return x;
        }
        private static void placePerson(Pane pane, human person, double x, double y) {
            Label label = new Label(person.getFullName());
            label.setLayoutX(x - 40);
            label.setLayoutY(y);
            if (person.cinsiyet=='E'){
                label.setTextFill(Color.BLUE);
            }else {
                label.setTextFill(Color.DEEPPINK);
            }
            pane.getChildren().add(label);
        }

    }











//******************************(Getter and Setter)******************************//
    public static String getCurrentFamilyName() {
        return currentFamilyName;
    }

    public static void setCurrentFamilyName(String currentFamilyName) {
        mainScreenController.currentFamilyName = currentFamilyName;
    }
//******************************(Getter and Setter)******************************//

}