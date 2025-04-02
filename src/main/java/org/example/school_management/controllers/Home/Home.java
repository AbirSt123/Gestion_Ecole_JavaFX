package org.example.school_management.controllers.Home;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import org.example.school_management.DAO.ModuleDAOImp;
import org.example.school_management.DAO.ProfDAOImp;
import org.example.school_management.DAO.EtudDAOImp;
import org.example.school_management.entities.Modules;
import org.example.school_management.entities.Professeur;

import java.sql.SQLException;
import java.util.List;

public class Home {

    @FXML private Text totalStudentsLabel;
    @FXML private Text totalProfessorsLabel;
    @FXML private Text totalModulesLabel;


    private EtudDAOImp studentDAO = new EtudDAOImp();
    private ProfDAOImp profDAO = new ProfDAOImp();
    private ModuleDAOImp moduleDAO = new ModuleDAOImp();

    @FXML
    public void initialize() {
        try {
            // Load total counts
            totalStudentsLabel.setText(String.valueOf(studentDAO.getTotalStudents()));
            totalProfessorsLabel.setText(String.valueOf(profDAO.getTotalProfessors()));
            totalModulesLabel.setText(String.valueOf(moduleDAO.getTotalModules()));




        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
