package com.telemedicine.authentication.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationEntity {
    @Id
    private String userId;
    private String userName;
    private String email;
    private String password;
    private String role;
}
