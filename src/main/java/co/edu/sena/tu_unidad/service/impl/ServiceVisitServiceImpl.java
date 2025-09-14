package co.edu.sena.tu_unidad.service.impl;

import co.edu.sena.tu_unidad.dto.ServiceVisitDto;
import co.edu.sena.tu_unidad.entity.ServiceVisitEntity;
import co.edu.sena.tu_unidad.repository.ServiceVisitRepository;
import co.edu.sena.tu_unidad.service.ServiceVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceVisitServiceImpl implements ServiceVisitService {

    @Autowired
    private ServiceVisitRepository repo;

    private ServiceVisitDto toDto(ServiceVisitEntity e) {
        if (e == null) return null;
        return ServiceVisitDto.builder()
                .id(e.getId())
                .serviceRequestId(e.getServiceRequestId())
                .visitDatetime(e.getVisitDatetime())
                .technicianId(e.getTechnicianId())
                .visitNotes(e.getVisitNotes())
                .meterReadingBefore(e.getMeterReadingBefore())
                .meterReadingAfter(e.getMeterReadingAfter())
                .solved(e.getSolved())
                .partsUsed(null)
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