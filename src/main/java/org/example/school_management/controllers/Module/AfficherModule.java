package org.example.school_management.controllers.Module;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.school_management.DAO.ModuleDAOImp;
import org.example.school_management.DAO.ProfDAOImp; // Assuming a DAO for professors
import org.example.school_management.entities.Modules;
import org.example.school_management.entities.Professeur;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherModule {

    @FXML
    private TableView<Modules> tableView;  // TableView to display modules
    @FXML
    private TableColumn<Modules, String> nomColumn; // Column for module name
    @FXML
    private TableColumn<Modules, String> codeColumn; // Column for module code
    @FXML
    private TableColumn<Modules, String> profColumn; // Column for professor


    @FXML
    private TextField codeField;

    @FXML
    private ComboBox<Professeur> comboField;  // Assuming Professeur is the class representing professors

    @FXML
    private TextField nomField;

    @FXML
    private AnchorPane modulesPane;

    private Modules currentModule;

    public void initData(Modules module) throws SQLException {
        this.currentModule = module;

        // Populate the fields with existing data
        nomField.setText(module.getNomModule());
        codeField.setText(module.getCodeModule());

        // Assuming you have a method to get all professors
        ProfDAOImp professeurDAO = new ProfDAOImp();
        comboField.getItems().setAll(professeurDAO.afficherProfesseurs());  // Populate ComboBox with professors

        // Assuming you need to set the selected professor based on the current module's professor ID
        // Find the professor by ID and set it as the selected value
        Professeur selectedProf = professeurDAO.getProfesseurById(module.getProfesseurId());
        comboField.setValue(selectedProf);
    }

    @FXML
    void handleModifier(ActionEvent event) {
        // Get the updated values from the form
        String nomModule = nomField.getText();
        String codeModule = codeField.getText();
        // Get the selected professor from ComboBox
        Professeur selectedProf = comboField.getValue();

        if (nomModule.isEmpty() || codeModule.isEmpty() || selectedProf == null) {
            // Show error message if fields are incomplete
            showErrorDialog("Error", "Please fill in all fields.");
            return;
        }

        // Create a new Modules object with the updated values
        Modules updatedModule = new Modules();
        updatedModule.setNomModule(nomModule);
        updatedModule.setCodeModule(codeModule);
        updatedModule.setProfesseurId(selectedProf.getId());  // Set the professor's ID

        // Call the DAO method to update the module
        ModuleDAOImp moduleDAO = new ModuleDAOImp();
        moduleDAO.modifierModule(updatedModule);

        // Close the current dialog
        Stage dialogStage = (Stage) nomField.getScene().getWindow();
        dialogStage.close();
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
    @FXML
    public void btnAdd(ActionEvent actionEvent) {
        // Call the openDialog method to show the "AddModuleDialog.fxml" for adding a new module
        openDialog("AddModuleDialog.fxml", "Ajouter Module", null);
    }
    private void openDialog(String fxmlFile, String title, Modules module) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/school_management/fxml/Modules/" + fxmlFile));
            AnchorPane page = loader.load();

            if (module != null) {
                ModifierModule controller = loader.getController();
                controller.initData(module);
            }

            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.initOwner(modulesPane.getScene().getWindow());
            dialogStage.setScene(new Scene(page));
            dialogStage.showAndWait();

            loadModules(); // Refresh the table after dialog closes
        } catch (IOException | SQLException e) {
            showErrorDialog("Error", "Unable to open dialog: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void loadModules() {
        ModuleDAOImp moduleDAO = new ModuleDAOImp();
        // Fetch the list of modules
        List<Modules> modulesList = moduleDAO.afficherModules();

        // Create an observable list to bind to the TableView
        ObservableList<Modules> observableModules = FXCollections.observableList(modulesList);

        // Set the items in the TableView
        tableView.setItems(observableModules);

        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomModule()));
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomModule()));
        codeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodeModule()));
        profColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProfesseur().getNom()));  // Assuming Professeur has a getNom method
    }


}
