package com.example.familytree;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

public class partnerInfoScreenController {
        private static human currentHumanBeing;
        @FXML
        private DatePicker partnerbdateDatepicker;

        @FXML
        private TextField partnernameTextfield;

        @FXML
        private TextField partnersurnameTextfield;

        private Runnable onSaveCallback;

        public void setOnSaveCallback(Runnable callback) {
        this.onSaveCallback = callback;
        }

        @FXML
        void dontsaveandlogout(MouseEvent event) throws IOException {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Dikkat");
            alert.setHeaderText(null);
            alert.setContentText("İşlemler kaydedilmeyecek.Devam etmek istiyor musunuz?");

            ButtonType evetButton = new ButtonType("Evet");
            ButtonType hayirButton = new ButtonType("Hayır");
            alert.getButtonTypes().setAll(evetButton, hayirButton);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == evetButton) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
            }
        }

        @FXML
        void saveandlogout(MouseEvent event) throws IOException{
            if (currentHumanBeing.partner==null && (partnernameTextfield.getText().isEmpty()||partnersurnameTextfield.getText().isEmpty() || partnerbdateDatepicker.getValue() == null)){
                gettingmessage("Uyarı","Partneri Olmayan Birine Partner Eklerken Hiçbir Yer Boş Bırakılamaz");
                return;
            }else if (!Objects.equals(partnersurnameTextfield.getText(), currentHumanBeing.surname)){
                gettingmessage("Uyarı","Partner Soyadları Farklı olamaz");
                return;
            }else {
                // burada o arkadaş için bir partner oluşturacağız. burası eksik
                String partnername= partnernameTextfield.getText();
                String partnersurname = partnersurnameTextfield.getText();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate selectedDate = partnerbdateDatepicker.getValue();
                String partnerbornyear = selectedDate.format(formatter);
                char maleorfemale;
                if (currentHumanBeing.cinsiyet=='E') {
                    maleorfemale = 'K';
                }else {
                    maleorfemale = 'E';
                }

                human partnermaking = new human(partnername,partnersurname,partnerbornyear,maleorfemale);
                partnermaking.partner = currentHumanBeing;
                currentHumanBeing.partner = partnermaking;


            }


            String partnername =partnernameTextfield.getText();

            if (!Objects.equals(partnersurnameTextfield.getText(), currentHumanBeing.surname)){
                gettingmessage("Uyarı","Partner Soyadları Farklı olamaz");
                return;
            }


            if (Objects.equals(partnername, "")){
                gettingmessage("Uyarı","isim Boş Bırakılmamalı");
                return;
            }
            currentHumanBeing.partner.name = partnername;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate selectedDate = partnerbdateDatepicker.getValue();


            if (selectedDate==null) {
                gettingmessage("Uyarı","Tarih Seçim İşleminde Hata.Tekrar Deneyin");
                return;
            } else {
                String bornyear = selectedDate.format(formatter);
                currentHumanBeing.partner.bornyear = bornyear;

            }


            if (onSaveCallback != null) {
                onSaveCallback.run();
            }


            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
        public void getingreadyinfo(){
            if (currentHumanBeing.partner!=null){
                partnersurnameTextfield.setEditable(false);
                partnernameTextfield.setText(currentHumanBeing.partner.name);
                partnersurnameTextfield.setText(currentHumanBeing.partner.surname);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate date = LocalDate.parse(currentHumanBeing.partner.getBornyear(), formatter);

                partnerbdateDatepicker.setValue(date);
            }

        }

        private void gettingmessage(String Title,String Content){
            Alert alert3 = new Alert(Alert.AlertType.WARNING);
            alert3.setTitle(Title);
            alert3.setHeaderText(null);
            alert3.setContentText(Content);
            alert3.showAndWait();
        }


    public static human getCurrentHumanBeing() {
        return currentHumanBeing;
    }

    public static void setCurrentHumanBeing(human currentHumanBeing) {
        partnerInfoScreenController.currentHumanBeing = currentHumanBeing;
    }
}

