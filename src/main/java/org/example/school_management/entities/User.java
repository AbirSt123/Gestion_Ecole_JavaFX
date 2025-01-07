package org.example.school_management.entities;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    private int id;
    private String username;
    private String password;
    private int role;
}
