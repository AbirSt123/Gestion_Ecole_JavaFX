package org.example.school_management.controllers.Dashboards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class AdminDashboard {

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
    private HBox root;

    @FXML
    private AnchorPane sideAnchorPane;


    @FXML
    private BorderPane mainBorderPane;

    @FXML
    void BtnLogout(ActionEvent event) {

    }

    @FXML
    void btnEtudiant(ActionEvent event) throws IOException {
//        AnchorPane view = FXMLLoader.load(getClass().getResource("/org/example/school_management/fxml/Etudiant/Etudiant.fxml"));
//        mainBorderPane.setCenter(view);
//        Parent root = null;
//        root = FXMLLoader.load(getClass().getResource("/org/example/school_management/fxml/Etudiant/Etudiant.fxml"));
//        mainBorderPane.setCenter(root);
    }

    @FXML
    void btnHome(ActionEvent event) {

    }

    @FXML
    void btnModule(ActionEvent event) {

    }

    @FXML
    void btnProf(ActionEvent event) {

    }

}