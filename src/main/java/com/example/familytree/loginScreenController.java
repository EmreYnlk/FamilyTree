package com.example.familytree;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
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
            mainScreenController.setCurrentFamilyName(familyNameString);

            treeOperationsController controller = fxmlLoader.getController();




            ////////////////////////////
            human root123 =null;
            try {
                root123 =Json_WriterandReader.readFamilyTree(familyNameString);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            ///////////////////////////
            treeOperationsController.currentfamilyroot = root123;
            controller.setupTree();


            stage.setX(150);
            stage.setY(150);
            stage.setScene(scene);
            stage.show();



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
    private void createFamilyTree() throws IOException{                                   // burada bir soyadı alınıp o soyadına ait bir soyağacı sınıfından nesne oluşturulacak ve mainScreen e yönlendirilecek

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
            FamilyTree newtree = new FamilyTree(familyNameString);

            Stage stage = (Stage) familyName.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("treeOperations.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            mainScreenController.setCurrentFamilyName(familyNameString);

            treeOperationsController controller = fxmlLoader.getController();
            newtree.root = null;



            File folder = new File("src/main/java/Jsonlar/");
            File file123 = new File(folder, familyNameString + ".json");
            file123.createNewFile();



            try {
                human root123 =Json_WriterandReader.readFamilyTree(familyNameString);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            controller.setupTree();

            stage.setX(150);
            stage.setY(150);
            stage.setScene(scene);
            stage.show();
        }
    }
}
