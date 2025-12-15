package co.edu.sena.tu_unidad.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Map;

import java.time.OffsetDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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


    @JdbcTypeCode(SqlTypes.JSON)  // 👈 así Hibernate sabe que es jsonb
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> partsUsed; // store JSON string

    private Long meterReadingBefore;
    private Long meterReadingAfter;
    private Boolean solved;
    private OffsetDateTime createdAt;
    @OneToOne
    @JoinColumn(name = "meter_reading_id")
    private MeterReadingEntity meterReading;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // NUEVO: Campo para lectura de toner
    @Column(name = "toner_reading_id")
    private Long tonerReadingId;

    // NUEVO: Campo para causa raíz específica de la visita
    @Column(name = "root_cause_id")
    private Long rootCauseId;

    // Relación opcional con ReadingTonerEntity
    @OneToOne
    @JoinColumn(name = "toner_reading_id", insertable = false, updatable = false)
    private ReadingTonerEntity tonerReading;

    // NUEVO: Relación con RootCauseEntity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "root_cause_id", insertable = false, updatable = false)
    private RootCauseEntity rootCause;



    @Column(name = "start_time")
    private OffsetDateTime startTime;

    @Column(name = "end_time")
    private OffsetDateTime endTime;

    // Callbacks para actualizar timestamps
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = OffsetDateTime.now();
        }
        if (this.updatedAt == null) {
            this.updatedAt = OffsetDateTime.now();
        }
    }

    // Método auxiliar para establecer la relación con tonerReading
    public void assignTonerReading(ReadingTonerEntity tonerReading) {
        this.tonerReading = tonerReading;
        this.tonerReadingId = tonerReading != null ? tonerReading.getId() : null;
    }

    // NUEVO: Método auxiliar para establecer la relación con rootCause
    public void assignRootCause(RootCauseEntity rootCause) {
        this.rootCause = rootCause;
        this.rootCauseId = rootCause != null ? rootCause.getId() : null;
    }


}


// 