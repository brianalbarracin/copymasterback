package co.edu.sena.tu_unidad.service.impl;

import co.edu.sena.tu_unidad.dto.ReadingTonerDto;
import co.edu.sena.tu_unidad.entity.ReadingTonerEntity;
import co.edu.sena.tu_unidad.repository.ReadingTonerRepository;
import co.edu.sena.tu_unidad.service.ReadingTonerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReadingTonerServiceImpl implements ReadingTonerService{
    @Autowired
    private ReadingTonerRepository repository;

    private ReadingTonerDto toDto(ReadingTonerEntity entity) {
        if (entity == null) return null;
        return ReadingTonerDto.builder()
                .id(entity.getId())
                .machineId(entity.getMachineId())
                .technicianId(entity.getTechnicianId())
                .readingDate(entity.getReadingDate())
                .notes(entity.getNotes())
                .levelK(entity.getLevelK())
                .levelC(entity.getLevelC())
                .levelM(entity.getLevelM())
                .levelY(entity.getLevelY())
                .build();
    }

    private ReadingTonerEntity toEntity(ReadingTonerDto dto) {
        if (dto == null) return null;
        return ReadingTonerEntity.builder()
                .id(dto.getId())
                .machineId(dto.getMachineId())
                .technicianId(dto.getTechnicianId())
                .readingDate(dto.getReadingDate() != null ? dto.getReadingDate() : OffsetDateTime.now())
                .notes(dto.getNotes())
                .levelK(dto.getLevelK())
                .levelC(dto.getLevelC())
                .levelM(dto.getLevelM())
                .levelY(dto.getLevelY())
                .build();
    }

    @Override
    public List<ReadingTonerDto> getReadingsByMachine(Long machineId) {
        return repository.findByMachineIdOrderByReadingDateDesc(machineId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReadingTonerDto createReadingToner(ReadingTonerDto dto) {
        ReadingTonerEntity entity = toEntity(dto);
        entity = repository.save(entity);
        return toDto(entity);
    }

    @Override
    public ReadingTonerDto updateReadingToner(Long id, ReadingTonerDto dto) {
        return repository.findById(id)
                .map(existing -> {
                    if (dto.getLevelK() != null) existing.setLevelK(dto.getLevelK());
                    if (dto.getLevelC() != null) existing.setLevelC(dto.getLevelC());
                    if (dto.getLevelM() != null) existing.setLevelM(dto.getLevelM());
                    if (dto.getLevelY() != null) existing.setLevelY(dto.getLevelY());
                    if (dto.getNotes() != null) existing.setNotes(dto.getNotes());
                    if (dto.getReadingDate() != null) existing.setReadingDate(dto.getReadingDate());

                    ReadingTonerEntity saved = repository.save(existing);
                    return toDto(saved);
                })
                .orElseThrow(() -> new RuntimeException("Lectura de toner no encontrada con id: " + id));
    }

    @Override
    public void deleteReadingToner(Long id) {
        repository.deleteById(id);
    }

    @Override
    public ReadingTonerDto getReadingTonerById(Long id) {
        return repository.findById(id)
                .map(this::toDto)
                .orElse(null);
    }


}
