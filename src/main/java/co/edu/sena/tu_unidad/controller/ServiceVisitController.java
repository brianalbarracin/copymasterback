package co.edu.sena.tu_unidad.controller;

import co.edu.sena.tu_unidad.dto.ServerResponseDto;
import co.edu.sena.tu_unidad.dto.ServiceVisitDto;
import co.edu.sena.tu_unidad.entity.ServiceVisitEntity;
import co.edu.sena.tu_unidad.service.ServiceVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/visits")
public class ServiceVisitController {

    @Autowired
    private ServiceVisitService service;

    @PostMapping
    public ServerResponseDto create(@RequestBody ServiceVisitDto dto) {
        ServiceVisitDto created = service.createServiceVisit(dto);
        return ServerResponseDto.builder()
                .status(HttpStatus.CREATED.value())
                .message("Visita creada")
                .data(created)
                .build();
    }


    // Actualizar visita
    @PutMapping("/{id}")
    public ResponseEntity<ServerResponseDto> updateVisit(@PathVariable Long id,
                                                         @RequestBody ServiceVisitDto dto) {
        ServiceVisitDto updated = service.updateServiceVisit(id, dto);
        return ResponseEntity.ok(ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Visita actualizada")
                .data(updated)
                .build());
    }

    // Listar visitas por service_request_id
    @GetMapping("/request/{requestId}")
    public List<ServiceVisitDto> getByRequest(@PathVariable Long requestId) {
        return service.getVisitsByRequest(requestId);  // ✅ correcto
    }
}


// 