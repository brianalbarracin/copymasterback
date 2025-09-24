package co.edu.sena.tu_unidad.service;

import co.edu.sena.tu_unidad.dto.VisitPartDto;

import java.util.List;

public interface VisitPartService {
    VisitPartDto create(VisitPartDto dto);
    List<VisitPartDto> getByServiceVisit(Long serviceVisitId);
    void delete(Long id);
}
