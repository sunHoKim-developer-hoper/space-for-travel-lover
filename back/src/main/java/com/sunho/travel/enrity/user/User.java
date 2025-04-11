package com.sunho.travel.enrity.user;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "USERS")
@Data
public class User {
    
    @Id
    private String id;
    private String password;
    private String name;
    private LocalDate birthDate;
    private String role;

}
