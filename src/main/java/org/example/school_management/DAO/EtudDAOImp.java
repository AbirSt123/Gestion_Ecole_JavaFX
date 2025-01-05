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




}
