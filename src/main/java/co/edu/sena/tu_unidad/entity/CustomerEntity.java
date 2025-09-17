package co.edu.sena.tu_unidad.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;

import java.time.OffsetDateTime;

@Entity
@Table(name = "customers")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nit;   // puede ser null si es persona natural

    @Column(nullable = false)
    private String name;

    @Column(name = "contact_name")
    private String contactName;

    private String phone;

    private String email;

    private String address;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private LocationEntity location;
}


// 