package co.edu.sena.tu_unidad.controller;

import co.edu.sena.tu_unidad.dto.ServerResponseDto;
import co.edu.sena.tu_unidad.dto.TechnicianDto;
import co.edu.sena.tu_unidad.service.TechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/technicians")
public class TechnicianController {

    @Autowired
    private TechnicianService technicianService;

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
}


// 