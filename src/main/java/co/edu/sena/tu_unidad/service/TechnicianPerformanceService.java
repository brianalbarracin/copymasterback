package co.edu.sena.tu_unidad.service;

import co.edu.sena.tu_unidad.dto.TechnicianPerformanceDto;
import co.edu.sena.tu_unidad.dto.TechnicianStatsDto;
import co.edu.sena.tu_unidad.dto.RepeatedRequestDto;
import co.edu.sena.tu_unidad.dto.VisitTimelineDto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

public interface TechnicianPerformanceService {

    TechnicianPerformanceDto getPerformance(Long technicianId);
    TechnicianStatsDto getStats(Long technicianId, OffsetDateTime startDate, OffsetDateTime endDate);
    List<RepeatedRequestDto> getRepeatedRequests(Long technicianId);
    Map<String, Object> getTimeMetrics(Long technicianId);
    List<VisitTimelineDto> getRecentVisits(Long technicianId, int limit);
    Map<String, Object> getOpenTasks(Long technicianId);
}
