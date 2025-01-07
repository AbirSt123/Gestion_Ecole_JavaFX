package org.example.school_management.entities;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Professeur {
    private int id;
    private String nom;
    private String prenom;
    private String specialite;
    private User user;
    public Professeur( String nom, String prenom, String specialite) {
        this.nom = nom;
        this.prenom = prenom;
        this.specialite = specialite;


    }


}
