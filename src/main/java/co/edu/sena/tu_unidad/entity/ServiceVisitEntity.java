package co.edu.sena.tu_unidad.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "service_visits")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceVisitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long serviceRequestId;
    private OffsetDateTime visitDatetime;
    private Long technicianId;
    private String visitNotes;

    @Column(columnDefinition = "jsonb")
    private String partsUsed; // store JSON string

    private Long meterReadingBefore;
    private Long meterReadingAfter;
    private Boolean solved;
    private OffsetDateTime createdAt;
}


// 