package co.edu.sena.tu_unidad.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "parts")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;
    private String brand;
    private String modelCompatibility;
    private String name;
    private String partType;
    private Integer stock;
    private java.math.BigDecimal unitPrice;
    private String notes;
    private OffsetDateTime createdAt;
}


// 