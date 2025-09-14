package co.edu.sena.tu_unidad.service;

import co.edu.sena.tu_unidad.dto.ServiceVisitDto;

import java.util.List;

public interface ServiceVisitService {
    ServiceVisitDto createServiceVisit(ServiceVisitDto dto);
    List<ServiceVisitDto> getVisitsByRequest(Long requestId);
    List<ServiceVisitDto> getVisitsByTechnician(Long technicianId);
}


// 