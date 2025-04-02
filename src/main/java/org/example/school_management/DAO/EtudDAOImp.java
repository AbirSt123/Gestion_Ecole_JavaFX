package org.example.school_management.DAO;
import org.example.school_management.config.DatabaseConnection;
import org.example.school_management.entities.Etudiant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtudDAOImp implements EtudiantDAO{

    private Connection connection;

    // Constructor to initialize the connection
    public EtudDAOImp() {
            this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void ajouterEtudiant(Etudiant etudiant) {
        String query = "INSERT INTO etudiants (matricule, nom, prenom, date_naissance, email, promotion) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, etudiant.getMatricule());
            stmt.setString(2, etudiant.getNom());
            stmt.setString(3, etudiant.getPrenom());
            stmt.setDate(4, new Date(etudiant.getDateNaissance().getTime()));
            stmt.setString(5, etudiant.getEmail());
            stmt.setString(6, etudiant.getPromotion());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Etudiant> afficherEtudiants() {
        List<Etudiant> etudiants = new ArrayList<>();
        String query = "SELECT * FROM etudiants";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Etudiant etudiant = new Etudiant(
                        rs.getInt("id"),
                        rs.getString("matricule"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getDate("date_naissance"),
                        rs.getString("email"),
                        rs.getString("promotion")
                );
                etudiants.add(etudiant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etudiants;
    }

    @Override
    public void modifierEtudiant(Etudiant etudiant) {
        String query = "UPDATE etudiants SET nom = ?, prenom = ?, date_naissance = ?, email = ?, promotion = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, etudiant.getNom());
            stmt.setString(2, etudiant.getPrenom());
            stmt.setDate(3, new Date(etudiant.getDateNaissance().getTime()));
            stmt.setString(4, etudiant.getEmail());
            stmt.setString(5, etudiant.getPromotion());
            stmt.setInt(6, etudiant.getId()); // Use the id to identify the record
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerEtudiant(int id) {
        String query = "DELETE FROM etudiants WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getTotalStudents() throws SQLException {
        String query = "SELECT COUNT(*) FROM etudiants";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }






}
