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
}


// 