package org.example.school_management.controllers.Dashboards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfesseurDashboard {
    @FXML
    private AnchorPane contentAnchorPane;

    @FXML
    public void btnHome(ActionEvent event) {
        loadContent("/org/example/school_management/fxml/Home/Home.fxml");
    }

    @FXML
    public void btnEtudiant(ActionEvent event) {
        loadContent("/org/example/school_management/fxml/ProfEtu/ProfEtu.fxml");
    }


    @FXML
    public void btnModule(ActionEvent event) {
        loadContent("/org/example/school_management/fxml/ProfEtu/ProfModule.fxml");
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
//    @FXML
//    public void BtnLogout(ActionEvent event) {
//        Stage stage = (Stage) contentAnchorPane.getScene().getWindow();
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/school_management/fxml/sign-in.fxml"));
//            Scene scene = new Scene(loader.load());
//            stage.setScene(scene);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


}
