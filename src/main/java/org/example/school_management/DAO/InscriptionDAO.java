package org.example.school_management.DAO;

import org.example.school_management.entities.Etudiant;
import org.example.school_management.entities.Inscription;
import org.example.school_management.entities.Modules;

import java.util.List;

public interface InscriptionDAO {

    void registerStudentToModule(Etudiant etudiant, Modules module);
    void unregisterStudentFromModule(Etudiant etudiant, Modules module);
    List<Inscription> getStudentsByModule(int moduleId);
    List<Inscription> getInscriptionsByModuleId(int moduleId);
}
