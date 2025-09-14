package co.edu.sena.tu_unidad.controller;

import co.edu.sena.tu_unidad.dto.ServerResponseDto;
import co.edu.sena.tu_unidad.dto.ServiceVisitDto;
import co.edu.sena.tu_unidad.service.ServiceVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}


// 