package org.example.school_management.DAO;
import java.util.List;
import org.example.school_management.entities.Etudiant;
public interface EtudiantDAO {
    void ajouterEtudiant(Etudiant etudiant);
    void modifierEtudiant(Etudiant etudiant);
    void supprimerEtudiant(int id);
    List<Etudiant> afficherEtudiants();
//    Etudiant getEtudiantById(int id);
}
