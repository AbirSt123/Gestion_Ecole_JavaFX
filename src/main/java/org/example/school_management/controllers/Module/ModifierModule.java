package org.example.school_management.controllers.Module;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.example.school_management.DAO.ModuleDAOImp;
import org.example.school_management.DAO.ProfDAOImp;
import org.example.school_management.entities.Modules;
import org.example.school_management.entities.Professeur;
import javafx.collections.FXCollections;


import java.sql.SQLException;

public class ModifierModule {

    @FXML
    private TextField codeField;

    @FXML
    private ComboBox<Professeur> comboField;

    @FXML
    private TextField nomField;
    private Modules currentModule;

    public void initData(Modules module) throws SQLException {
        // Store the current module in a class field to keep it for further use
        this.currentModule = module;

        // Populate the fields with the current module's data
        nomField.setText(module.getNomModule());  // Set the module name in the 'nomField' text field
        codeField.setText(module.getCodeModule());  // Set the module code in the 'codeField' text field

        // Assuming ProfDAOImp is used to fetch professors
        ProfDAOImp professeurDAO = new ProfDAOImp();

        // Fetch the list of all professors and populate the ComboBox with the professors
        comboField.setItems(FXCollections.observableList(professeurDAO.afficherProfesseurs()));
        comboField.setConverter(new StringConverter<>() {
            @Override
            public String toString(Professeur professeur) {
                return professeur != null ? professeur.getNom() + " " + professeur.getPrenom() : "";
            }

            @Override
            public Professeur fromString(String string) {
                return comboField.getItems()
                        .stream()
                        .filter(prof -> (prof.getNom() + " " + prof.getPrenom()).equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        // Fetch the selected professor using the professor's ID (from the module) and set it in the ComboBox
        Professeur selectedProf = professeurDAO.getProfesseurById(module.getProfesseurId());
        comboField.setValue(selectedProf);  // Set the selected professor in the ComboBox
    }



    @FXML
    void handleModifier(ActionEvent event) {
        // Get the updated values from the form
        String nomModule = nomField.getText();
        String codeModule = codeField.getText();
        // Get the selected Professeur object from the ComboBox
        Professeur selectedProfesseur = comboField.getValue();

        if (nomModule.isEmpty() || codeModule.isEmpty() || selectedProfesseur == null) {
            // Show error message if fields are incomplete
            showErrorDialog("Error", "Please fill in all fields.");
            return;
        }

        // Get the professor ID from the selected Professeur object
        Integer professeurId = selectedProfesseur.getId(); // Access the 'id' field of Professeur

        // Create a new Modules object with the updated values
        Modules updatedModule = new Modules();
        updatedModule.setNomModule(nomModule);
        updatedModule.setCodeModule(codeModule);
        updatedModule.setProfesseurId(professeurId);

        // Call the DAO method to update the module
        ModuleDAOImp moduleDAO = new ModuleDAOImp();
        moduleDAO.modifierModule(updatedModule);

        // Close the current dialog and refresh the module list (assuming parent stage is available)
        Stage dialogStage = (Stage) nomField.getScene().getWindow();
        dialogStage.close();

        // Optionally, refresh the list of modules (if applicable in your flow)
        // parentController.refreshModuleList();
    }

    private void showErrorDialog(String title, String message) {
        // Create a new alert with ERROR type
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);  // Set the title of the alert
        alert.setHeaderText(null);  // No header text
        alert.setContentText(message);  // The message to display

        // Show the alert and wait for user interaction
        alert.showAndWait();
    }


}
