package com.example.familytree;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class searchScreenController {
    static HashOperations kullanilanHashTablosu;
    @FXML
    private TextField personidField;

    public void addAllPersonalIdsToHash(human root, HashOperations hashTablo) {
        if (root == null) return;
        hashTablo.ekle(root);
        if (root.partner != null) {
            hashTablo.ekle(root.partner);
        }
        for (human child : root.childlist) {
            addAllPersonalIdsToHash(child, hashTablo);
        }
    }

    @FXML
    void Search(MouseEvent event) {
        String textfieldinput =personidField.getText();
        if(textfieldinput.isEmpty() || !textfieldinput.matches("\\d+")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Eyvah");
            alert.setHeaderText(null);
            alert.setContentText("Hatalı Tuşlama Yaptınız Tekrar Deneyiniz");
            alert.showAndWait();
            return;
        }
        double sorgulanacakpersonid = Double.parseDouble(textfieldinput);

        if (kullanilanHashTablosu==null){
            HashOperations hashTablo = new HashOperations(10);
            addAllPersonalIdsToHash(treeOperationsController.currentfamilyroot,hashTablo);
            kullanilanHashTablosu = hashTablo;
        }

        if (!kullanilanHashTablosu.aravarmi(sorgulanacakpersonid)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Eyvah");
            alert.setHeaderText(null);
            alert.setContentText("Öyle Bir Kişi Soyağacında Bulunamadı");
            alert.showAndWait();
            return;
        }

        human bulunankisi = kullanilanHashTablosu.ara(sorgulanacakpersonid);
        String cinsiyet = (bulunankisi.cinsiyet=='E') ? "Erkek"  : "Kadın";
        String bulunankisitext = "İsim: "+bulunankisi.name + "\nSoyisim: "+bulunankisi.surname
                +"\nCinsiyeti : "+cinsiyet + "\nDoğum Tarihi : "+bulunankisi.bornyear;

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bulundu");
        alert.setHeaderText(null);
        alert.setContentText(bulunankisitext);
        alert.showAndWait();

    }

}
