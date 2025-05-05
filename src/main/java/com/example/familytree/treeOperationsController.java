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
    private Label Upper_label_1, Upper_label_2, left_bottom_label1, partnerLabel, fatherLabel, motherLabel;
    @FXML
    private AnchorPane rightAnchor;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private ToggleGroup cinsiyetRadioGrup;
    @FXML
    private RadioButton femaleRadioButton, maleRadioButton;
    @FXML
    private TextField nameLabel, surnameLabel;
    @FXML
    private ListView<human> grandkidsListView, kidsListview;
    @FXML
    private Button saveChangesButton, deletePersonButton, iptalBtn, soyAgaciCiz;
    @FXML
    private ImageView partnerImage;
    private static human selectedperson;
    //   saveChanges()  yazılmadı
    //   bringinformation()  tam olarak tamamlanmadı anne ve baba bilgisi gerekiyor-- bunu yaparken .parent değiştirilebilir olacak  .parent.partner değiştirilemez olacak.
    //   soy ağacına yeni kişi eklenemiyor.     ve kişi silinemiyor

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTree();
    }

    public void setupTree() {

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
        setEditable(false);
        TreeItem<human> selectedItem = treeViewfamilytree.getSelectionModel().getSelectedItem();
        if (selectedItem != null && selectedItem.getValue() != null) {
            human selected = selectedItem.getValue();
            selectedperson = selected;   // sonradan kullanım için tutuyoruz.

            Upper_label_1.setText("Kişi Bilgileri");
            rightAnchor.setVisible(true);
            left_bottom_label1.setText(selectedperson.getFullNameWithoutPartner() + " Detayları");
            nameLabel.setText(selectedperson.name);
            surnameLabel.setText(selectedperson.surname);
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

    private void setEditable(boolean bool) {
        nameLabel.setEditable(bool);
        surnameLabel.setEditable(bool);
        femaleRadioButton.setMouseTransparent(!bool);
        maleRadioButton.setMouseTransparent(!bool);


        birthDatePicker.setMouseTransparent(!bool);
        birthDatePicker.setFocusTraversable(bool);
        partnerImage.setVisible(bool);
    }

    @FXML
    private void saveChanges() {   // değişiklikleri kaydetme butonu ----- eklenecek eklenmedi---  selectedperson  kullanarak yazılacak.
        saveChangesButton.setVisible(false);
        deletePersonButton.setVisible(false);
        setEditable(false);

    }

    @FXML
    private void changeInformations() {
        saveChangesButton.setVisible(true);
        deletePersonButton.setVisible(true);
        iptalBtn.setVisible(true);
        setEditable(true);
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
        //buraya silinme komutları girilecek;






        System.out.println("Silindi...........");
        setupTree();    // kişi silindikten sonra ağacı tekrar yükle
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

