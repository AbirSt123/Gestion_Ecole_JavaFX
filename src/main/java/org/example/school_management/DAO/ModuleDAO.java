package org.example.school_management.DAO;

import org.example.school_management.entities.Modules;

import java.util.List;

public interface ModuleDAO {
    void ajouterModule(Modules module);
     void modifierModule(Modules module);
     void supprimerModule(int id);
      List<Modules> afficherModules();
}
