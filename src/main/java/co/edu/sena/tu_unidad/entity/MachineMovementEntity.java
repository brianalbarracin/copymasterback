package co.edu.sena.tu_unidad.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "machine_movements")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MachineMovementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long machineId;
    private Long fromLocationId;
    private Long toLocationId;
    private String movementType;
    private OffsetDateTime effectiveDate;
    private String reason;
    private Long relatedContractId;
    private Long createdByUserId;
    private OffsetDateTime createdAt;
}


// 