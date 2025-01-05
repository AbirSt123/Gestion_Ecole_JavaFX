package org.example.school_management.controllers.Etudiant;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.example.school_management.DAO.EtudDAOImp;
import org.example.school_management.DAO.EtudiantDAO;
import org.example.school_management.entities.Etudiant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AjouterEtudiant {

    @FXML
    private DatePicker dateNaissanceField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField matriculeField;

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField promotionField;

    private EtudDAOImp etudiantDAO = new EtudDAOImp();

    @FXML
    void handleAjouterEtudiant(ActionEvent event) {
        String matricule = matriculeField.getText();
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        if (dateNaissanceField.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Form Validation", "Veuillez entrer tous les champs.");
            return;
        }
        java.sql.Date dateNaissance = java.sql.Date.valueOf(dateNaissanceField.getValue());
        String email = emailField.getText();
        String promotion = promotionField.getText();


        if (validateFields(matricule, nom, prenom, email, promotion, dateNaissance)) {
            if (validateEmail(email)) {
                Etudiant etudiant = new Etudiant(matricule, nom, prenom, dateNaissance, email, promotion);

                etudiantDAO.ajouterEtudiant(etudiant);
                clearForm();

                showAlert(Alert.AlertType.INFORMATION, "Success", "Etudiant ajouté avec succès!");
            }
        }
    }

    private boolean validateFields(String matricule, String nom, String prenom, String email, String promotion,java.sql.Date dateNaissance) {
        if (matricule.isEmpty() || nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || promotion.isEmpty()|| dateNaissance == null) {
            showAlert(Alert.AlertType.ERROR, "Form Validation", "Veuillez entrer tous les champs.");
            return false;
        }
        return true;
    }


    private boolean validateEmail(String email) {
        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Email Validation", "Veuillez entrer une adresse mail valide.");
            return false;
        }
        return true;
    }

    // Method to validate email format using regex
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private void clearForm() {
        matriculeField.clear();
        nomField.clear();
        prenomField.clear();
        dateNaissanceField.setValue(null);
        emailField.clear();
        promotionField.clear();
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
