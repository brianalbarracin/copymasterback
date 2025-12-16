package co.edu.sena.tu_unidad.service.impl;

import co.edu.sena.tu_unidad.dto.*;
import co.edu.sena.tu_unidad.entity.*;
import co.edu.sena.tu_unidad.repository.*;
import co.edu.sena.tu_unidad.service.TechnicianPerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TechnicianPerformanceServiceImpl implements TechnicianPerformanceService {

    private final ServiceVisitRepository visitRepository;
    private final ServiceRequestRepository requestRepository;
    private final TechnicianRepository technicianRepository;
    private final CustomerRepository customerRepository;
    private final MachineRepository machineRepository;
    private final RootCauseRepository rootCauseRepository;

    @Override
    public TechnicianPerformanceDto getPerformance(Long technicianId) {
        TechnicianPerformanceDto dto = new TechnicianPerformanceDto();
        dto.setTechnicianId(technicianId);

        // Obtener todas las visitas del técnico
        List<ServiceVisitEntity> visits = visitRepository.findByTechnicianId(technicianId);
        dto.setTotalVisits(visits.size());

        // Obtener todas las solicitudes donde el técnico hizo al menos una visita
        List<ServiceRequestEntity> allRequests = getRequestsByTechnician(technicianId);

        // Calcular métricas básicas
        calculateBasicMetrics(dto, visits, allRequests);

        // Calcular métricas de tiempo
        calculateTimeMetrics(dto, visits);

        // Calcular solicitudes repetidas
        calculateRepeatedRequests(dto, technicianId);

        // Calcular porcentajes de resolución - PASAR technicianId COMO PARÁMETRO
        calculateResolutionMetrics(dto, visits, allRequests, technicianId);  // <-- CAMBIO AQUÍ

        // Obtener visitas recientes
        dto.setRecentVisits(getRecentVisits(technicianId, 10));

        // Obtener tareas abiertas
        dto.setOpenTasks(getOpenTasks(technicianId));

        return dto;
    }

    private List<ServiceRequestEntity> getRequestsByTechnician(Long technicianId) {
        // Obtener IDs de solicitudes donde el técnico participó
        List<Long> requestIds = visitRepository.findByTechnicianId(technicianId)
                .stream()
                .map(ServiceVisitEntity::getServiceRequestId)
                .distinct()
                .collect(Collectors.toList());

        return requestRepository.findAllById(requestIds);
    }

    private void calculateBasicMetrics(TechnicianPerformanceDto dto,
                                       List<ServiceVisitEntity> visits,
                                       List<ServiceRequestEntity> requests) {
        // Visitas resueltas vs no resueltas
        long solvedVisits = visits.stream()
                .filter(v -> Boolean.TRUE.equals(v.getSolved()))
                .count();
        dto.setSolvedVisits((int)solvedVisits);
        dto.setUnsolvedVisits(visits.size() - (int)solvedVisits);

        // Solicitudes únicas atendidas
        long uniqueRequests = requests.stream()
                .map(ServiceRequestEntity::getId)
                .distinct()
                .count();
        dto.setUniqueRequests(uniqueRequests);

        // Solicitudes completadas (cerradas con visitas de este técnico)
        long completedRequests = requests.stream()
                .filter(r -> "resuelto".equalsIgnoreCase(r.getStatus()) ||
                        "cerrado".equalsIgnoreCase(r.getStatus()))
                .count();
        dto.setCompletedRequests(completedRequests);

        // Solicitudes en progreso
        long inProgressRequests = requests.stream()
                .filter(r -> "en_proceso".equalsIgnoreCase(r.getStatus()) ||
                        "abierto".equalsIgnoreCase(r.getStatus()))
                .count();
        dto.setInProgressRequests(inProgressRequests);
    }

    private void calculateTimeMetrics(TechnicianPerformanceDto dto, List<ServiceVisitEntity> visits) {
        // Filtrar visitas con start_time y end_time válidos
        List<ServiceVisitEntity> timedVisits = visits.stream()
                .filter(v -> v.getStartTime() != null && v.getEndTime() != null)
                .collect(Collectors.toList());

        if (!timedVisits.isEmpty()) {
            // Calcular tiempos promedio
            long totalSeconds = timedVisits.stream()
                    .mapToLong(v -> Duration.between(v.getStartTime(), v.getEndTime()).getSeconds())
                    .sum();

            long avgSeconds = totalSeconds / timedVisits.size();
            dto.setAverageVisitTime(Duration.ofSeconds(avgSeconds));

            // Encontrar la visita más larga y más corta
            timedVisits.stream()
                    .max(Comparator.comparing(v -> Duration.between(v.getStartTime(), v.getEndTime())))
                    .ifPresent(longest -> dto.setLongestVisitTime(
                            Duration.between(longest.getStartTime(), longest.getEndTime())));

            timedVisits.stream()
                    .min(Comparator.comparing(v -> Duration.between(v.getStartTime(), v.getEndTime())))
                    .ifPresent(shortest -> dto.setShortestVisitTime(
                            Duration.between(shortest.getStartTime(), shortest.getEndTime())));
        }

        // Calcular cumplimiento de fechas programadas
        long onTimeVisits = visits.stream()
                .filter(v -> v.getVisitDatetime() != null && v.getStartTime() != null)
                .filter(v -> {
                    Duration diff = Duration.between(v.getVisitDatetime(), v.getStartTime());
                    return Math.abs(diff.toHours()) <= 2; // Considerar a tiempo si llega en +/- 2 horas
                })
                .count();

        if (visits.stream().filter(v -> v.getVisitDatetime() != null).count() > 0) {
            double onTimePercentage = (double) onTimeVisits /
                    visits.stream().filter(v -> v.getVisitDatetime() != null).count() * 100;
            dto.setOnTimePercentage(onTimePercentage);
        }
    }

    private void calculateRepeatedRequests(TechnicianPerformanceDto dto, Long technicianId) {
        // Obtener todas las solicitudes donde el técnico participó
        List<ServiceRequestEntity> technicianRequests = getRequestsByTechnician(technicianId);

        // Agrupar por cliente+máquina+root_cause
        Map<String, List<ServiceRequestEntity>> grouped = technicianRequests.stream()
                .filter(r -> r.getCustomerId() != null && r.getMachineId() != null)
                .collect(Collectors.groupingBy(r ->
                        r.getCustomerId() + "-" + r.getMachineId() + "-" +
                                (r.getRootCauseId() != null ? r.getRootCauseId() : r.getRootCause())
                ));

        // Contar grupos con más de 1 solicitud
        long repeatedCount = grouped.values().stream()
                .filter(list -> list.size() > 1)
                .count();

        long totalGroups = grouped.size();

        if (totalGroups > 0) {
            double repeatedPercentage = (double) repeatedCount / totalGroups * 100;
            dto.setRepeatedRequestsPercentage(repeatedPercentage);
            dto.setRepeatedRequestsCount((int) repeatedCount);
        }
    }

    private void calculateResolutionMetrics(TechnicianPerformanceDto dto,
                                            List<ServiceVisitEntity> visits,
                                            List<ServiceRequestEntity> requests,
                                            Long technicianId) {  // AÑADE ESTE PARÁMETRO
        // Para cada solicitud, determinar quién la abrió y quién la cerró
        Map<Long, List<ServiceVisitEntity>> visitsByRequest = visits.stream()
                .collect(Collectors.groupingBy(ServiceVisitEntity::getServiceRequestId));

        int sameTechnicianOpenedClosed = 0;
        int differentTechnicianClosed = 0;

        for (ServiceRequestEntity request : requests) {
            List<ServiceVisitEntity> requestVisits = visitsByRequest.getOrDefault(request.getId(),
                    Collections.emptyList());

            if (!requestVisits.isEmpty()) {
                // Ordenar visitas por fecha
                requestVisits.sort(Comparator.comparing(ServiceVisitEntity::getVisitDatetime));

                // Técnico de la primera visita (posible apertura)
                Long firstVisitTech = requestVisits.get(0).getTechnicianId();

                // Técnico de la última visita (posible cierre)
                Long lastVisitTech = requestVisits.get(requestVisits.size() - 1).getTechnicianId();

                // Si la solicitud está cerrada y el último técnico fue este
                if (("resuelto".equalsIgnoreCase(request.getStatus()) ||
                        "cerrado".equalsIgnoreCase(request.getStatus())) &&
                        technicianId.equals(lastVisitTech)) {

                    if (technicianId.equals(firstVisitTech)) {
                        sameTechnicianOpenedClosed++;
                    } else {
                        differentTechnicianClosed++;
                    }
                }
            }
        }

        dto.setSameTechnicianOpenedClosed(sameTechnicianOpenedClosed);
        dto.setDifferentTechnicianClosed(differentTechnicianClosed);

        // Calcular porcentaje de éxito
        long totalClosures = sameTechnicianOpenedClosed + differentTechnicianClosed;
        if (totalClosures > 0 && dto.getCompletedRequests() > 0) {
            double closureRate = (double) totalClosures / dto.getCompletedRequests() * 100;
            dto.setClosureRate(closureRate);
        }
    }



    @Override
    public TechnicianStatsDto getStats(Long technicianId, OffsetDateTime startDate, OffsetDateTime endDate) {
        TechnicianStatsDto stats = new TechnicianStatsDto();
        stats.setTechnicianId(technicianId);
        stats.setPeriod(startDate + " to " + endDate);

        // Filtrar visitas por periodo
        List<ServiceVisitEntity> visits = visitRepository.findByTechnicianId(technicianId)
                .stream()
                .filter(v -> v.getVisitDatetime() != null)
                .filter(v -> !v.getVisitDatetime().isBefore(startDate) &&
                        !v.getVisitDatetime().isAfter(endDate))
                .collect(Collectors.toList());

        stats.setVisitsCount(visits.size());

        long solvedCount = visits.stream()
                .filter(v -> Boolean.TRUE.equals(v.getSolved()))
                .count();
        stats.setSolvedCount((int) solvedCount);  // CAST EXPLÍCITO

        // Calcular métricas adicionales...
        return stats;
    }

    @Override
    public List<RepeatedRequestDto> getRepeatedRequests(Long technicianIdParam) {  // Cambia el nombre del parámetro
        List<RepeatedRequestDto> repeated = new ArrayList<>();

        // Obtener solicitudes del técnico
        List<ServiceRequestEntity> technicianRequests = getRequestsByTechnician(technicianIdParam);  // Usa el nuevo nombre

        // Agrupar por cliente+máquina+root_cause
        Map<String, List<ServiceRequestEntity>> grouped = technicianRequests.stream()
                .filter(r -> r.getCustomerId() != null && r.getMachineId() != null)
                .collect(Collectors.groupingBy(r ->
                        r.getCustomerId() + "-" + r.getMachineId() + "-" +
                                (r.getRootCauseId() != null ? r.getRootCauseId() : r.getRootCause())
                ));

        // Procesar grupos con repeticiones
        for (Map.Entry<String, List<ServiceRequestEntity>> entry : grouped.entrySet()) {
            if (entry.getValue().size() > 1) {
                // Ordenar por fecha
                entry.getValue().sort(Comparator.comparing(ServiceRequestEntity::getReportedAt));

                RepeatedRequestDto dto = new RepeatedRequestDto();
                ServiceRequestEntity first = entry.getValue().get(0);
                ServiceRequestEntity last = entry.getValue().get(entry.getValue().size() - 1);

                dto.setCustomerId(first.getCustomerId());
                dto.setMachineId(first.getMachineId());
                dto.setRootCause(first.getRootCause());
                dto.setRootCauseId(first.getRootCauseId());
                dto.setFirstRequestId(first.getId());
                dto.setFirstRequestDate(first.getReportedAt());
                dto.setLastRequestId(last.getId());
                dto.setLastRequestDate(last.getReportedAt());
                dto.setRepeatCount(entry.getValue().size());

                // Calcular días entre fallas
                if (first.getReportedAt() != null && last.getReportedAt() != null) {
                    long daysBetween = ChronoUnit.DAYS.between(
                            first.getReportedAt().toLocalDate(),
                            last.getReportedAt().toLocalDate());
                    dto.setDaysBetweenFailures(daysBetween);
                }

                repeated.add(dto);
            }
        }

        return repeated;
    }

    @Override
    public Map<String, Object> getTimeMetrics(Long technicianId) {
        Map<String, Object> metrics = new HashMap<>();

        List<ServiceVisitEntity> visits = visitRepository.findByTechnicianId(technicianId)
                .stream()
                .filter(v -> v.getStartTime() != null && v.getEndTime() != null)
                .collect(Collectors.toList());

        if (!visits.isEmpty()) {
            // Calcular distribución de tiempos
            Map<String, Long> timeDistribution = visits.stream()
                    .collect(Collectors.groupingBy(v -> {
                        Duration duration = Duration.between(v.getStartTime(), v.getEndTime());
                        long hours = duration.toHours();
                        if (hours < 1) return "< 1 hora";
                        if (hours < 2) return "1-2 horas";
                        if (hours < 4) return "2-4 horas";
                        return "> 4 horas";
                    }, Collectors.counting()));

            metrics.put("timeDistribution", timeDistribution);

            // Calcular promedio por día de la semana
            Map<String, Double> avgByDay = visits.stream()
                    .collect(Collectors.groupingBy(v ->
                                    v.getStartTime().getDayOfWeek().toString(),
                            Collectors.averagingDouble(v ->
                                    Duration.between(v.getStartTime(), v.getEndTime()).toMinutes()
                            )));

            metrics.put("averageByDay", avgByDay);
        }

        return metrics;
    }

    @Override
    public List<VisitTimelineDto> getRecentVisits(Long technicianId, int limit) {
        return visitRepository.findByTechnicianId(technicianId)
                .stream()
                .sorted(Comparator.comparing(ServiceVisitEntity::getVisitDatetime,
                        Comparator.nullsLast(Comparator.reverseOrder())))
                .limit(limit)
                .map(this::convertToTimelineDto)
                .collect(Collectors.toList());
    }

    private VisitTimelineDto convertToTimelineDto(ServiceVisitEntity visit) {
        VisitTimelineDto dto = new VisitTimelineDto();
        dto.setVisitId(visit.getId());
        dto.setRequestId(visit.getServiceRequestId());
        dto.setVisitDate(visit.getVisitDatetime());
        dto.setStartTime(visit.getStartTime());
        dto.setEndTime(visit.getEndTime());
        dto.setSolved(visit.getSolved());
        dto.setNotes(visit.getVisitNotes());

        // Calcular duración si hay tiempos
        if (visit.getStartTime() != null && visit.getEndTime() != null) {
            Duration duration = Duration.between(visit.getStartTime(), visit.getEndTime());
            dto.setDurationMinutes(duration.toMinutes());
        }

        return dto;
    }

    @Override
    public Map<String, Object> getOpenTasks(Long technicianId) {
        Map<String, Object> openTasks = new HashMap<>();

        // Visitas no resueltas
        List<ServiceVisitEntity> unsolvedVisits = visitRepository.findByTechnicianId(technicianId)
                .stream()
                .filter(v -> !Boolean.TRUE.equals(v.getSolved()))
                .collect(Collectors.toList());

        openTasks.put("unsolvedVisits", unsolvedVisits.size());
        openTasks.put("unsolvedVisitsList", unsolvedVisits.stream()
                .map(this::convertToTimelineDto)
                .collect(Collectors.toList()));

        // Solicitudes asignadas no cerradas
        List<ServiceRequestEntity> openRequests = requestRepository.findAll()
                .stream()
                .filter(r -> technicianId.equals(r.getAssignedTechnicianId()))
                .filter(r -> !"resuelto".equalsIgnoreCase(r.getStatus()) &&
                        !"cerrado".equalsIgnoreCase(r.getStatus()))
                .collect(Collectors.toList());

        openTasks.put("openRequests", openRequests.size());

        return openTasks;
    }
}