package com.example.familytree;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


// turuncu kodu   D2A123
public class loginScreenController {
    @FXML
    private TextField familySurname;

    @FXML
    private void login(){
        String surname = familySurname.getText();
        // burada girilen soyismine göre soyağacı açılacak. açılan ekranda soyağacı gösterilecek ve düzenleme kısmı olacak
    }
    @FXML
    private void createFamilyTree(){
        // burada ise bir soyadı alınıp o soyadına ait bir soyağacı sınıfından nesne oluşturulacak ve mainScreen e yönlendirilecek

    }
}
