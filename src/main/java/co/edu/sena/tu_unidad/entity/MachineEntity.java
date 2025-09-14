package co.edu.sena.tu_unidad.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "machines")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MachineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_serial", unique = true)
    private String companySerial;

    @Column(name = "company_number", unique = true)
    private String companyNumber;

    private String model;
    private String brand;
    private Integer year;

    @Column(name = "current_location_id")
    private Long currentLocationId;

    private String status;

    @Column(name = "current_customer_id")
    private Long currentCustomerId;

    private OffsetDateTime purchaseDate;
    private OffsetDateTime createdAt;
    private String notes;
}


// 