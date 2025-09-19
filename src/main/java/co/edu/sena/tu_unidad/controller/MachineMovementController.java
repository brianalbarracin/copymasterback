package co.edu.sena.tu_unidad.controller;

import co.edu.sena.tu_unidad.dto.ServerResponseDto;
import co.edu.sena.tu_unidad.entity.MachineMovementEntity;
import co.edu.sena.tu_unidad.entity.MachineEntity;
import co.edu.sena.tu_unidad.repository.MachineRepository;
import co.edu.sena.tu_unidad.domain.enums.MovementType;
import co.edu.sena.tu_unidad.repository.MachineMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/machine-movements")
@RequiredArgsConstructor

public class MachineMovementController {

    private final MachineMovementRepository repo;
    private final MachineRepository machineRepo;

    @GetMapping
    public ServerResponseDto listByMachine(@RequestParam Long machineId) {
        return ServerResponseDto.builder()
                .status(200)
                .message("Movimientos obtenidos")
                .data(repo.findByMachineIdOrderByEffectiveDateDesc(machineId))
                .build();
    }

    @PostMapping
    public ServerResponseDto create(@RequestBody MachineMovementEntity move) {
        move.setEffectiveDate(OffsetDateTime.now());
        if (move.getMovementType() == null) {
            move.setMovementType(MovementType.TRASLADO); // o usa tu enum si corresponde
        }

        // Guardar movimiento
        MachineMovementEntity saved = repo.save(move);

        // Actualizar ubicación actual en la máquina
        MachineEntity machine = machineRepo.findById(move.getMachineId()).orElse(null);
        if (machine != null && move.getToLocationId() != null) {
            machine.setCurrentLocationId(move.getToLocationId());
            machineRepo.save(machine);
        }

        return ServerResponseDto.builder()
                .status(201)
                .message("Movimiento creado")
                .data(saved)
                .build();
    }
}
