package co.edu.sena.tu_unidad.controller;

import co.edu.sena.tu_unidad.dto.MachineDto;
import co.edu.sena.tu_unidad.dto.ServerResponseDto;
import co.edu.sena.tu_unidad.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/machines")
public class MachineController {

    @Autowired
    private MachineService machineService;

    @GetMapping
    public ServerResponseDto getAll() {
        List<MachineDto> list = machineService.getAllMachines();
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Máquinas obtenidas")
                .data(list)
                .build();
    }

    @GetMapping("/{id}")
    public ServerResponseDto getById(@PathVariable Long id) {
        MachineDto m = machineService.getMachineById(id);
        return ServerResponseDto.builder()
                .status(m != null ? HttpStatus.OK.value() : HttpStatus.NOT_FOUND.value())
                .message(m != null ? "Máquina encontrada" : "No encontrada")
                .data(m)
                .build();
    }

    @GetMapping("/search")
    public ServerResponseDto searchByModel(@RequestParam String model) {
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Resultados")
                .data(machineService.searchByModel(model))
                .build();
    }

    @PostMapping
    public ServerResponseDto create(@RequestBody MachineDto dto) {
        MachineDto created = machineService.createMachine(dto);
        return ServerResponseDto.builder()
                .status(HttpStatus.CREATED.value())
                .message("Máquina creada")
                .data(created)
                .build();
    }

    @PutMapping("/{id}")
    public ServerResponseDto update(@PathVariable Long id, @RequestBody MachineDto dto) {
        MachineDto updated = machineService.updateMachine(id, dto);
        return ServerResponseDto.builder()
                .status(updated != null ? HttpStatus.OK.value() : HttpStatus.NOT_FOUND.value())
                .message(updated != null ? "Máquina actualizada" : "No encontrada")
                .data(updated)
                .build();
    }

    @DeleteMapping("/{id}")
    public ServerResponseDto delete(@PathVariable Long id) {
        machineService.deleteMachine(id);
        return ServerResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Máquina eliminada")
                .data(null)
                .build();
    }
}


// 