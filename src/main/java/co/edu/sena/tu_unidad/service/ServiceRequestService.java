package co.edu.sena.tu_unidad.service;

import co.edu.sena.tu_unidad.dto.ServiceRequestDto;
import co.edu.sena.tu_unidad.entity.ServiceRequestEntity;

import java.time.OffsetDateTime;
import java.util.List;

public interface ServiceRequestService {
    List<ServiceRequestDto> getAllServiceRequests();
    List<ServiceRequestDto> getPendingServiceRequests();
    ServiceRequestDto getServiceRequestById(Long id);
    ServiceRequestDto createServiceRequest(ServiceRequestDto dto);
    ServiceRequestDto updateServiceRequestStatus(Long id, String status);
    List<ServiceRequestDto> getServiceRequestsByCustomer(Long customerId);
    List<ServiceRequestDto> getServiceRequestsByMachine(Long machineId);
    // agrega métodos útiles para repetidos y reportes
    List<ServiceRequestEntity> getRequestsByCustomer(Long customerId);
}


// 