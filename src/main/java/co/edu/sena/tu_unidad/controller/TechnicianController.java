package co.edu.sena.tu_unidad.controller;

import co.edu.sena.tu_unidad.dto.ServerResponseDto;
import co.edu.sena.tu_unidad.dto.TechnicianDto;
import co.edu.sena.tu_unidad.dto.TechnicianPerformanceDto;
import co.edu.sena.tu_unidad.dto.TechnicianStatsDto;
import co.edu.sena.tu_unidad.dto.RepeatedRequestDto;
import co.edu.sena.tu_unidad.dto.VisitTimelineDto;
import co.edu.sena.tu_unidad.service.TechnicianService;
import co.edu.sena.tu_unidad.service.TechnicianPerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.time.OffsetDateTime;

@RestController
@RequestMapping("/technicians")
public class TechnicianController {

    @Autowired
    private TechnicianService technicianService;
    @Autowired
    private TechnicianPerformanceService performanceService;

    @GetMapping
    public ServerResponseDto getAll() {
        List<TechnicianDto> list = technicianService.getAllTechnicians();
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Técnicos obtenidos")
                .data(list)
                .build();
    }

    @GetMapping("/{id}")
    public ServerResponseDto getById(@PathVariable Long id) {
        TechnicianDto t = technicianService.getTechnicianById(id);
        return ServerResponseDto.builder()
                .status(t != null ? HttpStatus.OK.value() : HttpStatus.NOT_FOUND.value())
                .message(t != null ? "Técnico encontrado" : "No encontrado")
                .data(t)
                .build();
    }

    @PostMapping
    public ServerResponseDto create(@RequestBody TechnicianDto dto) {
        TechnicianDto created = technicianService.createTechnician(dto);
        return ServerResponseDto.builder()
                .status(HttpStatus.CREATED.value())
                .message("Técnico creado")
                .data(created)
                .build();
    }

    @PutMapping("/{id}")
    public ServerResponseDto update(@PathVariable Long id, @RequestBody TechnicianDto dto) {
        TechnicianDto updated = technicianService.updateTechnician(id, dto);
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Técnico actualizado")
                .data(updated)
                .build();
    }


    @GetMapping("/{id}/performance")
    public ServerResponseDto getPerformance(@PathVariable Long id) {
        try {
            TechnicianPerformanceDto performance = performanceService.getPerformance(id);
            return ServerResponseDto.builder()
                    .status(HttpStatus.OK.value())
                    .message("Hoja de vida del técnico")
                    .data(performance)
                    .build();
        } catch (Exception e) {
            return ServerResponseDto.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Error al obtener hoja de vida: " + e.getMessage())
                    .data(null)
                    .build();
        }
    }

    // 🔹 Obtener estadísticas por periodo
    @GetMapping("/{id}/stats")
    public ServerResponseDto getStats(@PathVariable Long id,
                                      @RequestParam(required = false) String startDate,
                                      @RequestParam(required = false) String endDate) {
        try {
            OffsetDateTime start = startDate != null ?
                    OffsetDateTime.parse(startDate) :
                    OffsetDateTime.now().minusMonths(1);
            OffsetDateTime end = endDate != null ?
                    OffsetDateTime.parse(endDate) :
                    OffsetDateTime.now();

            TechnicianStatsDto stats = performanceService.getStats(id, start, end);
            return ServerResponseDto.builder()
                    .status(HttpStatus.OK.value())
                    .message("Estadísticas del técnico")
                    .data(stats)
                    .build();
        } catch (Exception e) {
            return ServerResponseDto.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Error al obtener estadísticas: " + e.getMessage())
                    .data(null)
                    .build();
        }
    }

    // 🔹 Obtener solicitudes repetidas
    @GetMapping("/{id}/repeated-requests")
    public ServerResponseDto getRepeatedRequests(@PathVariable Long id) {
        try {
            List<RepeatedRequestDto> repeated = performanceService.getRepeatedRequests(id);
            return ServerResponseDto.builder()
                    .status(HttpStatus.OK.value())
                    .message("Solicitudes repetidas del técnico")
                    .data(repeated)
                    .build();
        } catch (Exception e) {
            return ServerResponseDto.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Error al obtener solicitudes repetidas: " + e.getMessage())
                    .data(null)
                    .build();
        }
    }

    // 🔹 Obtener métricas de tiempo
    @GetMapping("/{id}/time-metrics")
    public ServerResponseDto getTimeMetrics(@PathVariable Long id) {
        try {
            Map<String, Object> metrics = performanceService.getTimeMetrics(id);
            return ServerResponseDto.builder()
                    .status(HttpStatus.OK.value())
                    .message("Métricas de tiempo del técnico")
                    .data(metrics)
                    .build();
        } catch (Exception e) {
            return ServerResponseDto.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Error al obtener métricas de tiempo: " + e.getMessage())
                    .data(null)
                    .build();
        }
    }

    // 🔹 Obtener línea de tiempo de visitas
    @GetMapping("/{id}/timeline")
    public ServerResponseDto getTimeline(@PathVariable Long id,
                                         @RequestParam(defaultValue = "20") int limit) {
        try {
            List<VisitTimelineDto> timeline = performanceService.getRecentVisits(id, limit);
            return ServerResponseDto.builder()
                    .status(HttpStatus.OK.value())
                    .message("Línea de tiempo de visitas")
                    .data(timeline)
                    .build();
        } catch (Exception e) {
            return ServerResponseDto.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Error al obtener línea de tiempo: " + e.getMessage())
                    .data(null)
                    .build();
        }
    }

    // 🔹 Obtener tareas abiertas
    @GetMapping("/{id}/open-tasks")
    public ServerResponseDto getOpenTasks(@PathVariable Long id) {
        try {
            Map<String, Object> openTasks = performanceService.getOpenTasks(id);
            return ServerResponseDto.builder()
                    .status(HttpStatus.OK.value())
                    .message("Tareas abiertas del técnico")
                    .data(openTasks)
                    .build();
        } catch (Exception e) {
            return ServerResponseDto.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Error al obtener tareas abiertas: " + e.getMessage())
                    .data(null)
                    .build();
        }
    }



}


// 