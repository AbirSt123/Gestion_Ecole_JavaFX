package org.example.school_management.DAO;

import org.example.school_management.entities.Professeur;

import java.util.List;

public interface ProfDAO {
    void ajouterProfesseur(Professeur prof);
    void modifierProfesseur(Professeur prof);
    void supprimerProfesseur(int id);
    List<Professeur> afficherProfesseurs();

}
