package co.edu.sena.tu_unidad.service.impl;

import co.edu.sena.tu_unidad.dto.ServiceRequestDto;
import co.edu.sena.tu_unidad.entity.ServiceRequestEntity;
import co.edu.sena.tu_unidad.repository.ServiceRequestRepository;
import co.edu.sena.tu_unidad.service.ServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.sena.tu_unidad.repository.ServiceRequestRepository;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceRequestServiceImpl implements ServiceRequestService {

    @Autowired
    private ServiceRequestRepository repo;

    private ServiceRequestDto toDto(ServiceRequestEntity e) {
        if (e == null) return null;
        return ServiceRequestDto.builder()
                .id(e.getId())
                .requestNumber(e.getRequestNumber())
                .customerId(e.getCustomerId())
                .machineId(e.getMachineId())
                .companySerial(e.getCompanySerial())
                .companyNumber(e.getCompanyNumber())
                .createdByUserId(e.getCreatedByUserId())
                .reportedAt(e.getReportedAt())
                .reportedChannel(e.getReportedChannel())
                .serviceType(e.getServiceType())
                .description(e.getDescription())
                .rootCause(e.getRootCause())
                .status(e.getStatus())
                .isRepeated(e.getIsRepeated())
                .repeatedOfRequestId(e.getRepeatedOfRequestId())
                .assignedTechnicianId(e.getAssignedTechnicianId())
                .assignedAt(e.getAssignedAt())
                .closedAt(e.getClosedAt())
                .resolution(e.getResolution())
                .build();
    }

    @Override
    public List<ServiceRequestDto> getAllServiceRequests() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ServiceRequestDto> getPendingServiceRequests() {
        return repo.findAll().stream()
                .filter(r -> r.getStatus() == null || r.getStatus().equalsIgnoreCase("abierto"))
                .map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public ServiceRequestDto getServiceRequestById(Long id) {
        return repo.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public ServiceRequestDto createServiceRequest(ServiceRequestDto dto) {
        ServiceRequestEntity e = new ServiceRequestEntity();
        e.setRequestNumber(dto.getRequestNumber() != null ? dto.getRequestNumber() : "SR-" + System.currentTimeMillis());
        e.setCustomerId(dto.getCustomerId());
        e.setMachineId(dto.getMachineId());
        e.setCompanySerial(dto.getCompanySerial());
        e.setCompanyNumber(dto.getCompanyNumber());
        e.setCreatedByUserId(dto.getCreatedByUserId());
        e.setReportedAt(dto.getReportedAt() != null ? dto.getReportedAt() : OffsetDateTime.now());
        e.setReportedChannel(dto.getReportedChannel());
        e.setServiceType(dto.getServiceType());
        e.setDescription(dto.getDescription());
        e.setRootCause(dto.getRootCause());
        e.setStatus(dto.getStatus() != null ? dto.getStatus() : "abierto");
        e.setIsRepeated(false);
        e.setCreatedAt(OffsetDateTime.now());

        // lógica básica para marcar repetido (ejemplo: buscar anterior en 30 días)
        if (e.getMachineId() != null && e.getRootCause() != null) {
            OffsetDateTime threshold = e.getReportedAt().minus(30, ChronoUnit.DAYS);
            ServiceRequestEntity prior = repo.findTopByMachineIdAndRootCauseAndReportedAtAfterOrderByReportedAtAsc(e.getMachineId(), e.getRootCause(), threshold);
            if (prior != null) {
                e.setIsRepeated(true);
                e.setRepeatedOfRequestId(prior.getId());
            }
        }

        e = repo.save(e);
        return toDto(e);
    }

    @Override
    public ServiceRequestDto updateServiceRequestStatus(Long id, String status) {
        ServiceRequestEntity e = repo.findById(id).orElse(null);
        if (e == null) return null;
        e.setStatus(status);
        if ("cerrado".equalsIgnoreCase(status) || "solved".equalsIgnoreCase(status)) {
            e.setClosedAt(OffsetDateTime.now());
        }
        repo.save(e);
        return toDto(e);
    }

    @Override
    public List<ServiceRequestDto> getServiceRequestsByCustomer(Long customerId) {
        return repo.findByCustomerId(customerId).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ServiceRequestDto> getServiceRequestsByMachine(Long machineId) {
        return repo.findByMachineId(machineId).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Autowired
    private ServiceRequestRepository repository;

    @Override
    public List<ServiceRequestEntity> getRequestsByCustomer(Long customerId) {
        return repository.findByCustomerId(customerId);
    }
}


// 