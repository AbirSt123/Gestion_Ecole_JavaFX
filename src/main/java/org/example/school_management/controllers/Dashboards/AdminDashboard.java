package org.example.school_management.controllers.Dashboards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboard implements Initializable {

    @FXML
    private AnchorPane anchorEtudiant;

    @FXML
    private AnchorPane anchorHome;

    @FXML
    private AnchorPane anchorModules;

    @FXML
    private AnchorPane anchorProf;

    @FXML
    private AnchorPane anchorInscription;

    @FXML
    private Button btn_etudiant;

    @FXML
    private Button btn_home;

    @FXML
    private Button btn_logout;

    @FXML
    private Button btn_modules;

    @FXML
    private Button btn_profs;

    @FXML
    private Button btn_inscription;

    @FXML
    private AnchorPane sideAnchorPane;

    @FXML
    private AnchorPane InnerAnchor;

    /**
     * Hides all AnchorPanes
     */
    private void hideAllAnchors() {
        anchorEtudiant.setVisible(false);
        anchorProf.setVisible(false);
        anchorModules.setVisible(false);
        anchorHome.setVisible(false);
        anchorInscription.setVisible(false);
    }

    @FXML
    void BtnLogout(ActionEvent event) {
        // Handle logout logic here
    }

    @FXML
    void btnEtudiant(ActionEvent event) {
        hideAllAnchors();
        anchorEtudiant.setVisible(true);
    }

    @FXML
    void btnHome(ActionEvent event) {
        hideAllAnchors();
        anchorHome.setVisible(true);
    }

    @FXML
    void btnModule(ActionEvent event) {
        hideAllAnchors();
        anchorModules.setVisible(true);
    }

    @FXML
    void btnProf(ActionEvent event) {
        hideAllAnchors();
        anchorProf.setVisible(true);
    }

    @FXML
    void btnInscription(ActionEvent event) {
        hideAllAnchors();
        anchorInscription.setVisible(true);
    }

    /**
     * Called automatically when the FXML is loaded
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set default visibility for anchorHome
        hideAllAnchors();
        anchorHome.setVisible(true);
    }


    /**
     * CRUD
     */
    @FXML
    void btnAdd(ActionEvent event) throws IOException {
        // Load the Add Student dialog FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/school_management/fxml/Etudiant/AddStudentDialog.fxml"));
        AnchorPane page = loader.load();

        // Create a new stage for the dialog
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Add Student");
        dialogStage.initOwner(anchorEtudiant.getScene().getWindow());  // Make the dialog modal to the main window
        dialogStage.setScene(new Scene(page));
        dialogStage.showAndWait();
    }
}
