package org.example.school_management.controllers.ProfEtu;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.school_management.DAO.InscriptionDAOImp;
import org.example.school_management.DAO.ModuleDAOImp;
import org.example.school_management.entities.Etudiant;
import org.example.school_management.entities.Inscription;
import org.example.school_management.entities.Modules;

import java.util.List;

public class ProfEtu {

    @FXML
    private TableColumn<Inscription, String> idColumn;

    @FXML
    private TableColumn<Inscription, String> nomColumn;

    @FXML
    private TableColumn<Inscription, String> prenomColumn;

    @FXML
    private TableColumn<Inscription, String> dateColumn;

    @FXML
    private ComboBox<Modules> comboField;

    @FXML
    private TableView<Inscription> tableView; // Display `Inscription` objects

    private InscriptionDAOImp inscriptionDAO = new InscriptionDAOImp();
    private ModuleDAOImp moduleDAO = new ModuleDAOImp();
    private ObservableList<Inscription> inscriptionList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up the table columns
        idColumn.setCellValueFactory(cellData -> {
            Inscription inscription = cellData.getValue();
            Etudiant etudiant = inscription.getEtudiant();
            return etudiant != null ? new SimpleStringProperty(String.valueOf(etudiant.getId())) : new SimpleStringProperty("");
        });
        nomColumn.setCellValueFactory(cellData -> {
            Inscription inscription = cellData.getValue();
            Etudiant etudiant = inscription.getEtudiant();
            return etudiant != null ? new SimpleStringProperty(etudiant.getNom()) : new SimpleStringProperty("");
        });
        prenomColumn.setCellValueFactory(cellData -> {
            Inscription inscription = cellData.getValue();
            Etudiant etudiant = inscription.getEtudiant();
            return etudiant != null ? new SimpleStringProperty(etudiant.getPrenom()) : new SimpleStringProperty("");
        });
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateInscription"));

        comboField.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Modules>() {
            @Override
            public void changed(ObservableValue<? extends Modules> observable, Modules oldValue, Modules newValue) {
                if (newValue != null) {
                    loadInscriptionsForModule(newValue);
                }
            }
        });

        populateModulesComboBox();
    }

    private void loadInscriptionsForModule(Modules module) {
        // Get inscriptions for the selected module
        List<Inscription> inscriptions = inscriptionDAO.getStudentsByModule(module.getId());

        // Clear current list and add the new list of inscriptions
        inscriptionList.clear();
        inscriptionList.addAll(inscriptions);

        // Update the TableView with the new list of inscriptions
        tableView.setItems(inscriptionList);
    }

    private void populateModulesComboBox() {
        // Fetch modules from the database
        List<Modules> modules = moduleDAO.afficherModules();

        // Populate the ComboBox with the modules
        comboField.setItems(FXCollections.observableArrayList(modules));

        comboField.setCellFactory(comboBox -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Modules item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNomModule());
            }
        });
        comboField.setButtonCell(new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Modules item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNomModule());
            }
        });
    }
}
