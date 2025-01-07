package org.example.school_management.controllers.Prof;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.school_management.DAO.ProfDAOImp;
import org.example.school_management.entities.Professeur;
import org.example.school_management.entities.User;

public class AjouterProf {

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

    @FXML
    void handleAjouterProf(ActionEvent event) {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String password = passwordField.getText();
        String specialite = specialiteField.getText();
        String username = usernameField.getText();

        if(validateFields(nom,prenom,password,specialite,username)){
            try{
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.setRole(3);

                Professeur prof = new Professeur();
                prof.setNom(nom);
                prof.setPrenom(prenom);
                prof.setSpecialite(specialite);
                prof.setUser(user);

                // Add professor and associated user
                profDAO.ajouterProfesseur(prof);

                // Show success message
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Professeur ajouté avec succès.");

                // Clear input fields
                clearFields();
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors de l'ajout.");
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
