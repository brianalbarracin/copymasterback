package co.edu.sena.tu_unidad.service.impl;

import co.edu.sena.tu_unidad.dto.MachineDto;
import co.edu.sena.tu_unidad.entity.MachineEntity;
import co.edu.sena.tu_unidad.repository.MachineRepository;
import co.edu.sena.tu_unidad.repository.LocationRepository;
import co.edu.sena.tu_unidad.repository.MeterReadingRepository;
import co.edu.sena.tu_unidad.entity.MeterReadingEntity;
import co.edu.sena.tu_unidad.service.MachineService;
import co.edu.sena.tu_unidad.repository.MachineMovementRepository;
import co.edu.sena.tu_unidad.entity.MachineMovementEntity;
import co.edu.sena.tu_unidad.domain.enums.MovementType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MachineServiceImpl implements MachineService {

    @Autowired
    private MachineRepository machineRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private MachineMovementRepository movementRepo;

    @Autowired
    private MeterReadingRepository meterReadingRepository;

    private MachineDto toDto(MachineEntity e) {
        if (e == null) return null;
        return MachineDto.builder()
                .id(e.getId())
                .companySerial(e.getCompanySerial())
                .companyNumber(e.getCompanyNumber())
                .model(e.getModel())
                .brand(e.getBrand())
                .year(e.getYear())
                .currentLocationId(e.getCurrentLocationId())
                .status(e.getStatus())
                .currentCustomerId(e.getCurrentCustomerId())
                .notes(e.getNotes())
                .build();
    }

    @Override
    public List<MachineDto> getAllMachines() {
        return machineRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public MachineDto getMachineById(Long id) {
        return machineRepository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public MachineDto createMachine(MachineDto dto) {
        MachineEntity e = new MachineEntity();
        e.setCompanySerial(dto.getCompanySerial());
        e.setCompanyNumber(dto.getCompanyNumber());
        e.setModel(dto.getModel());
        e.setBrand(dto.getBrand());
        e.setYear(dto.getYear());
        e.setCurrentLocationId(dto.getCurrentLocationId());
        if (dto.getStatus() != null) {
            e.setStatus(dto.getStatus());
        }
        e.setCurrentCustomerId(dto.getCurrentCustomerId());
        e.setPurchaseDate(null);
        e.setCreatedAt(OffsetDateTime.now());
        e.setNotes(dto.getNotes());

        // Guardar máquina
        e = machineRepository.save(e);

        // ✅ Crear primer movimiento con los valores correctos
        if (e.getId() != null) {
            MachineMovementEntity move = MachineMovementEntity.builder()
                    .machineId(e.getId())
                    .fromLocationId(null) // primera vez
                    .toLocationId(e.getCurrentLocationId())
                    .movementType(MovementType.INGRESO) // tu enum
                    .reason("Ubicación inicial")
                    .createdByUserId(1L) // opcional, si tienes usuario
                    .effectiveDate(OffsetDateTime.now())
                    .createdAt(OffsetDateTime.now())
                    .build();

            movementRepo.save(move);
        }

        // ✅ Si viene lectura inicial, la guardamos
        if (e.getId() != null && dto.getInitialReading() != null) {
            MeterReadingEntity mr = MeterReadingEntity.builder()
                    .machineId(e.getId())
                    .reading(dto.getInitialReading())
                    .colorReading(dto.getInitialColorReading())     // si lo quieres manejar
                    .scannerReading(dto.getInitialScannerReading()) // si lo quieres manejar
                    .readingDate(OffsetDateTime.now())
                    .notes(dto.getReadingNotes() != null ? dto.getReadingNotes() : "Lectura inicial")
                    .build();
            meterReadingRepository.save(mr);
        }

        return toDto(e);
    }

    @Override
    public MachineDto updateMachine(Long id, MachineDto dto) {
        MachineEntity e = machineRepository.findById(id).orElse(null);
        if (e == null) return null;
        e.setModel(dto.getModel());
        e.setBrand(dto.getBrand());
        e.setCompanyNumber(dto.getCompanyNumber());
        e.setCurrentLocationId(dto.getCurrentLocationId());

        e.setCurrentCustomerId(dto.getCurrentCustomerId());
        if (dto.getStatus() != null) {
            e.setStatus(dto.getStatus());  // ✅ ahora enum
        }
        e.setNotes(dto.getNotes());
        machineRepository.save(e);
        return toDto(e);
    }

    @Override
    public void deleteMachine(Long id) {
        machineRepository.deleteById(id);
    }

    @Override
    public List<MachineDto> searchByModel(String model) {
        return machineRepository.findByModel(model).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public MachineDto findByCompanySerial(String serial) {
        return machineRepository.findByCompanySerial(serial).map(this::toDto).orElse(null);
    }
}


// 