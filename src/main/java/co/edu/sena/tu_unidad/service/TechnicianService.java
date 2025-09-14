package co.edu.sena.tu_unidad.service;

import co.edu.sena.tu_unidad.dto.TechnicianDto;

import java.time.OffsetDateTime;
import java.util.List;

public interface TechnicianService {
    List<TechnicianDto> getAllTechnicians();
    TechnicianDto getTechnicianById(Long id);
    TechnicianDto createTechnician(TechnicianDto dto);
    TechnicianDto updateTechnician(Long id, TechnicianDto dto);
    Object getTechnicianPerformance(Long id, OffsetDateTime startDate, OffsetDateTime endDate);
    Object getTechniciansComparison(OffsetDateTime startDate, OffsetDateTime endDate);
}


// 