package co.edu.sena.tu_unidad.controller;

import co.edu.sena.tu_unidad.dto.ServerResponseDto;
import co.edu.sena.tu_unidad.dto.ServiceRequestDto;
import co.edu.sena.tu_unidad.entity.ServiceVisitEntity;
import co.edu.sena.tu_unidad.service.ServiceRequestService;
import co.edu.sena.tu_unidad.service.ServiceVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service-requests")
public class ServiceRequestController {

    @Autowired
    private ServiceRequestService service;

    @Autowired
    private ServiceVisitService visitService;

    @GetMapping
    public ServerResponseDto getAll() {
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Solicitudes obtenidas")
                .data(service.getAllServiceRequests())
                .build();
    }

    @GetMapping("/pending")
    public ServerResponseDto getPending() {
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Pendientes")
                .data(service.getPendingServiceRequests())
                .build();
    }


    @GetMapping("/{id}")
    public ServerResponseDto getById(@PathVariable Long id) {
        ServiceRequestDto request = service.getServiceRequestById(id);
        if (request == null) {
            return ServerResponseDto.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Solicitud no encontrada")
                    .data(null)
                    .build();
        }
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Solicitud obtenida")
                .data(request)
                .build();
    }

    @GetMapping("/customer/{customerId}")
    public ServerResponseDto byCustomer(@PathVariable Long customerId) {
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Solicitudes por cliente")
                .data(service.getServiceRequestsByCustomer(customerId))
                .build();
    }

    @PostMapping
    public ServerResponseDto create(@RequestBody ServiceRequestDto dto) {
        ServiceRequestDto created = service.createServiceRequest(dto);
        return ServerResponseDto.builder()
                .status(HttpStatus.CREATED.value())
                .message("Solicitud creada")
                .data(created)
                .build();
    }

    @PostMapping("/{requestId}/visit")
    public ServerResponseDto addVisit(@PathVariable Long requestId, @RequestBody ServiceVisitEntity visit) {
        // Endpoint simple que delega a serviceVisit
        return ServerResponseDto.builder()
                .status(HttpStatus.CREATED.value())
                .message("Visita agregada (usa /visits para detalle)")
                .data(null)
                .build();
    }

    @PutMapping("/{requestId}/status")
    public ServerResponseDto updateStatus(@PathVariable Long requestId, @RequestParam String status) {
        ServiceRequestDto updated = service.updateServiceRequestStatus(requestId, status);
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Estado actualizado")
                .data(updated)
                .build();
    }
    @GetMapping("/machine/{machineId}")
    public ServerResponseDto byMachine(@PathVariable Long machineId) {
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Solicitudes por máquina")
                .data(service.getServiceRequestsByMachine(machineId)) // necesitas implementarlo en ServiceRequestService
                .build();
    }


}


// 