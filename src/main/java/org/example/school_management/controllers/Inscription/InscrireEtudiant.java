package org.example.school_management.controllers.Inscription;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import org.example.school_management.DAO.EtudDAOImp;
import org.example.school_management.DAO.InscriptionDAOImp;
import org.example.school_management.DAO.ModuleDAO;
import org.example.school_management.DAO.ModuleDAOImp;
import org.example.school_management.entities.Etudiant;
import org.example.school_management.entities.Modules;

import java.util.List;

public class InscrireEtudiant {

    private InscriptionDAOImp inscriptionDAO = new InscriptionDAOImp();

    @FXML
    private ListView<Etudiant> ListEtu;

    @FXML
    private ComboBox<Modules> ListModules;

    private EtudDAOImp etudiantDAO = new EtudDAOImp();
    private ModuleDAO modulesDAO = new ModuleDAOImp();

    @FXML
    public void initialize() {
        List<Etudiant> studentsList = etudiantDAO.afficherEtudiants();
        ListEtu.getItems().setAll(studentsList);

        ListEtu.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Customize how Etudiant objects are displayed in the ListView
        ListEtu.setCellFactory(param -> new javafx.scene.control.ListCell<Etudiant>() {
            @Override
            protected void updateItem(Etudiant etudiant, boolean empty) {
                super.updateItem(etudiant, empty);
                if (empty || etudiant == null) {
                    setText(null);
                } else {
                    setText(etudiant.getNom() + " " + etudiant.getPrenom());
                }
            }
        });

        // Populate the ComboBox for modules (Modules objects)
        List<Modules> modulesList = modulesDAO.afficherModules();
        ListModules.getItems().setAll(modulesList);

        // Customize how Modules objects are displayed in the ComboBox
        ListModules.setCellFactory(param -> new javafx.scene.control.ListCell<Modules>() {
            @Override
            protected void updateItem(Modules module, boolean empty) {
                super.updateItem(module, empty);
                if (empty || module == null) {
                    setText(null);
                } else {
                    setText(module.getNomModule()); // Display module's name
                }
            }
        });

        ListModules.setButtonCell(new javafx.scene.control.ListCell<Modules>() {
            @Override
            protected void updateItem(Modules module, boolean empty) {
                super.updateItem(module, empty);
                if (empty || module == null) {
                    setText(null);
                } else {
                    setText(module.getNomModule()); // Display module's name in the ComboBox button
                }
            }
        });
    }




    @FXML
    public void handleInscrireEtudiant(ActionEvent event) {
        try {
            Modules selectedModule = ListModules.getSelectionModel().getSelectedItem();
            List<Etudiant> selectedEtudiants = ListEtu.getSelectionModel().getSelectedItems();

            if (selectedModule == null || selectedEtudiants.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Selection Error", "Veuillez sélectionner un module et au moins un étudiant.");
                return;
            }

            for (Etudiant etudiant : selectedEtudiants) {
                inscriptionDAO.registerStudentToModule(etudiant, selectedModule);
            }

            showAlert(Alert.AlertType.INFORMATION, "Registration Successful", selectedEtudiants.size() + " student(s) have been successfully registered to module " + selectedModule.getNomModule() + ".");


        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "An error occurred while registering the students.");
        }

    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
