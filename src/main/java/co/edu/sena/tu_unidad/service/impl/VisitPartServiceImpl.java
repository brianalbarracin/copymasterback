package co.edu.sena.tu_unidad.service.impl;

import co.edu.sena.tu_unidad.dto.VisitPartDto;
import co.edu.sena.tu_unidad.entity.PartEntity;
import co.edu.sena.tu_unidad.entity.ServiceVisitEntity;
import co.edu.sena.tu_unidad.entity.VisitPartEntity;
import co.edu.sena.tu_unidad.repository.PartRepository;
import co.edu.sena.tu_unidad.repository.ServiceVisitRepository;
import co.edu.sena.tu_unidad.repository.VisitPartRepository;
import co.edu.sena.tu_unidad.service.VisitPartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitPartServiceImpl implements VisitPartService {

    private final VisitPartRepository visitPartRepository;
    private final ServiceVisitRepository serviceVisitRepository;
    private final PartRepository partRepository;

    @Override
    public VisitPartDto create(VisitPartDto dto) {
        ServiceVisitEntity visit = serviceVisitRepository.findById(dto.getServiceVisitId())
                .orElseThrow(() -> new RuntimeException("ServiceVisit not found"));

        PartEntity part = partRepository.findById(dto.getPartId())
                .orElseThrow(() -> new RuntimeException("Part not found"));

        VisitPartEntity entity = VisitPartEntity.builder()
                .serviceVisit(visit)
                .part(part)
                .qty(dto.getQty())
                .serialNumber(dto.getSerialNumber())
                .cost(dto.getCost())
                .build();

        VisitPartEntity saved = visitPartRepository.save(entity);

        dto.setId(saved.getId());
        return dto;
    }

    @Override
    public List<VisitPartDto> getByServiceVisit(Long serviceVisitId) {
        return visitPartRepository.findByServiceVisitId(serviceVisitId)
                .stream()
                .map(v -> VisitPartDto.builder()
                        .id(v.getId())
                        .serviceVisitId(v.getServiceVisit().getId())
                        .partId(v.getPart().getId())
                        .qty(v.getQty())
                        .serialNumber(v.getSerialNumber())
                        .cost(v.getCost())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        visitPartRepository.deleteById(id);
    }
}

