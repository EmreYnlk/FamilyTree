package com.example.familytree;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class treeOperationsController {
    @FXML
    private TextField operationTextfield;

    @FXML
    private void removalwithname(){}

    @FXML
    private void removalwithid(){
        operationTextfield.setPromptText("Çıkarılacak id");
        operationTextfield.setVisible(true);
    }

    @FXML
    private void personadding(){}

    @FXML
    private void searchwithname(){
        operationTextfield.setPromptText("Aranacak id");
        operationTextfield.setVisible(true);
    }
    @FXML
    private void searchwithid(){}

    @FXML
    private void findingleafs(){}



}
