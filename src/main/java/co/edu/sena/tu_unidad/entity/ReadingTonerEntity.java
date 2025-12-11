package co.edu.sena.tu_unidad.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
@Entity
@Table(name = "reading_toner")
@Data  // ← Esto genera getters y setters automáticamente
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ReadingTonerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "machine_id", nullable = false)
    private Long machineId;

    @Column(name = "technician_id")
    private Long technicianId;

    @Column(name = "reading_date", nullable = false)
    private OffsetDateTime readingDate;

    private String notes;

    @Column(name = "level_k")
    private Integer levelK;  // Black (0-100%)

    @Column(name = "level_c")
    private Integer levelC;  // Cyan (0-100%)

    @Column(name = "level_m")
    private Integer levelM;  // Magenta (0-100%)

    @Column(name = "level_y")
    private Integer levelY;  // Yellow (0-100%)

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (this.readingDate == null) {
            this.readingDate = OffsetDateTime.now();
        }
        if (this.createdAt == null) {
            this.createdAt = OffsetDateTime.now();
        }
    }
}
