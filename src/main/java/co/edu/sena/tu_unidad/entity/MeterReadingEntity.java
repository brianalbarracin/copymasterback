package co.edu.sena.tu_unidad.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "meter_readings")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeterReadingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long machineId;
    private Long reading;
    private OffsetDateTime readingDate;
    private Long technicianId;
    private String notes;
    @OneToOne(mappedBy = "meterReading")
    private ServiceVisitEntity serviceVisit;
}


// 