package org.example.school_management.entities;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Modules {
    private int id;
    private String nomModule;
    private String codeModule;
    private int professeurId;
    private Professeur professeur;


}

