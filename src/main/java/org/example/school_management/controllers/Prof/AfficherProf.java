package org.example.school_management.controllers.Prof;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
//import org.example.school_management.controllers.Etudiant.ModifierEtudiant;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.example.school_management.DAO.EtudDAOImp;
import org.example.school_management.DAO.ProfDAOImp;
import org.example.school_management.entities.Etudiant;
import org.example.school_management.entities.Professeur;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


public class AfficherProf {

    @FXML
    private AnchorPane profsPane;

    @FXML
    private TableColumn<Professeur, Void> actionsColumn;

    @FXML
    private TableColumn<Professeur, Integer> idColumn;

    @FXML
    private TableColumn<Professeur, String> nomColumn;

    @FXML
    private TableColumn<Professeur,String> prenomColumn;


    @FXML
    private TableColumn<Professeur,String> specialite;

    @FXML
    private TableView<Professeur> tableView;

    private final ProfDAOImp profDAO = new ProfDAOImp();
    private final ObservableList<Professeur> professeurList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        specialite.setCellValueFactory(new PropertyValueFactory<>("specialite"));

        // Configure actions column
        configureActionsColumn();

        // Load data into the TableView
        loadProfesseurs();
    }

    private void configureActionsColumn() {
        actionsColumn.setCellFactory(param -> new TableCell<Professeur, Void>() {
            private final Button modifyButton = new Button("Modifier");
            private final Button deleteButton = new Button("Supprimer");

            {
                modifyButton.getStyleClass().add("modify-btn");
                deleteButton.getStyleClass().add("delete-btn");

                modifyButton.setOnAction(event -> {
                    Professeur professeur = getTableView().getItems().get(getIndex());
                    openDialog("ModifierProfDialog.fxml", "Modify Professeur", professeur);
                });

                deleteButton.setOnAction(event -> {
                    Professeur professeur = getTableView().getItems().get(getIndex());
                    handleDelete(professeur);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(10, modifyButton, deleteButton);
                    setGraphic(hbox);
                }
            }
        });
    }


    private void loadProfesseurs() {
        List<Professeur> professeur = profDAO.afficherProfesseurs();
        professeurList.setAll(professeur);
        tableView.setItems(professeurList);
    }

    @FXML
    void btnAdd(ActionEvent event) {
        openDialog("AddProfDialog.fxml", "Add Student", null);
    }

    private void openDialog(String fxmlFile, String title, Professeur professeur) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/school_management/fxml/Profs/" + fxmlFile));
            AnchorPane page = loader.load();

            // If the dialog requires data, pass it to the controller
//            if (etudiant != null) {
//                ModifierEtudiant controller = loader.getController();
//                controller.initData(etudiant);
//            }
            if (professeur != null) {
                // Pass the selected professor to the controller
                ModifierProf controller = loader.getController();
                controller.initData(professeur);
            }

            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.initOwner(profsPane.getScene().getWindow());
            dialogStage.setScene(new Scene(page));
            dialogStage.showAndWait();

            // Refresh the TableView after the dialog closes
            loadProfesseurs();
        } catch (IOException e) {
            showErrorDialog("Error", "Unable to open dialog: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void handleDelete(Professeur professeur) {
        Optional<ButtonType> result = showConfirmationDialog("Confirm Deletion", "Are you sure you want to delete this student?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            profDAO.supprimerProfesseur(professeur.getId());
            loadProfesseurs();
        }
    }

    private Optional<ButtonType> showConfirmationDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait();
    }

    @FXML
    public void exportToCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        fileChooser.setInitialFileName("professors.csv");

        Stage stage = (Stage) profsPane.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                // Write header
                writer.write("ID,Nom,Prénom,Spécialité");
                writer.newLine();

                // Write data rows
                List<Professeur> professeurs = profDAO.afficherProfesseurs();
                for (Professeur professeur : professeurs) {
                    writer.write(professeur.getId() + "," +
                            professeur.getNom() + "," +
                            professeur.getPrenom() + "," +
                            professeur.getSpecialite());
                    writer.newLine();
                }

                showInformationDialog("Export Successful", "CSV file has been exported successfully!");

            } catch (IOException e) {
                showErrorDialog("Error", "An error occurred while exporting to CSV: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }


    private void showInformationDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void exportToPDF() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        fileChooser.setInitialFileName("professors.pdf");

        Stage stage = (Stage) profsPane.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);

                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                // Set font and initialize layout
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                float margin = 50;
                float yPosition = page.getMediaBox().getHeight() - margin;
                float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
                float rowHeight = 20;

                // Draw table header
                String[] headers = {"ID", "Nom", "Prénom", "Spécialité"};
                float[] columnWidths = {50, 150, 150, 150};
                drawRow(contentStream, headers, columnWidths, yPosition);
                yPosition -= rowHeight;

                // Draw table rows
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                List<Professeur> professeurs = profDAO.afficherProfesseurs();
                for (Professeur professeur : professeurs) {
                    if (yPosition < margin) { // Check if a new page is needed
                        contentStream.close();
                        page = new PDPage();
                        document.addPage(page);
                        contentStream = new PDPageContentStream(document, page);
                        yPosition = page.getMediaBox().getHeight() - margin;
                    }

                    String[] row = {
                            String.valueOf(professeur.getId()),
                            professeur.getNom(),
                            professeur.getPrenom(),
                            professeur.getSpecialite()
                    };
                    drawRow(contentStream, row, columnWidths, yPosition);
                    yPosition -= rowHeight;
                }

                contentStream.close();
                document.save(file);

                showInformationDialog("Export Successful", "PDF file has been exported successfully!");

            } catch (IOException e) {
                showErrorDialog("Error", "An error occurred while exporting to PDF: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void drawRow(PDPageContentStream contentStream, String[] row, float[] columnWidths, float yPosition) throws IOException {
        float xStart = 50;
        float cellMargin = 5;
        for (int i = 0; i < row.length; i++) {
            contentStream.beginText();
            contentStream.newLineAtOffset(xStart + cellMargin, yPosition - 15);
            contentStream.showText(row[i]);
            contentStream.endText();
            xStart += columnWidths[i];
        }
    }


}

