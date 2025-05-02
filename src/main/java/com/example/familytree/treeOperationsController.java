package com.example.familytree;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class treeOperationsController implements Initializable {
    @FXML
    private TreeView<human> treeViewfamilytree;
    @FXML
    private Label Upper_label_1,Upper_label_2,left_bottom_label1,partnerLabel;
    @FXML
    private AnchorPane rightAnchor;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private ToggleGroup cinsiyetRadioGrup;
    @FXML
    private RadioButton femaleRadioButton,maleRadioButton;
    @FXML
    private TextField nameLabel,surnameLabel;
    @FXML
    private ListView<human> grandkidsListView,kidsListview;
    @FXML
    private ChoiceBox<human> fatherChoicebox,motherChoicebox;
    @FXML
    private Button saveChangesButton,deletePersonButton;
    @FXML
    private ImageView partnerImage;

    private static human selectedperson;

    //   saveChanges()  yazılmadı
    //   bringinformation()  tam olarak tamamlanmadı anne ve baba bilgisi gerekiyor-- bunu yaparken .parent değiştirilebilir olacak  .parent.partner değiştirilemez olacak.
    //   soy ağacına yeni kişi eklenemiyor.     ve kişi silinemiyor

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rightAnchor.setVisible(false);




        human familytreeRoot = FamilyTree.getRoot(mainScreenController.getCurrentFamilyName());
        treeViewfamilytree.setStyle("-fx-font-size: 18px;");
        if (familytreeRoot == null) {
            TreeItem<human> emptyItem = new TreeItem<>(null);
            treeViewfamilytree.setRoot(emptyItem);
        } else {
            TreeItem<human> rootItem = new TreeItem<>(familytreeRoot);
            treeViewfamilytree.setRoot(rootItem);
            fillTreeView(familytreeRoot, rootItem);
        }



        kidsListview.setCellFactory(param -> new ListCell<human>() {
            @Override
            protected void updateItem(human item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getFullNameWithoutPartner());
            }
        });

        grandkidsListView.setCellFactory(param -> new ListCell<human>() {
            @Override
            protected void updateItem(human item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getFullNameWithoutPartner());
            }
        });





        treeViewfamilytree.setCellFactory(tv -> new TreeCell<>() {
            @Override
            protected void updateItem(human item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);  // Boş öğe ya da null verisi varsa, yazıyı kaldır
                } else {
                    setText(item.getFullNameWithEmoji());  // Eğer öğe varsa, isim ve emoji göster
                }
            }
        });


    }

    private void fillTreeView(human currentHuman, TreeItem<human> parentItem) {

        for (human child : currentHuman.childlist) {
            TreeItem<human> childItem = new TreeItem<>(child);
            parentItem.getChildren().add(childItem);
            fillTreeView(child, childItem);
        }
    }


    @FXML
    public void bringinformation() {
        setEditable(false);
        TreeItem<human> selectedItem = treeViewfamilytree.getSelectionModel().getSelectedItem();
        if (selectedItem != null && selectedItem.getValue() != null) {
            human selected = selectedItem.getValue();
            selectedperson=selected;   // sonradan kullanım için tutuyoruz.

            Upper_label_1.setText("Kişi Bilgileri");
            rightAnchor.setVisible(true);
            left_bottom_label1.setText(selected.getFullNameWithoutPartner()+" 'nın Detayları");
            nameLabel.setText(selected.name);
            surnameLabel.setText(selected.surname);
            if (selected.cinsiyet=='E'){
                maleRadioButton.setSelected(true);
            }else {
                femaleRadioButton.setSelected(true);
            }
            if (selected.partner==null){
                partnerLabel.setText("Evli Değil");
            }else {
                partnerLabel.setText(selected.partner.getFullNameWithoutPartner());
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate date = LocalDate.parse(selected.bornyear, formatter);
            birthDatePicker.setValue(date);
            kidsListview.getItems().clear();
            grandkidsListView.getItems().clear();
            for(human childrens_1 : selected.childlist){
                kidsListview.getItems().add(childrens_1);
                for (human grandchildrens_1 : childrens_1.childlist){
                    grandkidsListView.getItems().add(grandchildrens_1);
                }
            }




            partnerInfoScreenController.setCurrentHumanBeing(selected);
        }
    }

    @FXML
    private void backtomainpage() throws IOException {
        Stage stage = (Stage) treeViewfamilytree.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }



    private void setEditable(boolean bool){
    nameLabel.setEditable(bool);
    surnameLabel.setEditable(bool);
    femaleRadioButton.setMouseTransparent(!bool);
    maleRadioButton.setMouseTransparent(!bool);


    birthDatePicker.setMouseTransparent(!bool);
    birthDatePicker.setFocusTraversable(bool);
    fatherChoicebox.setMouseTransparent(!bool);
    motherChoicebox.setMouseTransparent(!bool);
    partnerImage.setVisible(bool);
    }

    @FXML
    private void saveChanges(){   // değişiklikleri kaydetme butonu ----- eklenecek eklenmedi---  selectedperson  kullanarak yazılacak.
        saveChangesButton.setVisible(false);
        deletePersonButton.setVisible(false);
        setEditable(false);
        motherChoicebox.setMouseTransparent(true);
        fatherChoicebox.setMouseTransparent(true);

    }
    @FXML
    private void changeInformations(){
        saveChangesButton.setVisible(true);
        deletePersonButton.setVisible(true);
        setEditable(true);
        if (selectedperson.parent!=null){
            if (selectedperson.parent.cinsiyet=='E'){
                motherChoicebox.setMouseTransparent(true);
                fatherChoicebox.setMouseTransparent(false);
            }else {
                fatherChoicebox.setMouseTransparent(true);
                motherChoicebox.setMouseTransparent(false);
            }}

    }
    @FXML
    private void deletePerson(){

    }








    @FXML
    private void changePartnerinfo() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("partnerInfoScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        partnerInfoScreenController controller = fxmlLoader.getController();
        controller.getingreadyinfo();

        controller.setOnSaveCallback(() -> {
            bringinformation();
        });

        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.show();
    }

}
