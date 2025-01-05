package org.example.school_management.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Module {
    private int id;
    private String nomModule;
    private String codeModule;
    private int professeurId;
}
