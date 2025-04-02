package org.example.school_management.controllers.ProfEtu;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.school_management.DAO.ModuleDAOImp;
import org.example.school_management.entities.Modules;
import org.example.school_management.entities.Professeur;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherModules{

    @FXML
    private TableView<Modules> tableView;
    @FXML
    private TableColumn<Modules, String> nomColumn;
    @FXML
    private TableColumn<Modules, String> codeColumn;
    @FXML
    private TableColumn<Modules, String> profColumn;
    @FXML
    private TableColumn<Modules, String> idColumn;
    @FXML
    private TableColumn<Modules, Void> actionsColumn;

    @FXML
    private AnchorPane modulesPane;

    @FXML
    public void initialize() {
        loadModules();
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
                btnModifier.getStyleClass().add("modify-btn");
                btnSupprimer.getStyleClass().add("cancel-btn");

                btnModifier.setOnAction(event -> {
                    Modules selectedModule = getTableView().getItems().get(getIndex());
                    // Add the logic to open the Modify dialog here
                });

                btnSupprimer.setOnAction(event -> {
                    Modules selectedModule = getTableView().getItems().get(getIndex());
                    deleteModule(selectedModule);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : new HBox(10, btnModifier, btnSupprimer));
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
