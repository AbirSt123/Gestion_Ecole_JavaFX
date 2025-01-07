package org.example.school_management.entities;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Etudiant {
    private int id;
    private String matricule;
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String email;
    private String promotion;
    public Etudiant(String matricule, String nom, String prenom, java.util.Date dateNaissance, String email, String promotion) {
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.promotion = promotion;
    }
}
