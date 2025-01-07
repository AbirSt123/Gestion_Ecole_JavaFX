package org.example.school_management.DAO;

import org.example.school_management.config.DatabaseConnection;
import org.example.school_management.entities.Modules;
import org.example.school_management.entities.Professeur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleDAOImp implements ModuleDAO {

    private Connection connection;

    public ModuleDAOImp() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    @Override
    public void ajouterModule(Modules module) {
        String query = "INSERT INTO modules (nom_module, code_module, professeur_id) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, module.getNomModule());
            preparedStatement.setString(2, module.getCodeModule());
            preparedStatement.setInt(3, module.getProfesseurId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Modules> afficherModules() {
        List<Modules> modulesList = new ArrayList<>();
        String query = "SELECT * FROM modules";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            var rs = stmt.executeQuery();

            // Create an instance of ProfDAOImp
            ProfDAOImp profDAOImp = new ProfDAOImp();

            while (rs.next()) {
                Modules module = new Modules();
                module.setId(rs.getInt("id"));
                module.setNomModule(rs.getString("nom_module"));
                module.setCodeModule(rs.getString("code_module"));
                module.setProfesseurId(rs.getInt("professeur_id"));

                // Get Professeur using ProfDAOImp
                Professeur professeur = profDAOImp.getProfesseurById(module.getProfesseurId());
                module.setProfesseur(professeur);

                modulesList.add(module);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return modulesList;
    }


    @Override
    public void supprimerModule(int id) {
        String query = "DELETE FROM modules WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifierModule(Modules module) {
        String query = "UPDATE modules SET nom_module = ?, code_module = ?, professeur_id = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Set the new values for the module
            preparedStatement.setString(1, module.getNomModule());
            preparedStatement.setString(2, module.getCodeModule());
            preparedStatement.setInt(3, module.getProfesseurId());
            preparedStatement.setInt(4, module.getId());  // Specify the module to update by its ID

            // Execute the update query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
