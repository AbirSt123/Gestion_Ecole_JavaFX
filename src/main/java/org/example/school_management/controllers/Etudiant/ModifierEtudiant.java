package org.example.school_management.controllers.Etudiant;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.example.school_management.DAO.EtudDAOImp;
import org.example.school_management.entities.Etudiant;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModifierEtudiant {

    @FXML
    private TextField matriculeField;

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private DatePicker dateNaissanceField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField promotionField;

    @FXML
    private TextField error;

    private final EtudDAOImp etudiantDAO = new EtudDAOImp();
    private int id;
    public void initData(Etudiant etudiant) {
        matriculeField.setText(etudiant.getMatricule());
        nomField.setText(etudiant.getNom());
        prenomField.setText(etudiant.getPrenom());
        Date sqlDate = (Date) etudiant.getDateNaissance();
        if (sqlDate != null) {
            // Convert java.util.Date to java.sql.Date
            java.sql.Date sqlDateConverted = new java.sql.Date(sqlDate.getTime());

            // Convert to LocalDate
            LocalDate localDate = sqlDateConverted.toLocalDate();

            // Set the value in the field
            dateNaissanceField.setValue(localDate);
        }
        emailField.setText(etudiant.getEmail());
        promotionField.setText(etudiant.getPromotion());
        this.id = etudiant.getId();
    }

    @FXML
    void handleModifierEtudiant(ActionEvent event) {
        String matricule = matriculeField.getText();
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        if (dateNaissanceField.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Form Validation", "Veuillez entrer tous les champs.");
            return;
        }
        Date dateNaissance = Date.valueOf(dateNaissanceField.getValue());
        String email = emailField.getText();
        String promotion = promotionField.getText();

        if (validateFields(matricule, nom, prenom, email, promotion, dateNaissance)) {
            if (validateEmail(email)) {
                Etudiant etudiant = new Etudiant(matricule, nom, prenom, dateNaissance, email, promotion);
                etudiant.setId(id);

                etudiantDAO.modifierEtudiant(etudiant);
                clearForm();

                showAlert(Alert.AlertType.INFORMATION, "Success", "Etudiant modifié avec succès!");
            }
        }
    }

    private boolean validateFields(String matricule, String nom, String prenom, String email, String promotion, Date dateNaissance) {
        if (matricule.isEmpty() || nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || promotion.isEmpty() || dateNaissance == null) {
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
