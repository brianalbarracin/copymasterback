package co.edu.sena.tu_unidad.controller;

import co.edu.sena.tu_unidad.dto.ServerResponseDto;
import co.edu.sena.tu_unidad.dto.ServiceRequestDto;
import co.edu.sena.tu_unidad.entity.ServiceVisitEntity;
import co.edu.sena.tu_unidad.service.ServiceRequestService;
import co.edu.sena.tu_unidad.service.ServiceVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import co.edu.sena.tu_unidad.domain.enums.ServiceType;

import java.util.List;
import java.util.Collections;

@RestController
@RequestMapping("/service-requests")
public class ServiceRequestController {

    @Autowired
    private ServiceRequestService service;

    @Autowired
    private ServiceVisitService visitService;

    @GetMapping
    public ServerResponseDto getAll() {
        System.out.println("📋 GET /service-requests - Obteniendo todas las solicitudes");
        System.out.println("📋 Hora: " + new java.util.Date());

        try {
            List<ServiceRequestDto> requests = service.getAllServiceRequests();
            System.out.println("✅ Solicitudes obtenidas: " + requests.size());

            if (requests.isEmpty()) {
                System.out.println("⚠️ No hay solicitudes en la base de datos");
                // Devuelve una lista vacía en lugar de null
                return ServerResponseDto.builder()
                        .status(HttpStatus.OK.value())
                        .message("No hay solicitudes")
                        .data(Collections.emptyList())
                        .build();
            }

            System.out.println("📋 Primera solicitud ID: " + requests.get(0).getId());
            System.out.println("📋 Primera solicitud RequestNumber: " + requests.get(0).getRequestNumber());
            System.out.println("📋 Primera solicitud ServiceType: " + requests.get(0).getServiceType());

            ServerResponseDto response = ServerResponseDto.builder()
                    .status(HttpStatus.OK.value())
                    .message("Solicitudes obtenidas")
                    .data(requests)
                    .build();

            System.out.println("✅ Response creado, status: " + response.getStatus());
            return response;
        } catch (Exception e) {
            System.out.println("❌ Error obteniendo solicitudes: " + e.getMessage());
            e.printStackTrace();
            return ServerResponseDto.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Error al obtener solicitudes: " + e.getMessage())
                    .data(null)
                    .build();
        }
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

    @GetMapping("/test")
    public ServerResponseDto test() {
        System.out.println("🧪 Probando endpoint /test");

        try {
            // Crear un DTO simple para prueba
            ServiceRequestDto testDto = ServiceRequestDto.builder()
                    .id(1L)
                    .requestNumber("001ST091225")
                    .customerId(1L)
                    .machineId(1L)
                    .serviceType(ServiceType.servicio_tecnico)  // Usa tu enum
                    .status("ABIERTA")
                    .build();

            System.out.println("🧪 DTO de prueba creado: " + testDto);

            return ServerResponseDto.builder()
                    .status(HttpStatus.OK.value())
                    .message("Test exitoso")
                    .data(testDto)
                    .build();
        } catch (Exception e) {
            System.out.println("❌ Error en test: " + e.getMessage());
            e.printStackTrace();
            return ServerResponseDto.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Error en test: " + e.getMessage())
                    .data(null)
                    .build();
        }
    }




}


// 