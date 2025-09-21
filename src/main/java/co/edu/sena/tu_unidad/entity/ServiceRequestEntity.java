package co.edu.sena.tu_unidad.entity;

import jakarta.persistence.*;
import lombok.*;
import co.edu.sena.tu_unidad.domain.enums.CommChannel;
import co.edu.sena.tu_unidad.domain.enums.ServiceType;

import java.time.OffsetDateTime;

@Entity
@Table(name = "service_requests")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String requestNumber;

    private Long customerId;
    private Long machineId;
    private String companySerial;
    private String companyNumber;
    private Long createdByUserId;
    private OffsetDateTime reportedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "reported_channel")
    private CommChannel reportedChannel;


    @Enumerated(EnumType.STRING)
    @Column(name = "service_type")
    private ServiceType serviceType;

    @Column(columnDefinition = "text")
    private String description;
    private String rootCause;
    private String status;
    private Boolean isRepeated;
    private Long repeatedOfRequestId;
    private Long assignedTechnicianId;
    private OffsetDateTime assignedAt;
    private OffsetDateTime closedAt;

    @Column(columnDefinition = "text")
    private String resolution;
    private OffsetDateTime createdAt;

    // 👉 contador estático para requestNumber
    private static long requestCounter = 2000;

    @PrePersist
    public void prePersist() {
        if (this.assignedAt == null) {
            this.assignedAt = OffsetDateTime.now();
        }
        if (this.createdAt == null) {
            this.createdAt = OffsetDateTime.now();
        }
        if (this.reportedAt == null) {
            this.reportedAt = OffsetDateTime.now();
        }
        if (this.status == null) {
            this.status = "ABIERTA";
        }
        if (this.requestNumber == null) {
            this.requestNumber = String.valueOf(requestCounter++);
        }
    }
}


// 