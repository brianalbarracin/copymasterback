package co.edu.sena.tu_unidad.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceVisitDto {
    private Long id;
    private Long serviceRequestId;
    private OffsetDateTime visitDatetime;
    private Long technicianId;
    private String visitNotes;
    private Map<String, Object> partsUsed;
    private Long meterReadingBefore;
    private Long meterReadingAfter;
    private Boolean solved;
}


// 