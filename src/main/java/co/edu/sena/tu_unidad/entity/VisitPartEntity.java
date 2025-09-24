package co.edu.sena.tu_unidad.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "visit_parts")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VisitPartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_visit_id", nullable = false)
    private ServiceVisitEntity serviceVisit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id", nullable = false)
    private PartEntity part;

    @Column(nullable = false)
    private Integer qty;

    private String serialNumber;

    private BigDecimal cost;

    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;
}
