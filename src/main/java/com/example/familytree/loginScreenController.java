package com.example.familytree;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        if (searchScreenController.kullanilanHashTablosu!=null){searchScreenController.kullanilanHashTablosu=null;}
        String familyNameString = familyName.getText();
        if(isFamilyNameinList(familyNameString)){

            Stage stage = (Stage) familyName.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("treeOperations.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            mainScreenController.setCurrentFamilyName(familyNameString);

            treeOperationsController controller = fxmlLoader.getController();




            ////////////////////////////
            try {
                ObjectMapper mapper = new ObjectMapper();
                File folder = new File("src/main/java/Jsonlar/");
                File file123 = new File(folder, familyNameString + ".json");
                JsonNode rootNode = mapper.readTree(file123);
                JsonNode allMembers = rootNode.get("allMembers");

                if (allMembers != null && allMembers.size() != 0) {

                    try {
                        FamilyTree asd = Json_WriterandReader.readFamilyTree(familyNameString);
                        treeOperationsController.currentfamilyroot = asd.root;
                        FamilyTree.setRoot(familyNameString,asd.root);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            ///////////////////////////

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



            try {
                File folder = new File("src/main/java/Jsonlar/");
                File file123 = new File(folder, familyNameString + ".json");
                file123.createNewFile();
                String jsonContent = "{\n" +
                            "  \"familytreename\": \"" + familyNameString + "\",\n" +
                            "  \"allMembers\": []\n" +
                            "}";

                    FileWriter writer = new FileWriter(file123);
                    writer.write(jsonContent);
                    writer.close();


            } catch (IOException e) {
                e.printStackTrace();
            }





/*
            human root123=null;
            try {
                root123 =Json_WriterandReader.readFamilyTree(familyNameString);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            treeOperationsController.currentfamilyroot=root123;
            controller.setupTree();
*/
            treeOperationsController.currentfamilyroot=null;
            controller.setupTree();


            stage.setX(150);
            stage.setY(150);
            stage.setScene(scene);
            stage.show();
        }
    }
}
