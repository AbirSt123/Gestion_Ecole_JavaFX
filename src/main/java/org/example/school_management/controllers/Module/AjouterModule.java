package org.example.school_management.controllers.Module;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import org.example.school_management.DAO.ModuleDAOImp;
import org.example.school_management.DAO.ProfDAOImp;
import org.example.school_management.entities.Professeur;
import org.example.school_management.entities.Modules;

import java.util.List;

public class AjouterModule {

    @FXML
    private TextField codeField;

    @FXML
    private ComboBox<Professeur> comboField;

    @FXML
    private TextField nomField;

    private List<Professeur> professors; // Store professors to retrieve IDs later

    @FXML
    public void initialize() {
        // Load professors into the ComboBox
        ProfDAOImp professeurDAO = new ProfDAOImp();
        professors = professeurDAO.afficherProfesseurs();

        // Populate ComboBox with Professeur objects
        comboField.getItems().addAll(professors);

        // Set a custom cell factory to display "nom prenom" in the ComboBox
        comboField.setCellFactory(param -> new ListCell<Professeur>() {
            @Override
            protected void updateItem(Professeur professor, boolean empty) {
                super.updateItem(professor, empty);
                if (empty || professor == null) {
                    setText(null);
                } else {
                    setText(professor.getNom() + " " + professor.getPrenom());
                }
            }
        });

        // Set how the selected item appears in the ComboBox
        comboField.setButtonCell(new ListCell<Professeur>() {
            @Override
            protected void updateItem(Professeur professor, boolean empty) {
                super.updateItem(professor, empty);
                if (empty || professor == null) {
                    setText(null);
                } else {
                    setText(professor.getNom() + " " + professor.getPrenom());
                }
            }
        });
    }


    @FXML
    void handleAjouterModule(ActionEvent event) {
        // Retrieve data from fields
        String nomModule = nomField.getText();
        String codeModule = codeField.getText();
        Professeur selectedProfesseur = comboField.getValue(); // Get selected professor

        // Validate inputs
        if (nomModule.isEmpty() || codeModule.isEmpty() || selectedProfesseur == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill all fields and select a professor.");
            return;
        }

        // Create a Module object
        Modules module = new Modules();
        module.setNomModule(nomModule);
        module.setCodeModule(codeModule);
        module.setProfesseurId(selectedProfesseur.getId()); // Set professor ID directly from the selected professor

        // Add the module to the database
        ModuleDAOImp moduleDAO = new ModuleDAOImp();
        moduleDAO.ajouterModule(module);

        // Show success message
        showAlert(Alert.AlertType.INFORMATION, "Success", "Module added successfully.");

        // Clear fields
        clearFields();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Utility method to clear input fields
    private void clearFields() {
        nomField.clear();
        codeField.clear();
        comboField.setValue(null);
    }

}
