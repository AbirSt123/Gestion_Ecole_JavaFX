package org.example.school_management.controllers.Dashboards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

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
}
