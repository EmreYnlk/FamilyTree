package com.example.familytree;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class treeOperationsController implements Initializable {
    @FXML
    private TreeView<human> treeViewfamilytree;
    @FXML
    private Label fathertextLabel,mothertextLabel,Upper_label_1, soyIsmeGoreKisi, left_bottom_label1, partnerLabel, fatherLabel, motherLabel,changeparentLabel,
            esLabel_1,cocuklarLabel_1,torunlarLabel_1;
    @FXML
    private AnchorPane rightAnchor;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private ToggleGroup cinsiyetRadioGrup;
    @FXML
    private RadioButton femaleRadioButton, maleRadioButton;
    @FXML
    private TextField nameTextfield, surnameTextfield;
    @FXML
    private ListView<human> grandkidsListView, kidsListview;
    @FXML
    private Button saveChangesButton, deletePersonButton, iptalBtn, soyAgaciCiz,duzenleButton;
    @FXML
    private ImageView partnerImage;
    @FXML
    private ChoiceBox<human> parentChoiceBox;
    private static human selectedperson;
    public static human currentfamilyroot;

    //   soy ağacından silinen kişiler json dan da silinmeli. veya kullanıcı kaydet dediği zaman mevcut soyağacının bilgilerini yeniden yazmalı.

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setupTree() {

        try {
            Json_WriterandReader.writeFamilyTree(mainScreenController.getCurrentFamilyName(),currentfamilyroot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        soyIsmeGoreKisi.setText(mainScreenController.getCurrentFamilyName()+" Ailesi");
        saveChangesButton.setVisible(false);
        deletePersonButton.setVisible(false);
        iptalBtn.setVisible(false);
        setEditable(false);
        rightAnchor.setVisible(false);
        human familytreeRoot = currentfamilyroot;
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


        Image maleIcon = new Image(getClass().getResource("/com/example/familytree/icons/male.png").toExternalForm());
        Image femaleIcon = new Image(getClass().getResource("/com/example/familytree/icons/female.png").toExternalForm());

        treeViewfamilytree.setCellFactory(tv -> new TreeCell<>() {
            @Override
            protected void updateItem(human item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.getFullNameWithoutPartner());

                    ImageView iconView;
                    if (item.cinsiyet == 'E') {
                        iconView = new ImageView(maleIcon);
                    } else {
                        iconView = new ImageView(femaleIcon);
                    }
                    iconView.setFitWidth(15);  // isteğe bağlı boyutlandırma
                    iconView.setFitHeight(15);
                    setGraphic(iconView);
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
        partnerImage.setVisible(false);
        saveChangesButton.setVisible(false);
        deletePersonButton.setVisible(false);
        iptalBtn.setVisible(false);
        setEditable(false);
        TreeItem<human> selectedItem = treeViewfamilytree.getSelectionModel().getSelectedItem();
        if (selectedItem != null && selectedItem.getValue() != null) {
            human selected = selectedItem.getValue();
            selectedperson = selected;   // sonradan kullanım için tutuyoruz.

            Upper_label_1.setText("Kişi Bilgileri");
            rightAnchor.setVisible(true);
            left_bottom_label1.setText(selectedperson.getFullNameWithoutPartner() + " Detayları");
            nameTextfield.setText(selectedperson.name);
            surnameTextfield.setText(selectedperson.surname);
            if (selectedperson.cinsiyet == 'E') {
                maleRadioButton.setSelected(true);
            } else {
                femaleRadioButton.setSelected(true);
            }

            if (selectedperson.partner == null) {
                partnerLabel.setText("Evli Değil");
            } else {
                partnerLabel.setText(selectedperson.partner.getFullNameWithoutPartner());
            }


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate date = LocalDate.parse(selectedperson.bornyear, formatter);
            birthDatePicker.setValue(date);
            kidsListview.getItems().clear();
            grandkidsListView.getItems().clear();
            for (human childrens_1 : selectedperson.childlist) {
                kidsListview.getItems().add(childrens_1);
                for (human grandchildrens_1 : childrens_1.childlist) {
                    grandkidsListView.getItems().add(grandchildrens_1);
                }
            }
            if (selectedperson.parent != null) {

                if (selectedperson.parent.cinsiyet == 'E') {
                    fatherLabel.setText(selectedperson.parent.getFullNameWithoutPartner());
                    motherLabel.setText(selectedperson.parent.partner.getFullNameWithoutPartner());
                } else {
                    motherLabel.setText(selectedperson.parent.getFullNameWithoutPartner());
                    fatherLabel.setText(selectedperson.parent.partner.getFullNameWithoutPartner());
                }
            } else {
                fatherLabel.setText("Bulunamadı");
                motherLabel.setText("Bulunamadı");
            }


            partnerInfoScreenController.setCurrentHumanBeing(selectedperson);

            parentChoiceBox.getItems().clear();


            recursiveFillParentCB(currentfamilyroot,1);

        }
    }

    private void recursiveFillParentCB(human temphuman,int temp){
        if (temp==1){
            if(temphuman!=selectedperson && !isFamilywith(selectedperson,temphuman)){
                parentChoiceBox.getItems().add(temphuman);
            }
            if (temphuman==selectedperson.parent){
                parentChoiceBox.getSelectionModel().select(temphuman);
            }

            for (human child : temphuman.childlist){
                recursiveFillParentCB(child,1);
            }

        } else {
            parentChoiceBox.getItems().add(temphuman);
            for (human child : temphuman.childlist){
                recursiveFillParentCB(child,2);
            }

        }




    }

    private boolean isFamilywith(human root, human target) {
        for (human child : root.childlist) {
            if (child == target) return true;
            if (isFamilywith(child, target)) return true;
        }
        return false;
    }

    @FXML
    private void backtomainpage() throws IOException {
        Stage stage = (Stage) treeViewfamilytree.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void backtologinscreen() throws IOException{
        Stage stage = (Stage) treeViewfamilytree.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    private void setEditable(boolean bool) {
        nameTextfield.setEditable(bool);
        surnameTextfield.setEditable(bool);
        femaleRadioButton.setMouseTransparent(!bool);
        maleRadioButton.setMouseTransparent(!bool);

        birthDatePicker.setMouseTransparent(!bool);
        birthDatePicker.setFocusTraversable(bool);
        partnerImage.setVisible(bool);

        parentChoiceBox.setVisible(bool);
        parentChoiceBox.setMouseTransparent(!bool);
        changeparentLabel.setVisible(bool);

        mothertextLabel.setVisible(!bool);
        fathertextLabel.setVisible(!bool);
        motherLabel.setVisible(!bool);
        fatherLabel.setVisible(!bool);
    }
    private void givealert(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("İmkansız");
        alert.setHeaderText(null);
        alert.setContentText("Eksik ya da Yanlış girişler yaptınız.Lütfen geçerli ifadeler giriniz");
        alert.showAndWait();
    }

    @FXML
    private void saveChanges() {
        if (nameTextfield.getText()==null&&nameTextfield.getText()==""){
            givealert();
            return;
        }

        if (surnameTextfield.getText()==null || surnameTextfield.getText()==""){
            givealert();
            return;
        }
        selectedperson.name=nameTextfield.getText();
        selectedperson.surname=surnameTextfield.getText();

        if (maleRadioButton.isSelected()){
            selectedperson.cinsiyet='E';
        }else if (femaleRadioButton.isSelected()){
            selectedperson.cinsiyet='K';
        }else {
            givealert();
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        if (birthDatePicker.getValue()==null){
            givealert();
            return;
        }
        LocalDate selectedDate = birthDatePicker.getValue();
        String bornyear = selectedDate.format(formatter);
        selectedperson.bornyear=bornyear;




        if (parentChoiceBox.getValue() != selectedperson.parent){

            selectedperson.parent.childlist.remove(selectedperson);
            selectedperson.parent = parentChoiceBox.getValue();
            parentChoiceBox.getValue().childlist.add(selectedperson);
        }








        iptalBtn.setVisible(false);
        saveChangesButton.setVisible(false);
        deletePersonButton.setVisible(false);

        setEditable(false);



        setupTree();

    }

    @FXML
    private void editButtonAction() {
        saveChangesButton.setVisible(true);
        deletePersonButton.setVisible(true);
        iptalBtn.setVisible(true);
        setEditable(true);
    }


    @FXML
    private void addPersontoTree(){
        rightAnchor.setVisible(true);
        parentChoiceBox.getItems().clear();
        nameTextfield.setText("İsim Giriniz");
        nameTextfield.setEditable(true);
        surnameTextfield.setText("Soyisim Giriniz");
        surnameTextfield.setEditable(true);
        left_bottom_label1.setText("Yeni Kişi Bilgilerini Giriniz");
        birthDatePicker.setEditable(true);
        birthDatePicker.setMouseTransparent(false);
        maleRadioButton.setSelected(false);
        maleRadioButton.setMouseTransparent(false);
        femaleRadioButton.setMouseTransparent(false);
        femaleRadioButton.setSelected(false);

        if (currentfamilyroot!=null){
            recursiveFillParentCB(currentfamilyroot,2);
            parentChoiceBox.setValue(currentfamilyroot);
            parentChoiceBox.setVisible(true);
            parentChoiceBox.setMouseTransparent(false);
        }else {
            parentChoiceBox.setVisible(false);
            parentChoiceBox.setMouseTransparent(true);
            changeparentLabel.setText("Bu kişi root olarak eklenecek. Bu kısımda değişiklik yapılamaz");
        }
        changeparentLabel.setVisible(true);

        kidsListview.setVisible(false);
        grandkidsListView.setVisible(false);

        partnerLabel.setVisible(false);

        mothertextLabel.setVisible(false);
        fathertextLabel.setVisible(false);
        motherLabel.setVisible(false);
        fatherLabel.setVisible(false);

        esLabel_1.setVisible(false);
        cocuklarLabel_1.setVisible(false);
        torunlarLabel_1.setVisible(false);


        treeViewfamilytree.setMouseTransparent(true);
        duzenleButton.setText("Kişiyi Ekle");

        duzenleButton.setOnMouseClicked(event -> {
            actionForAddingPersonToTree();
        });

        iptalBtn.setVisible(true);
        iptalBtn.setOnMouseClicked(event -> {
            actionForCancelingAdding();
        });



    }


    @FXML
    private void actionForAddingPersonToTree(){
            if (nameTextfield.getText()==null&&nameTextfield.getText()==""){
                givealert();
                return;
            }

            if (surnameTextfield.getText()==null || surnameTextfield.getText()==""){
                givealert();
                return;
            }

            String name = nameTextfield.getText();
            String surname = surnameTextfield.getText();
            char cinsiyet;
            if (maleRadioButton.isSelected()){
                cinsiyet='E';
            }else if (femaleRadioButton.isSelected()){
                cinsiyet='K';
            }else {
                givealert();
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            if (birthDatePicker.getValue()==null){
                givealert();
                return;
            }
            LocalDate selectedDate = birthDatePicker.getValue();
            String bornyear = selectedDate.format(formatter);

            human human12 = new human(name,surname,bornyear,cinsiyet);

        if (currentfamilyroot==null){
            FamilyTree.setRoot(mainScreenController.getCurrentFamilyName(),human12);

        }else {
            if (parentChoiceBox.getValue().partner==null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Olamaz");
                alert.setHeaderText(null);
                alert.setContentText("Soyağacında bir kişiye çocuk eklemek istiyorsanız önce o kişiye eş eklemelisiniz.");
                alert.showAndWait();
                return;
            }

            if(parentChoiceBox.getValue()!=null){
               human12.parent = parentChoiceBox.getValue();
               parentChoiceBox.getValue().childlist.add(human12);
            }else {
                givealert();
                return;
            }

        }



        easyAccess();
    }

    @FXML
    private void actionForCancelingAdding(){
        easyAccess();
    }

    private void easyAccess(){
        changeparentLabel.setText("Ebeveyni Değiştir");
        duzenleButton.setText("Düzenle");
        duzenleButton.setOnMouseClicked(event -> {
            editButtonAction();
        });
        treeViewfamilytree.setMouseTransparent(false);
        iptalBtn.setVisible(false);
        iptalBtn.setOnMouseClicked(event -> {
            iptalEt();
        });
        kidsListview.setVisible(true);
        grandkidsListView.setVisible(true);
        esLabel_1.setVisible(true);
        cocuklarLabel_1.setVisible(true);
        torunlarLabel_1.setVisible(true);
        partnerLabel.setVisible(true);


        setupTree();
    }


    @FXML
    private void iptalEt() {
        saveChangesButton.setVisible(false);
        deletePersonButton.setVisible(false);
        iptalBtn.setVisible(false);
        setEditable(false);  // Alanları tekrar pasif yap
        bringinformation();  // Seçili kişiyi yeniden yükle, yani geri al
    }

    @FXML
    private void deletePerson() {
        if (!isDeleteConfirmation()){
            return;
        }
        if (selectedperson==currentfamilyroot){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("İmkansız");
            alert.setHeaderText(null);
            alert.setContentText("Soyağacındaki ilk kişiyi silemezsiniz");
            alert.showAndWait();
            return;
        }

        if (selectedperson.partner!=null){
            selectedperson.partner=null;
        }
        recursiveDeletePerson(selectedperson);

        if (selectedperson.parent!=null){
            selectedperson.parent.childlist.remove(selectedperson);
        }


        selectedperson = null;
        setupTree();    // kişi silindikten sonra ağacı tekrar yükle
    }
    private void recursiveDeletePerson(human humans){
        for (human child : humans.childlist) {
            recursiveDeletePerson(child);
        }
        humans.childlist.clear();
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


    public static boolean isDeleteConfirmation() {
        final boolean[] result = new boolean[1]; // tek elemanlı dizi ile değer taşıyoruz

        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle("Onay");

        Label label = new Label("Silmek istediğinize emin misiniz?");
        Button yesButton = new Button("Evet (3)");
        yesButton.setDisable(true);
        Button noButton = new Button("Hayır");

        yesButton.setOnAction(e -> {
            result[0] = true;
            dialogStage.close();
        });

        noButton.setOnAction(e -> {
            result[0] = false;
            dialogStage.close();
        });

        VBox layout = new VBox(15, label, yesButton, noButton);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        Scene scene = new Scene(layout);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);

        int[] countdown = {3};
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            countdown[0]--;
            if (countdown[0] > 0) {
                yesButton.setText("Evet (" + countdown[0] + ")");
            } else {
                yesButton.setText("Evet");
                yesButton.setDisable(false);
            }
        }));
        timeline.setCycleCount(3);
        timeline.play();

        dialogStage.showAndWait();

        return result[0];
    }


}