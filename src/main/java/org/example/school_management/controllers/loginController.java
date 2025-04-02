package org.example.school_management.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.school_management.config.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class loginController {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Label wrongPass;

    public static int loggedInUserId = -1;

    public void loginButtonOnAction(ActionEvent event){
        if(!username.getText().isBlank() && !password.getText().isBlank()){
            //wrongPass.setText("You try to login");
            validateLogin();
        }
        else {
            wrongPass.setText("Veuillez entrer le nom d'utilisateur et le mot de passe.");
        }

    }
    public void validateLogin() {
        DatabaseConnection connection = DatabaseConnection.getInstance();
        Connection con = connection.getConnection();

        String query = "SELECT role,id FROM utilisateurs WHERE username = ? AND password = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, username.getText());
            preparedStatement.setString(2, password.getText());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int role = resultSet.getInt("role");

                handleRole(role,con);
            } else {
                wrongPass.setText("Nom d'utilisateur ou mot de passe incorrect.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            wrongPass.setText("Une erreur s'est produite lors de la connexion.");
        }
    }

    private void handleRole(int role, Connection con) throws IOException {
        Scene scene;
        String fxmlPath = "";
        Stage stage = new Stage();


        switch (role) {
            case 1:
                fxmlPath = "/org/example/school_management/fxml/Dashboards/AdminDashboard.fxml";
                break;
            case 2:
                fxmlPath = "/org/example/school_management/fxml/Dashboards/SecretaireDashboard.fxml";
                break;
            case 3:
                fxmlPath = "/org/example/school_management/fxml/Dashboards/ProfesseurDashboard.fxml";

                break;
            default:
                break;
        }
        if(!fxmlPath.isEmpty()){
            System.out.println(fxmlPath);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
        }
        else {
            wrongPass.setText("Rôle non trouvé ou FXML non disponible.");
            return;
        }
        stage.show();
        Stage loginStage = (Stage) username.getScene().getWindow();
        loginStage.close();
    }

}
