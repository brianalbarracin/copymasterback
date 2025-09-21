package co.edu.sena.tu_unidad.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import co.edu.sena.tu_unidad.domain.enums.CommChannel;
import co.edu.sena.tu_unidad.domain.enums.ServiceType;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRequestDto {
    private Long id;
    private String requestNumber;
    private Long customerId;
    private Long machineId;
    private String companySerial;
    private String companyNumber;
    private Long createdByUserId;
    private OffsetDateTime reportedAt;
    private CommChannel reportedChannel;
    private ServiceType serviceType;
    private String description;
    private String rootCause;
    private String status;
    private Boolean isRepeated;
    private Long repeatedOfRequestId;
    private Long assignedTechnicianId;
    private OffsetDateTime assignedAt;
    private OffsetDateTime closedAt;
    private String resolution;
}


// 