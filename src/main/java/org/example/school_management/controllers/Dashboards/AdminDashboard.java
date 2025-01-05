//package org.example.school_management.controllers.Dashboards;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.layout.AnchorPane;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//import java.net.URL;
//import java.util.ResourceBundle;
//
//public class AdminDashboard implements Initializable {
//
//    @FXML
//    private AnchorPane anchorEtudiant;
//
//    @FXML
//    private AnchorPane anchorHome;
//
//    @FXML
//    private AnchorPane anchorModules;
//
//    @FXML
//    private AnchorPane anchorProf;
//
//    @FXML
//    private AnchorPane anchorInscription;
//
//    @FXML
//    private Button btn_etudiant;
//
//    @FXML
//    private Button btn_home;
//
//    @FXML
//    private Button btn_logout;
//
//    @FXML
//    private Button btn_modules;
//
//    @FXML
//    private Button btn_profs;
//
//    @FXML
//    private Button btn_inscription;
//
//    @FXML
//    private AnchorPane sideAnchorPane;
//
//    @FXML
//    private AnchorPane InnerAnchor;
//
//    /**
//     * Hides all AnchorPanes
//     */
//    private void hideAllAnchors() {
//        anchorEtudiant.setVisible(false);
//        anchorProf.setVisible(false);
//        anchorModules.setVisible(false);
//        anchorHome.setVisible(false);
//        anchorInscription.setVisible(false);
//    }
//
//    @FXML
//    void BtnLogout(ActionEvent event) {
//        // Handle logout logic here
//    }
//
//    @FXML
//    void btnEtudiant(ActionEvent event) {
//        hideAllAnchors();
//        anchorEtudiant.setVisible(true);
//    }
//
//    @FXML
//    void btnHome(ActionEvent event) {
//        hideAllAnchors();
//        anchorHome.setVisible(true);
//    }
//
//    @FXML
//    void btnModule(ActionEvent event) {
//        hideAllAnchors();
//        anchorModules.setVisible(true);
//    }
//
//    @FXML
//    void btnProf(ActionEvent event) {
//        hideAllAnchors();
//        anchorProf.setVisible(true);
//    }
//
//    @FXML
//    void btnInscription(ActionEvent event) {
//        hideAllAnchors();
//        anchorInscription.setVisible(true);
//    }
//
//    /**
//     * Called automatically when the FXML is loaded
//     */
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        // Set default visibility for anchorHome
//        hideAllAnchors();
//        anchorHome.setVisible(true);
//    }
//
//
//    /**
//     * CRUD
//     */
//    @FXML
//    void btnAdd(ActionEvent event) throws IOException {
//        // Load the Add Student dialog FXML
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/school_management/fxml/Etudiant/AddStudentDialog.fxml"));
//        AnchorPane page = loader.load();
//
//        // Create a new stage for the dialog
//        Stage dialogStage = new Stage();
//        dialogStage.setTitle("Add Student");
//        dialogStage.initOwner(anchorEtudiant.getScene().getWindow());  // Make the dialog modal to the main window
//        dialogStage.setScene(new Scene(page));
//        dialogStage.showAndWait();
//    }
//}
package org.example.school_management.controllers.Dashboards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class AdminDashboard {

    @FXML
    private AnchorPane contentAnchorPane;

    @FXML
    public void btnHome(ActionEvent event) {
        loadContent("/org/example/school_management/fxml/Home/Home.fxml");
    }

    @FXML
    public void btnEtudiant(ActionEvent event) {
        loadContent("/org/example/school_management/fxml/Etudiant/Etudiant.fxml");
    }

    @FXML
    public void btnProf(ActionEvent event) {
        loadContent("/org/example/school_management/fxml/Profs/Prof.fxml");
    }

    @FXML
    public void btnModule(ActionEvent event) {
        loadContent("/org/example/school_management/fxml/Modules/Module.fxml");
    }

    @FXML
    public void btnInscription(ActionEvent event) {
        loadContent("/org/example/school_management/fxml/Inscription/Inscription.fxml");
    }

//    @FXML
//    public void BtnLogout(ActionEvent event) {
//        Stage stage = (Stage) contentAnchorPane.getScene().getWindow();
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/school_management/views/Login.fxml"));
//            Scene scene = new Scene(loader.load());
//            stage.setScene(scene);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void loadContent(String fxml) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(fxml));
            contentAnchorPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     //     * CRUD
     //     */
//    @FXML
//    void btnAdd(ActionEvent event) throws IOException {
//        // Load the Add Student dialog FXML
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/school_management/fxml/Etudiant/AddStudentDialog.fxml"));
//        AnchorPane page = loader.load();
//
//        // Create a new stage for the dialog
//        Stage dialogStage = new Stage();
//        dialogStage.setTitle("Add Student");
//        dialogStage.initOwner(anchorEtudiant.getScene().getWindow());  // Make the dialog modal to the main window
//        dialogStage.setScene(new Scene(page));
//        dialogStage.showAndWait();
//    }
}
