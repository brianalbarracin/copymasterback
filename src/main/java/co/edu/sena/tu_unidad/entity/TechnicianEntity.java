package co.edu.sena.tu_unidad.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

import java.time.LocalDate;

@Entity
@Table(name = "technicians")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TechnicianEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identification;
    private String fullName;
    private String phone;
    private String email;
    private String address;
    private LocalDate hireDate;
    private Boolean active;
    private String certifications;
    private OffsetDateTime createdAt;
}


// 