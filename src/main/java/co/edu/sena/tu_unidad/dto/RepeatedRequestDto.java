package co.edu.sena.tu_unidad.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RepeatedRequestDto {
    private Long customerId;
    private String customerName;
    private Long machineId;
    private String machineSerial;
    private String rootCause;
    private Long rootCauseId;
    private Long firstRequestId;
    private OffsetDateTime firstRequestDate;
    private Long lastRequestId;
    private OffsetDateTime lastRequestDate;
    private int repeatCount;
    private long daysBetweenFailures;
}
