package com.example.familytree;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;

import java.awt.*;

public class mainScreenController {
    @FXML
    private ScrollPane scrollPane_familytree;

    @FXML
    private void ciz(){
        // bu komut deneme komutudur sonradan loginScreenController kısmına eklenecek

        Pane familyPane = new Pane(); // Tüm soy ağacı burada olacak
        scrollPane_familytree.setContent(familyPane);
        // Bir kişiyi ekleyelim
        Label personLabel = new Label("Hasan");
        personLabel.setLayoutX(200);
        personLabel.setLayoutY(50);
        familyPane.getChildren().add(personLabel);

// Bir çocuk ekleyelim
        Label childLabel = new Label("Ayşe");
        childLabel.setLayoutX(150);
        childLabel.setLayoutY(150);
        familyPane.getChildren().add(childLabel);

// Aralarına çizgi çizelim
        Line line = new Line(210, 70, 160, 150); // Başlangıç X,Y ve Bitiş X,Y
        familyPane.getChildren().add(line);

    }

}