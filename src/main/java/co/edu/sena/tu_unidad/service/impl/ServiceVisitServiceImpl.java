package co.edu.sena.tu_unidad.service.impl;

import co.edu.sena.tu_unidad.dto.ServiceVisitDto;
import co.edu.sena.tu_unidad.entity.ServiceVisitEntity;
import co.edu.sena.tu_unidad.repository.ServiceVisitRepository;
import co.edu.sena.tu_unidad.service.ServiceVisitService;
import co.edu.sena.tu_unidad.entity.MeterReadingEntity;
import co.edu.sena.tu_unidad.repository.MeterReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceVisitServiceImpl implements ServiceVisitService {

    @Autowired
    private ServiceVisitRepository repo;


    @Autowired
    private MeterReadingRepository meterReadingRepository;

    @Override
    public ServiceVisitDto updateServiceVisit(Long id, ServiceVisitDto dto) {
        return repo.findById(id)
                .map(existing -> {
                    existing.setTechnicianId(dto.getTechnicianId());
                    existing.setVisitNotes(dto.getVisitNotes());
                    existing.setSolved(dto.getSolved() != null ? dto.getSolved() : false);

                    // Manejo de medición
                    if (Boolean.TRUE.equals(dto.getTakeMeterReading()) && dto.getReading() != null) {
                        MeterReadingEntity reading = new MeterReadingEntity();
                        reading.setReading(dto.getReading());
                        reading.setReadingDate(OffsetDateTime.now());
                        reading.setTechnicianId(dto.getTechnicianId());
                        reading.setNotes(dto.getReadingNotes());

                        // si tu ServiceVisit tiene referencia a machineId, puedes usarla aquí
                        reading.setMachineId(existing.getServiceRequestId()); // ⚠️ ajustar según tu modelo

                        meterReadingRepository.save(reading);
                        existing.setMeterReading(reading);
                    }

                    if (dto.getPartsUsed() != null) {
                        existing.setPartsUsed(dto.getPartsUsed());
                    }

                    ServiceVisitEntity saved = repo.save(existing);
                    return toDto(saved);
                })
                .orElseThrow(() -> new RuntimeException("Visita no encontrada con id " + id));
    }






    private ServiceVisitDto toDto(ServiceVisitEntity e) {
        if (e == null) return null;
        return ServiceVisitDto.builder()
                .id(e.getId())
                .serviceRequestId(e.getServiceRequestId())
                .visitDatetime(e.getVisitDatetime())
                .technicianId(e.getTechnicianId())
                .visitNotes(e.getVisitNotes())
                .solved(e.getSolved())
                .partsUsed(e.getPartsUsed()) // ahora sí se devuelve el JSONB

                // si existe meterReading relacionado, mapear sus campos
                .takeMeterReading(e.getMeterReading() != null)
                .reading(e.getMeterReading() != null ? e.getMeterReading().getReading() : null)
                .readingNotes(e.getMeterReading() != null ? e.getMeterReading().getNotes() : null)

                .build();
    }

    @Override
    public ServiceVisitDto createServiceVisit(ServiceVisitDto dto) {
        ServiceVisitEntity e = new ServiceVisitEntity();
        e.setServiceRequestId(dto.getServiceRequestId());
        e.setVisitDatetime(dto.getVisitDatetime() != null ? dto.getVisitDatetime() : OffsetDateTime.now());
        e.setTechnicianId(dto.getTechnicianId());
        e.setVisitNotes(dto.getVisitNotes());
        e.setMeterReadingBefore(dto.getMeterReadingBefore());
        e.setMeterReadingAfter(dto.getMeterReadingAfter());
        e.setSolved(dto.getSolved() != null ? dto.getSolved() : false);
        e.setCreatedAt(OffsetDateTime.now());
        // partsUsed serialized to JSON if dto.partsUsed != null -> left as null for now
        if (dto.getPartsUsed() != null) {
            e.setPartsUsed(dto.getPartsUsed());
        } else {
            e.setPartsUsed(null); // 👈 explícito para que no intente castear
        }
        e = repo.save(e);
        return toDto(e);
    }

    @Override
    public List<ServiceVisitDto> getVisitsByRequest(Long requestId) {
        return repo.findByServiceRequestId(requestId).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ServiceVisitDto> getVisitsByTechnician(Long technicianId) {
        return repo.findByTechnicianId(technicianId).stream().map(this::toDto).collect(Collectors.toList());
    }
}


// 