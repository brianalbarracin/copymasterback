package co.edu.sena.tu_unidad.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private String reportedChannel;
    private String serviceType;
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
}


// 