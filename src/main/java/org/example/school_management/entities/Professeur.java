package org.example.school_management.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Professeur {
    private int id;
    private String nom;
    private String prenom;
    private String specialite;
    private int userId;
}
