package org.example.school_management.controllers.Etudiant;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.example.school_management.DAO.EtudDAOImp;
import org.example.school_management.entities.Etudiant;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;






public class AfficherEtudiant {

    @FXML
    private AnchorPane etudiantsPane;

    @FXML
    private TableView<Etudiant> tableView;

    @FXML
    private TableColumn<Etudiant, Integer> idColumn;

    @FXML
    private TableColumn<Etudiant, String> matriculeColumn;

    @FXML
    private TableColumn<Etudiant, String> nomColumn;

    @FXML
    private TableColumn<Etudiant, String> prenomColumn;

    @FXML
    private TableColumn<Etudiant, String> dateNaissanceColumn;

    @FXML
    private TableColumn<Etudiant, String> emailColumn;

    @FXML
    private TableColumn<Etudiant, String> promotionColumn;

    @FXML
    private TableColumn<Etudiant, Void> actionsColumn;

    private final EtudDAOImp etudDAO = new EtudDAOImp();
    private final ObservableList<Etudiant> etudiantList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        matriculeColumn.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        dateNaissanceColumn.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        promotionColumn.setCellValueFactory(new PropertyValueFactory<>("promotion"));

        configureActionsColumn();

        loadEtudiants();
    }

    private void configureActionsColumn() {
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button modifyButton = new Button("Modifier");
            private final Button deleteButton = new Button("Supprimer");

            {
                modifyButton.getStyleClass().add("modify-btn");
                deleteButton.getStyleClass().add("delete-btn");

                modifyButton.setOnAction(event -> {
                    Etudiant etudiant = getTableView().getItems().get(getIndex());
                    openDialog("ModifierEtudiantDialog.fxml", "Modify Student", etudiant);
                });

                deleteButton.setOnAction(event -> {
                    Etudiant etudiant = getTableView().getItems().get(getIndex());
                    handleDelete(etudiant);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    AnchorPane buttonsPane = new AnchorPane(modifyButton, deleteButton);
                    AnchorPane.setLeftAnchor(modifyButton, 0.0);
                    AnchorPane.setRightAnchor(deleteButton, 0.0);
                    setGraphic(buttonsPane);
                }
            }
        });
    }

    private void loadEtudiants() {
        List<Etudiant> etudiants = etudDAO.afficherEtudiants();
        etudiantList.setAll(etudiants);
        tableView.setItems(etudiantList);
    }

    private void handleDelete(Etudiant etudiant) {
        Optional<ButtonType> result = showConfirmationDialog("Confirm Deletion", "Are you sure you want to delete this student?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            etudDAO.supprimerEtudiant(etudiant.getId());
            loadEtudiants();
        }
    }

    @FXML
    void btnAdd(ActionEvent event) {
        openDialog("AddStudentDialog.fxml", "Add Student", null);
    }

    private void openDialog(String fxmlFile, String title, Etudiant etudiant) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/school_management/fxml/Etudiant/" + fxmlFile));
            AnchorPane page = loader.load();

            if (etudiant != null) {
                ModifierEtudiant controller = loader.getController();
                controller.initData(etudiant);
            }

            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.initOwner(etudiantsPane.getScene().getWindow());
            dialogStage.setScene(new Scene(page));
            dialogStage.showAndWait();

            loadEtudiants();
        } catch (IOException e) {
            showErrorDialog("Error", "Unable to open dialog: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Optional<ButtonType> showConfirmationDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait();
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void exportToCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        // Show save dialog to let the user choose the file location
        File file = fileChooser.showSaveDialog(etudiantsPane.getScene().getWindow());

        if (file != null) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                // Write the header row
                writer.println("ID,Matricule,Nom,Prénom,Date de naissance,Email,Promotion");

                // Write the student data
                for (Etudiant etudiant : etudiantList) {
                    writer.println(etudiant.getId() + "," +
                            etudiant.getMatricule() + "," +
                            etudiant.getNom() + "," +
                            etudiant.getPrenom() + "," +
                            etudiant.getDateNaissance() + "," +
                            etudiant.getEmail() + "," +
                            etudiant.getPromotion());
                }

                showInformationDialog("Export successful", "CSV file has been saved successfully!");

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

    private void showErrorCSVDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
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

        // Show save dialog to let the user choose the file location
        File file = fileChooser.showSaveDialog(etudiantsPane.getScene().getWindow());

        if (file != null) {
            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    float yStart = 750;
                    float yPosition = yStart;
                    float margin = 50;
                    float cellHeight = 20;

                    // Create the table header
                    String[] headers = {"ID", "Matricule", "Nom", "Prénom", "Date de naissance", "Email", "Promotion"};
                    for (String header : headers) {
                        contentStream.beginText();
                        contentStream.newLineAtOffset(margin, yPosition);
                        contentStream.showText(header);
                        contentStream.endText();
                        margin += 100; // Adjust margin for next header
                    }

                    // Reset margin and move to next row
                    margin = 50;
                    yPosition -= cellHeight;

                    // Write data rows
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    for (Etudiant etudiant : etudiantList) {
                        String[] row = {
                                String.valueOf(etudiant.getId()),
                                etudiant.getMatricule(),
                                etudiant.getNom(),
                                etudiant.getPrenom(),
                                dateFormat.format(etudiant.getDateNaissance()),
                                etudiant.getEmail(),
                                etudiant.getPromotion()
                        };

                        for (String cell : row) {
                            contentStream.beginText();
                            contentStream.newLineAtOffset(margin, yPosition);
                            contentStream.showText(cell);
                            contentStream.endText();
                            margin += 100; // Adjust margin for next cell
                        }
                        margin = 50;
                        yPosition -= cellHeight;
                    }
                }

                // Save the document to the chosen location
                document.save(file);

                showInformationDialog("Export successful", "PDF file has been saved successfully!");

            } catch (IOException e) {
                showErrorDialog("Error", "An error occurred while exporting to PDF: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

}
