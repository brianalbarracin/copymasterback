package co.edu.sena.tu_unidad.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TechnicianPerformanceDto {
    private Long technicianId;
    private String technicianName;

    // Métricas básicas
    private int totalVisits;
    private int solvedVisits;
    private int unsolvedVisits;
    private long uniqueRequests;
    private long completedRequests;
    private long inProgressRequests;

    // Métricas de tiempo
    private Duration averageVisitTime;
    private Duration longestVisitTime;
    private Duration shortestVisitTime;
    private double onTimePercentage; // % de visitas que empezaron en la hora programada

    // Métricas de repetición
    private double repeatedRequestsPercentage;
    private int repeatedRequestsCount;

    // Métricas de resolución
    private int sameTechnicianOpenedClosed; // Mismo técnico abrió y cerró
    private int differentTechnicianClosed; // Otro técnico cerró
    private double closureRate; // % de solicitudes que cerró

    // Datos adicionales
    private List<VisitTimelineDto> recentVisits;
    private Map<String, Object> openTasks;
    private Map<String, Object> timeMetrics;
}