package org.example.school_management.DAO;

import org.example.school_management.config.DatabaseConnection;
import org.example.school_management.entities.Professeur;
import org.example.school_management.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfDAOImp implements ProfDAO {

    private Connection connection;

    public ProfDAOImp() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void ajouterProfesseur(Professeur prof) {
        String userQuery = "INSERT INTO utilisateurs (username, password, role) VALUES (?, ?, ?)";
        String profQuery = "INSERT INTO professeurs (nom, prenom, specialite, utilisateur_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement userStmt = connection.prepareStatement(userQuery, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement profStmt = connection.prepareStatement(profQuery)) {

            // Insert into utilisateurs (User entity)
            userStmt.setString(1, prof.getUser().getUsername());
            userStmt.setString(2, prof.getUser().getPassword());
            userStmt.setInt(3, prof.getUser().getRole()); // Assuming role is set on User entity
            userStmt.executeUpdate();

            // Retrieve the generated user ID
            var generatedKeys = userStmt.getGeneratedKeys();
            int utilisateurId = 0;
            if (generatedKeys.next()) {
                utilisateurId = generatedKeys.getInt(1);
            }

            // Insert into professeurs (Professeur entity)
            profStmt.setString(1, prof.getNom());
            profStmt.setString(2, prof.getPrenom());
            profStmt.setString(3, prof.getSpecialite());
            profStmt.setInt(4, utilisateurId); // Link to the newly created user
            profStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Professeur> afficherProfesseurs() {
        List<Professeur> professeurs = new ArrayList<>();
        String query = "SELECT p.id, p.nom, p.prenom, p.specialite, u.id as user_id, u.username, u.password, u.role " +
                "FROM professeurs p " +
                "JOIN utilisateurs u ON p.utilisateur_id = u.id";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            var rs = stmt.executeQuery();

            while (rs.next()) {
                Professeur prof = new Professeur();
                prof.setId(rs.getInt("id"));
                prof.setNom(rs.getString("nom"));
                prof.setPrenom(rs.getString("prenom"));
                prof.setSpecialite(rs.getString("specialite"));

                // Create and populate the User object
                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getInt("role"));

                prof.setUser(user);  // Set the User object inside Professeur

                professeurs.add(prof);  // Add the professor to the list
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return professeurs;
    }
    @Override
    public void supprimerProfesseur(int id) {
        String deleteProfQuery = "DELETE FROM professeurs WHERE id = ?";
        String deleteUserQuery = "DELETE FROM utilisateurs WHERE id = ?";

        try (PreparedStatement deleteProfStmt = connection.prepareStatement(deleteProfQuery);
             PreparedStatement deleteUserStmt = connection.prepareStatement(deleteUserQuery)) {

            // Delete the professor first
            deleteProfStmt.setInt(1, id);
            deleteProfStmt.executeUpdate();

            // After deleting the professor, delete the associated user
            // Assuming that 'id' is the primary key of the professor and also associated user in the 'utilisateurs' table
            deleteUserStmt.setInt(1, id);
            deleteUserStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void modifierProfesseur(Professeur prof) {
        String updateUserQuery = "UPDATE utilisateurs SET username = ?, password = ?, role = ? WHERE id = ?";
        String updateProfQuery = "UPDATE professeurs SET nom = ?, prenom = ?, specialite = ? WHERE id = ?";

        try (PreparedStatement updateUserStmt = connection.prepareStatement(updateUserQuery);
             PreparedStatement updateProfStmt = connection.prepareStatement(updateProfQuery)) {

            // Update the user details
            updateUserStmt.setString(1, prof.getUser().getUsername());
            updateUserStmt.setString(2, prof.getUser().getPassword());
            updateUserStmt.setInt(3, prof.getUser().getRole());
            updateUserStmt.setInt(4, prof.getUser().getId());
            updateUserStmt.executeUpdate();

            // Update the professor details
            updateProfStmt.setString(1, prof.getNom());
            updateProfStmt.setString(2, prof.getPrenom());
            updateProfStmt.setString(3, prof.getSpecialite());
            updateProfStmt.setInt(4, prof.getId());
            updateProfStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Professeur getProfesseurById(int id) throws SQLException {
        // SQL query to get professor by ID
        String query = "SELECT * FROM professeurs WHERE id = ?";
        Professeur professeur = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id); // Set the ID parameter for the query
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    professeur = new Professeur();
                    professeur.setId(resultSet.getInt("id"));
                    professeur.setNom(resultSet.getString("nom")); // Assuming the column is named 'nom'
                    // Add more fields as necessary based on your schema
                }
            }
        }
        return professeur;  // Return the fetched professor or null if not found
    }



}
