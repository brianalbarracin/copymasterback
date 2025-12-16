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
public class VisitTimelineDto {
    private Long visitId;
    private Long requestId;
    private String requestNumber;
    private OffsetDateTime visitDate;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    private Long durationMinutes;
    private Boolean solved;
    private String notes;
}