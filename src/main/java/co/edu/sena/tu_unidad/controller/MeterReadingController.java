package co.edu.sena.tu_unidad.controller;

import co.edu.sena.tu_unidad.dto.ServerResponseDto;
import co.edu.sena.tu_unidad.entity.MeterReadingEntity;
import co.edu.sena.tu_unidad.repository.MeterReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meter-readings")
public class MeterReadingController {

    @Autowired
    private MeterReadingRepository repo;

    @GetMapping("/machine/{machineId}")
    public ServerResponseDto readingsByMachine(@PathVariable Long machineId) {
        List<MeterReadingEntity> list = repo.findByMachineIdOrderByReadingDateDesc(machineId);
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Lecturas")
                .data(list)
                .build();
    }

    @PostMapping
    public ServerResponseDto create(@RequestBody MeterReadingEntity e) {
        MeterReadingEntity saved = repo.save(e);
        return ServerResponseDto.builder()
                .status(HttpStatus.CREATED.value())
                .message("Lectura creada")
                .data(saved)
                .build();
    }
}


// 