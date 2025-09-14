package co.edu.sena.tu_unidad.controller;

import co.edu.sena.tu_unidad.dto.ServerResponseDto;
import co.edu.sena.tu_unidad.entity.PartEntity;
import co.edu.sena.tu_unidad.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parts")
public class PartController {

    @Autowired
    private PartRepository repo;

    @GetMapping
    public ServerResponseDto getAll() {
        List<PartEntity> parts = repo.findAll();
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Repuestos")
                .data(parts)
                .build();
    }

    @PostMapping
    public ServerResponseDto create(@RequestBody PartEntity p) {
        PartEntity saved = repo.save(p);
        return ServerResponseDto.builder()
                .status(HttpStatus.CREATED.value())
                .message("Repuesto creado")
                .data(saved)
                .build();
    }
}