package co.edu.sena.tu_unidad.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "root_causes")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RootCauseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String category;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = OffsetDateTime.now();
        }
    }


}
