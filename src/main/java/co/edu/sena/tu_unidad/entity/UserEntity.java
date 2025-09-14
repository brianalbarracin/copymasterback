package co.edu.sena.tu_unidad.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String password;

    private String fullName;
    private String role;
    private String phone;
    private String email;
    private String identification;

    private OffsetDateTime createdAt;
    private OffsetDateTime lastLoginAt;
}


// 