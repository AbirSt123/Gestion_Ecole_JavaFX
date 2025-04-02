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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.school_management.DAO.ModuleDAOImp;
import org.example.school_management.DAO.ProfDAOImp;
import org.example.school_management.entities.Modules;
import org.example.school_management.entities.Professeur;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherModule {

    @FXML
    private TableView<Modules> tableView;
    @FXML
    private TableColumn<Modules, String> nomColumn;
    @FXML
    private TableColumn<Modules, String> codeColumn;
    @FXML
    private TableColumn<Modules, String> profColumn;


    @FXML
    private TextField codeField;

    @FXML
    private TableColumn<Modules, String> idColumn;

    @FXML
    private ComboBox<Professeur> comboField;

    @FXML
    private TextField nomField;

    @FXML
    private AnchorPane modulesPane;

    @FXML
    private TableColumn<Modules, Void> actionsColumn;

    private Modules currentModule;

    public void initData(Modules module) throws SQLException {
        this.currentModule = module;

        // Populate the fields with existing data
        nomField.setText(module.getNomModule());
        codeField.setText(module.getCodeModule());

        // Assuming you have a method to get all professors
        ProfDAOImp professeurDAO = new ProfDAOImp();
        comboField.getItems().setAll(professeurDAO.afficherProfesseurs());
        Professeur selectedProf = professeurDAO.getProfesseurById(module.getProfesseurId());
        comboField.setValue(selectedProf);

    }

    @FXML
    public void initialize() {

        loadModules();
    }

    @FXML
    void handleModifier(ActionEvent event) {
        String nomModule = nomField.getText();
        String codeModule = codeField.getText();
        Professeur selectedProf = comboField.getValue();

        if (nomModule.isEmpty() || codeModule.isEmpty() || selectedProf == null) {
            showErrorDialog("Error", "Please fill in all fields.");
            return;
        }

        Modules updatedModule = new Modules();
        updatedModule.setNomModule(nomModule);
        updatedModule.setCodeModule(codeModule);
        updatedModule.setProfesseurId(selectedProf.getId());
        ModuleDAOImp moduleDAO = new ModuleDAOImp();
        moduleDAO.modifierModule(updatedModule);

        Stage dialogStage = (Stage) nomField.getScene().getWindow();
        dialogStage.close();
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
    @FXML
    public void btnAdd(ActionEvent actionEvent) {
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

            loadModules();
        } catch (IOException | SQLException e) {
            showErrorDialog("Error", "Unable to open dialog: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void loadModules() {
        ModuleDAOImp moduleDAO = new ModuleDAOImp();
        List<Modules> modulesList = moduleDAO.afficherModules();

        ObservableList<Modules> observableModules = FXCollections.observableList(modulesList);

        tableView.setItems(observableModules);
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomModule()));
        codeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodeModule()));
        profColumn.setCellValueFactory(cellData -> {
            Professeur professeur = cellData.getValue().getProfesseur();
            String profFullName = professeur != null ? professeur.getNom() + " " + professeur.getPrenom() : "N/A";
            return new SimpleStringProperty(profFullName);
        });
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button btnModifier = new Button("Modifier");
            private final Button btnSupprimer = new Button("Supprimer");

            {
                btnModifier.getStyleClass().add("modify-btn"); // Use the 'save-btn' style
                btnSupprimer.getStyleClass().add("cancel-btn");
                btnModifier.setOnAction(event -> {
                    Modules selectedModule = getTableView().getItems().get(getIndex());
                    openDialog("ModifyModuleDialog.fxml", "Modifier Module", selectedModule);

                });

                btnSupprimer.setOnAction(event -> {
                    Modules selectedModule = getTableView().getItems().get(getIndex());
                    deleteModule(selectedModule);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(10, btnModifier, btnSupprimer);
                    setGraphic(buttons);
                }
            }
        });
    }

    private void deleteModule(Modules module) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to delete this module?");
        alert.setContentText(module.getNomModule());

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                ModuleDAOImp moduleDAO = new ModuleDAOImp();
                moduleDAO.supprimerModule(module.getId());
                loadModules();
            }
        });
    }



}
