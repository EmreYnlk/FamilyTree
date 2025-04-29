package com.example.familytree;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import static com.example.familytree.FamilyTree.isFamilyNameinList;

// turuncu kodu   D2A123

public class loginScreenController {
    @FXML
    private TextField familyName;

    @FXML
    private void login() throws IOException {
        String familyNameString = familyName.getText();
        if(isFamilyNameinList(familyNameString)){

            Stage stage = (Stage) familyName.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainScreen.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();

            mainScreenController.setCurrentFamilyName(familyNameString);        // şu anki aile adini kullanmamız için anaekrancontroller a yazıyor
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Uyarı");
            alert.setHeaderText("Soyadı Geçersiz");
            alert.showAndWait();
        }

    }
    @FXML
    private void createFamilyTree(){
        // burada bir soyadı alınıp o soyadına ait bir soyağacı sınıfından nesne oluşturulacak ve mainScreen e yönlendirilecek

    }
}
