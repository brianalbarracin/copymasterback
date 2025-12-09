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
    private Boolean takeMeterReading;
    private Long reading;        // valor de la lectura
    private Long colorReading;
    private Long scannerReading;
    private String readingNotes;
    private Long machineId;
}


// 