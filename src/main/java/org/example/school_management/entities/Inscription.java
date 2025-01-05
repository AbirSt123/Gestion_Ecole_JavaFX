package org.example.school_management.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inscription {
    private int id;
    private int etudiantId;
    private int moduleId;
    private Date dateInscription;
}
