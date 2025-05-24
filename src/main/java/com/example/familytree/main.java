package com.example.familytree;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        main_treerandomizer.begining.makecomplexfamilytree(8,3,"hazir2");



        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("loginScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hoşgeldiniz");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {launch();}


}