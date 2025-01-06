package org.example.school_management.controllers.Etudiant;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AfficherEtudiant {

    @FXML
    private AnchorPane etudiantsPane;

    @FXML
    void btnAdd(ActionEvent event) throws IOException {
         //Load the Add Student dialog FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/school_management/fxml/Etudiant/AddStudentDialog.fxml"));
        AnchorPane page = loader.load();

        // Create a new stage for the dialog
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Add Student");
        dialogStage.initOwner(etudiantsPane.getScene().getWindow());  // Make the dialog modal to the main window
        dialogStage.setScene(new Scene(page));
        dialogStage.showAndWait();

    }

    /*
    Afficher les etudiants
     */
    

}

