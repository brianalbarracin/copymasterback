package co.edu.sena.tu_unidad.controller;
import co.edu.sena.tu_unidad.dto.ReadingTonerDto;
import co.edu.sena.tu_unidad.dto.ServerResponseDto;
import co.edu.sena.tu_unidad.service.ReadingTonerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reading-toner")
public class ReadingTonerController {
    @Autowired
    private ReadingTonerService readingTonerService;

    @GetMapping("/machine/{machineId}")
    public ServerResponseDto getReadingsByMachine(@PathVariable Long machineId) {
        List<ReadingTonerDto> readings = readingTonerService.getReadingsByMachine(machineId);
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Lecturas de toner obtenidas")
                .data(readings)
                .build();
    }

    @PostMapping
    public ServerResponseDto createReading(@RequestBody ReadingTonerDto dto) {
        ReadingTonerDto created = readingTonerService.createReadingToner(dto);
        return ServerResponseDto.builder()
                .status(HttpStatus.CREATED.value())
                .message("Lectura de toner creada")
                .data(created)
                .build();
    }

    @GetMapping("/{id}")
    public ServerResponseDto getReadingById(@PathVariable Long id) {
        ReadingTonerDto reading = readingTonerService.getReadingTonerById(id);
        return ServerResponseDto.builder()
                .status(reading != null ? HttpStatus.OK.value() : HttpStatus.NOT_FOUND.value())
                .message(reading != null ? "Lectura encontrada" : "No encontrada")
                .data(reading)
                .build();
    }

    @PutMapping("/{id}")
    public ServerResponseDto updateReading(@PathVariable Long id, @RequestBody ReadingTonerDto dto) {
        ReadingTonerDto updated = readingTonerService.updateReadingToner(id, dto);
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Lectura de toner actualizada")
                .data(updated)
                .build();
    }

    @DeleteMapping("/{id}")
    public ServerResponseDto deleteReading(@PathVariable Long id) {
        readingTonerService.deleteReadingToner(id);
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Lectura de toner eliminada")
                .data(null)
                .build();
    }


}
