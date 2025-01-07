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
import javafx.stage.Stage;
import org.example.school_management.DAO.EtudDAOImp;
import org.example.school_management.entities.Etudiant;

import java.io.IOException;
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
        // Bind columns to properties
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        matriculeColumn.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        dateNaissanceColumn.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        promotionColumn.setCellValueFactory(new PropertyValueFactory<>("promotion"));

        // Configure actions column
        configureActionsColumn();

        // Load data into the TableView
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

            // If the dialog requires data, pass it to the controller
            if (etudiant != null) {
                ModifierEtudiant controller = loader.getController();
                controller.initData(etudiant);
            }

            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.initOwner(etudiantsPane.getScene().getWindow());
            dialogStage.setScene(new Scene(page));
            dialogStage.showAndWait();

            // Refresh the TableView after the dialog closes
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
}
