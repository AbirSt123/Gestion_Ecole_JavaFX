package org.example.school_management.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Etudiant {
    private int id;
    private String matricule;
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String email;
    private String promotion;
}
