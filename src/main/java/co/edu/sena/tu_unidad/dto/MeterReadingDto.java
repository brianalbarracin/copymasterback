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
public class MeterReadingDto {

    private Long id;
    private Long machineId;
    private Long reading;
    private OffsetDateTime readingDate;
    private Long technicianId;
    private String notes;
    private Long colorReading;
    private Long scannerReading;
}
