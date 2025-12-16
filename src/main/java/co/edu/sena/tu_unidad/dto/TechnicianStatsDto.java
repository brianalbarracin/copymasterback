package co.edu.sena.tu_unidad.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TechnicianStatsDto {
    private Long technicianId;
    private String period;
    private int visitsCount;
    private int solvedCount;
    private double averageTimePerVisit; // en minutos
    private double onTimeRate; // % de visitas a tiempo
    private int repeatedIssuesCount;
    private double successRate; // % de problemas resueltos permanentemente
}
