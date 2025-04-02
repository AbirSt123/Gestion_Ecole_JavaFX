package org.example.school_management.entities;

import lombok.*;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Inscription {
    private int id;
    private Date dateInscription;
    private Etudiant etudiant;
    private Modules module;
}
