package org.example.school_management.controllers.Inscription;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.school_management.DAO.InscriptionDAOImp;
import org.example.school_management.DAO.ModuleDAOImp;
import org.example.school_management.entities.Etudiant;
import org.example.school_management.entities.Inscription;
import org.example.school_management.entities.Modules;

import java.io.*;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;



public class AfficherInscription {

    @FXML
    private TableColumn<Inscription, Void> actionsColumn;

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
    private TableView<Inscription> tableView; // Updated to display `Inscription` objects

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

        actionsColumn.setCellFactory(col -> new TableCell<>() {
            private final Button btnAnnuler = new Button("Annuler");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    btnAnnuler.setOnAction(event -> {
                        Inscription inscription = getTableView().getItems().get(getIndex());
                        Etudiant etudiant = inscription.getEtudiant();
                        Modules module = comboField.getSelectionModel().getSelectedItem();
                        if (etudiant != null && module != null) {
                            unregisterStudent(etudiant, module);
                        }
                    });
                    btnAnnuler.getStyleClass().add("delete-btn");
                    setGraphic(btnAnnuler);
                }
            }
        });

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
    private void unregisterStudent(Etudiant etudiant, Modules module) {

        inscriptionDAO.unregisterStudentFromModule(etudiant, module);

        Modules selectedModule = comboField.getSelectionModel().getSelectedItem();
        if (selectedModule != null) {
            loadInscriptionsForModule(selectedModule);
        }
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

    public void btnAdd() {
        // Open the dialog to add a new student
        openDialog("/org/example/school_management/fxml/Inscription/InscrireEtudiantDialog.fxml", "Inscrire Etudiant", null);
        Modules selectedModule = comboField.getSelectionModel().getSelectedItem();
        if (selectedModule != null) {
            loadInscriptionsForModule(selectedModule);
        }
    }

    public void openDialog(String fxmlFile, String title, Object controllerData) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));

            // Set the controller if needed
            if (controllerData != null) {
                loader.setController(controllerData);
            }

            // Load the scene from the FXML file
            Parent root = loader.load();

            // Create a new Stage (window) for the dialog
            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);

            // Set the modality for the dialog (blocks interaction with other windows)
            dialogStage.initModality(Modality.APPLICATION_MODAL);

            // Set the scene to the loaded FXML
            dialogStage.setScene(new Scene(root));

            // Show the dialog
            dialogStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @FXML
    public void exportToCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                // Write the header
                writer.write("Id,Nom,Prénom,Date Inscription");
                writer.newLine();

                // Write the data
                for (Inscription inscription : inscriptionList) {
                    Etudiant etudiant = inscription.getEtudiant();
                    String id = etudiant != null ? String.valueOf(etudiant.getId()) : "";
                    String nom = etudiant != null ? etudiant.getNom() : "";
                    String prenom = etudiant != null ? etudiant.getPrenom() : "";
                    String dateInscription = inscription.getDateInscription() != null ? inscription.getDateInscription().toString() : "";

                    writer.write(String.join(",", id, nom, prenom, dateInscription));
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'exporter vers CSV.");
            }
        }
    }

    @FXML
    public void exportToPDF() {
        try {
            // Specify the output PDF file path
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            fileChooser.setTitle("Save PDF File");
            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                try (PDDocument document = new PDDocument()) {
                    PDPage page = new PDPage();
                    document.addPage(page);

                    // Start writing to the PDF
                    PDPageContentStream contentStream = new PDPageContentStream(document, page);

                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                    contentStream.beginText();
                    contentStream.setLeading(20f);
                    contentStream.newLineAtOffset(50, 750);

                    // Add header
                    contentStream.showText("Liste des inscriptions");
                    contentStream.newLine();
                    contentStream.setFont(PDType1Font.HELVETICA, 12);

                    // Add table headers
                    contentStream.showText("Id       Nom       Prénom       Date Inscription");
                    contentStream.newLine();

                    // Add data rows
                    for (Inscription inscription : inscriptionList) {
                        Etudiant etudiant = inscription.getEtudiant();
                        String line = String.format(
                                "%-8s %-10s %-10s %-15s",
                                etudiant != null ? etudiant.getId() : "",
                                etudiant != null ? etudiant.getNom() : "",
                                etudiant != null ? etudiant.getPrenom() : "",
                                inscription.getDateInscription()
                        );
                        contentStream.showText(line);
                        contentStream.newLine();
                    }

                    contentStream.endText();
                    contentStream.close();

                    // Save the PDF
                    document.save(file);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'exporter vers PDF.");
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
