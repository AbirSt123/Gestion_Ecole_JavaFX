package org.example.school_management.controllers.Prof;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.school_management.DAO.ProfDAOImp;
import org.example.school_management.entities.Professeur;
import org.example.school_management.entities.User;

public class ModifierProf {

    @FXML
    private TextField nomField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField specialiteField;

    @FXML
    private TextField usernameField;

    private ProfDAOImp profDAO = new ProfDAOImp();
    private Professeur professeur;

    public void initData(Professeur professeur) {
        this.professeur = professeur;
        nomField.setText(professeur.getNom());
        prenomField.setText(professeur.getPrenom());
        specialiteField.setText(professeur.getSpecialite());
        usernameField.setText(professeur.getUser().getUsername());
        passwordField.setText(professeur.getUser().getPassword());
    }

    @FXML
    void handleModifierProf(ActionEvent event) {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String password = passwordField.getText();
        String specialite = specialiteField.getText();
        String username = usernameField.getText();

        if (validateFields(nom, prenom, password, specialite, username)) {
            try {
                // Update the user data
                User user = professeur.getUser();
                user.setUsername(username);
                user.setPassword(password);

                // Update the professor's data
                professeur.setNom(nom);
                professeur.setPrenom(prenom);
                professeur.setSpecialite(specialite);

                // Update the professor in the database
                profDAO.modifierProfesseur(professeur);

                // Show success message
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Professeur modifié avec succès.");

                // Optionally, close the dialog or clear fields
                clearFields();
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors de la modification.");
                e.printStackTrace();
            }
        }
    }

    private boolean validateFields(String nom, String prenom, String password, String specialite, String username) {
        if (nom.isEmpty() || prenom.isEmpty() || password.isEmpty() || specialite.isEmpty() || username.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Validation", "Veuillez entrer tous les champs.");
            return false;
        }
        return true;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        nomField.clear();
        prenomField.clear();
        passwordField.clear();
        specialiteField.clear();
        usernameField.clear();
    }
}
