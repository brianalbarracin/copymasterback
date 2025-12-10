package co.edu.sena.tu_unidad.service.impl;

import co.edu.sena.tu_unidad.domain.enums.ServiceType;
import co.edu.sena.tu_unidad.util.RequestNumberGenerator;
import co.edu.sena.tu_unidad.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import co.edu.sena.tu_unidad.entity.ServiceRequestEntity;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class RequestCounterInitializer {
    @Autowired
    private ServiceRequestRepository requestRepository;

    @Autowired
    private RequestNumberGenerator numberGenerator;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeCounters() {
        System.out.println("🚀 INICIALIZANDO CONTADORES DE SOLICITUDES...");

        // Obtener la fecha de hoy
        LocalDate today = LocalDate.now();
        String todayKey = today.format(DateTimeFormatter.ofPattern("ddMMyy"));

        // Obtener todas las solicitudes del día de hoy
        // CORRECCIÓN: Usar LocalDateTime y luego convertirlo a OffsetDateTime
        LocalDateTime startOfDayLocal = today.atStartOfDay();
        LocalDateTime endOfDayLocal = today.plusDays(1).atStartOfDay();

        // Convertir a OffsetDateTime con UTC
        OffsetDateTime startOfDay = startOfDayLocal.atOffset(ZoneOffset.UTC);
        OffsetDateTime endOfDay = endOfDayLocal.atOffset(ZoneOffset.UTC);

        List<co.edu.sena.tu_unidad.entity.ServiceRequestEntity> todayRequests =
                requestRepository.findByReportedAtBetween(startOfDay, endOfDay);

        System.out.println("Solicitudes encontradas hoy: " + todayRequests.size());

        // Inicializar contadores para cada tipo de servicio
        for (ServiceType serviceType : ServiceType.values()) {
            long countForType = todayRequests.stream()
                    .filter(r -> r.getServiceType() == serviceType)
                    .count();

            if (countForType > 0) {
                numberGenerator.initializeCounter(serviceType.name(), todayKey, (int) countForType);
                System.out.println("Contador para " + serviceType + ": " + countForType);
            }
        }

        System.out.println("✅ CONTADORES INICIALIZADOS CORRECTAMENTE");
    }

}
