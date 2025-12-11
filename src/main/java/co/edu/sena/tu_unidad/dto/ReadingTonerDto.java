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

public class ReadingTonerDto {
    private Long id;
    private Long machineId;
    private Long technicianId;
    private OffsetDateTime readingDate;
    private String notes;
    private Integer levelK;  // Black
    private Integer levelC;  // Cyan
    private Integer levelM;  // Magenta
    private Integer levelY;  // Yellow
}
