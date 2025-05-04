package com.example.familytree;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.familytree.FamilyTree.isFamilyNameinList;


public class loginScreenController {
    @FXML
    private TextField familyName;

    @FXML
    private void login() throws IOException {
        String familyNameString = familyName.getText();
        if(isFamilyNameinList(familyNameString)){

            Stage stage = (Stage) familyName.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("treeOperations.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setX(150);
            stage.setY(150);
            stage.setScene(scene);
            stage.show();

            mainScreenController.setCurrentFamilyName(familyNameString);        // şu anki aile adini kullanmamız için anaekrancontroller a yazıyor
        } else if (familyNameString=="") {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Eyvah");
            alert.setHeaderText(null);
            alert.setContentText("Hiçbir Şey Girmediniz.Lütfen Bir Değer Giriniz");
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Uyarı");
            alert.setHeaderText(null);
            alert.setContentText("Girdiğiniz Soyağacı Sistemde Bulunamadı.Bu Soyağacı Adını Kullanarak Yeni Bir Soyağacı Oluşturabilirsiniz. ");
            alert.showAndWait();
        }

    }
    @FXML
    private void createFamilyTree(){                                   // burada bir soyadı alınıp o soyadına ait bir soyağacı sınıfından nesne oluşturulacak ve mainScreen e yönlendirilecek

        String familyNameString = familyName.getText();
        if(isFamilyNameinList(familyNameString)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Uyarı");
            alert.setHeaderText(null);
            alert.setContentText("Girdiğiniz Soyağacı Zaten Sistemde Bulunmaktadir.Bu Soyağacı Adını Kullanarak Yeni Bir Soyağacı Oluşturamazsınız. ");
            alert.showAndWait();
        }else if (familyNameString=="") {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Eyvah");
            alert.setHeaderText(null);
            alert.setContentText("Hiçbir Şey Girmediniz.Lütfen Bir Değer Giriniz");
            alert.showAndWait();
        }
        else {
            System.out.println("Yapılmadı daha sonra gel . girilen: " +familyNameString);

            mainScreenController.setCurrentFamilyName(familyNameString);
        }
    }
}
