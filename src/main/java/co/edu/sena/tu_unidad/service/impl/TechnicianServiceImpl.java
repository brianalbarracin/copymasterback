package co.edu.sena.tu_unidad.service.impl;

import co.edu.sena.tu_unidad.dto.TechnicianDto;
import co.edu.sena.tu_unidad.entity.TechnicianEntity;
import co.edu.sena.tu_unidad.repository.TechnicianRepository;
import co.edu.sena.tu_unidad.repository.ServiceVisitRepository;
import co.edu.sena.tu_unidad.service.TechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TechnicianServiceImpl implements TechnicianService {

    @Autowired
    private TechnicianRepository repo;

    @Autowired
    private ServiceVisitRepository visitRepo;

    private TechnicianDto toDto(TechnicianEntity e) {
        if (e == null) return null;
        return TechnicianDto.builder()
                .id(e.getId())
                .identification(e.getIdentification())
                .fullName(e.getFullName())
                .phone(e.getPhone())
                .email(e.getEmail())
                .active(e.getActive())
                .build();
    }

    @Override
    public List<TechnicianDto> getAllTechnicians() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public TechnicianDto getTechnicianById(Long id) {
        return repo.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public TechnicianDto createTechnician(TechnicianDto dto) {
        TechnicianEntity e = new TechnicianEntity();
        e.setIdentification(dto.getIdentification());
        e.setFullName(dto.getFullName());
        e.setPhone(dto.getPhone());
        e.setEmail(dto.getEmail());
        e.setActive(dto.getActive() != null ? dto.getActive() : true);
        e = repo.save(e);
        return toDto(e);
    }

    @Override
    public TechnicianDto updateTechnician(Long id, TechnicianDto dto) {
        TechnicianEntity e = repo.findById(id).orElse(null);
        if (e == null) return null;
        e.setFullName(dto.getFullName());
        e.setPhone(dto.getPhone());
        e.setEmail(dto.getEmail());
        e.setActive(dto.getActive());
        repo.save(e);
        return toDto(e);
    }

    @Override
    public Object getTechnicianPerformance(Long id, OffsetDateTime startDate, OffsetDateTime endDate) {
        // Implementar lógica de rendimiento: ponderado por visitas, repetidos, tiempos, etc.
        // Aquí retorno un objeto simple con conteos para que el frontend muestre.
        long totalVisits = visitRepo.findByTechnicianId(id).stream()
                .filter(v -> {
                    if (v.getVisitDatetime() == null) return false;
                    return !v.getVisitDatetime().isBefore(startDate) && !v.getVisitDatetime().isAfter(endDate);
                })
                .count();
        return java.util.Map.of("technicianId", id, "totalVisits", totalVisits);
    }

    @Override
    public Object getTechniciansComparison(OffsetDateTime startDate, OffsetDateTime endDate) {
        // Retorna un mapa simple con counts por técnico (puedes enriquecer para gráficos)
        List<TechnicianEntity> techs = repo.findAll();
        java.util.Map<Long, Long> map = new java.util.HashMap<>();
        for (TechnicianEntity t : techs) {
            long count = visitRepo.findByTechnicianId(t.getId()).stream()
                    .filter(v -> v.getVisitDatetime() != null && !v.getVisitDatetime().isBefore(startDate) && !v.getVisitDatetime().isAfter(endDate))
                    .count();
            map.put(t.getId(), count);
        }
        return map;
    }
}


// 