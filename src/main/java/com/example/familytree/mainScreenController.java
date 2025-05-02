package com.example.familytree;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Optional;

import static com.example.familytree.FamilyTree.getRoot;

public class mainScreenController {
    private static String currentFamilyName;                           // çıkış işlemlerinde burayı null yapmamız gerekebilir.

    @FXML
    private ScrollPane scrollPane_familytree;

    @FXML
    private void familyTreeOperations() throws IOException {                                            // soy ağacı işlemleri komutu
        Stage stage = (Stage) scrollPane_familytree.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("treeOperations.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Operasyonlar");
        stage.show();


    }
    @FXML
    private void backToLoginPage() throws IOException {                                       //login ekranına dönecek komutu
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Dikkat");
        alert.setHeaderText(null);
        alert.setContentText("Devam etmek istiyor musun?");

        ButtonType evetButton = new ButtonType("Evet");
        ButtonType hayirButton = new ButtonType("Hayır");
        alert.getButtonTypes().setAll(evetButton, hayirButton);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == evetButton) {
            currentFamilyName = null;
            Stage stage = (Stage) scrollPane_familytree.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginScreen.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Uyarı");
            alert2.setHeaderText(null);
            alert2.setContentText("İşlem iptal edildi");
            alert2.showAndWait();
        }

    }

    @FXML
    public void esliciz(){
        Pane canvas = new Pane();
        human root = getRoot(currentFamilyName);

        TreeDrawer.nextX[0] = 50; // başlangıç pozisyonunu sıfırla

        TreeDrawer.drawTree(canvas, root, 50, 150,true);  // sadece 4 parametre: Pane, root, y, xSpacing

        scrollPane_familytree.setContent(canvas);
    }
    @FXML
    public void essizciz(){
        Pane canvas = new Pane();
        human root = getRoot(currentFamilyName);

        TreeDrawer.nextX[0] = 50; // başlangıç pozisyonunu sıfırla

        TreeDrawer.drawTree(canvas, root, 50, 150,false);  // sadece 4 parametre: Pane, root, y, xSpacing

        scrollPane_familytree.setContent(canvas);
    }

    private static class TreeDrawer {
        private static final double[] nextX = {50};  // başlangıç x pozisyonu


        public static double drawTree(Pane pane, human root, double y, double xSpacing,boolean iswithpartner) {
            if (root.childlist == null || root.childlist.isEmpty()) {
                // Yaprak düğüm (child yok), bu noktaya çiz
                double x = nextX[0];
                placePerson(pane, root, x, y,iswithpartner);
                nextX[0] += xSpacing;
                return x;
            }

            // Çocukları çiz ve ortalamayı hesapla
            List<Double> childXs = new ArrayList<>();
            for (human child : root.childlist) {
                double childX = drawTree(pane, child, y + 80, xSpacing,iswithpartner);
                childXs.add(childX);
            }

            // Bu kişiyi çocuklarının ortasına yerleştir
            double minX = Collections.min(childXs);
            double maxX = Collections.max(childXs);
            double x = (minX + maxX) / 2;

            placePerson(pane, root, x, y,iswithpartner);

            // Çocuklarla çizgi çiz
            for (double childX : childXs) {
                Line line = new Line(x, y + 15, childX, y + 80);
                pane.getChildren().add(line);
            }

            return x;
        }
        private static void placePerson(Pane pane, human person, double x, double y,boolean iswithpartner) {
            Label label=new Label();
            if (iswithpartner){
                label = new Label(person.getFullNameWithPartner());
            }else {
                label = new Label(person.getFullNameWithoutPartner());
            }
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