package org.example.school_management.DAO;

import org.example.school_management.config.DatabaseConnection;
import org.example.school_management.entities.Etudiant;
import org.example.school_management.entities.Inscription;
import org.example.school_management.entities.Modules;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InscriptionDAOImp implements InscriptionDAO {

    private Connection connection;

    public InscriptionDAOImp() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void registerStudentToModule(Etudiant etudiant, Modules module) {
        // Insert the inscription into the database
        String query = "INSERT INTO inscriptions (etudiant_id, module_id, date_inscription) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, etudiant.getId());  // Assuming Etudiant has an getId() method
            statement.setInt(2, module.getId());    // Assuming Modules has an getId() method
            statement.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unregisterStudentFromModule(Etudiant etudiant, Modules module) {
        // Remove the inscription from the database
        String query = "DELETE FROM inscriptions WHERE etudiant_id = ? AND module_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, etudiant.getId());  // Assuming Etudiant has an getId() method
            statement.setInt(2, module.getId());    // Assuming Modules has an getId() method
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Inscription> getStudentsByModule(int moduleId) {
        List<Inscription> inscriptions = new ArrayList<>();
        String query = "SELECT e.id AS etudiant_id, e.nom, e.prenom, i.date_inscription " +
                "FROM etudiants e " +
                "JOIN inscriptions i ON e.id = i.etudiant_id " +
                "WHERE i.module_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, moduleId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Create Etudiant object
                Etudiant etudiant = new Etudiant();
                etudiant.setId(resultSet.getInt("etudiant_id"));
                etudiant.setNom(resultSet.getString("nom"));
                etudiant.setPrenom(resultSet.getString("prenom"));

                // Create Inscription object and link it with Etudiant
                Inscription inscription = new Inscription();
                inscription.setDateInscription(resultSet.getDate("date_inscription"));
                inscription.setEtudiant(etudiant);

                // Add the Inscription object to the list
                inscriptions.add(inscription);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return inscriptions;
    }
    @Override
    public List<Inscription> getInscriptionsByModuleId(int moduleId) {
        List<Inscription> inscriptions = new ArrayList<>();
        try {
            String sql = "SELECT * FROM inscriptions WHERE module_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, moduleId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Inscription inscription = new Inscription(
                        rs.getInt("id"),
                        rs.getDate("dateInscription"),
                        null, // Set the Etudiant object if needed
                        null  // Set the Module object if needed
                );
                inscriptions.add(inscription);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inscriptions;
    }


}
