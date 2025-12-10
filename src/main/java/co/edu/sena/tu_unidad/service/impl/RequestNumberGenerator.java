package co.edu.sena.tu_unidad.service.impl;

import co.edu.sena.tu_unidad.domain.enums.ServiceType;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RequestNumberGenerator {

    private final Map<String, Integer> dailyCounters = new ConcurrentHashMap<>();

    public String generateNextNumber(ServiceType serviceType) {
        LocalDate today = LocalDate.now();
        String dateKey = today.format(DateTimeFormatter.ofPattern("ddMMyy"));

        // Crear clave para el tipo de servicio en el día actual
        String counterKey = serviceType.name() + "_" + dateKey;

        // Obtener o inicializar el contador para este tipo en este día
        int currentCounter = dailyCounters.getOrDefault(counterKey, 0) + 1;
        dailyCounters.put(counterKey, currentCounter);

        // Formatear contador a 3 dígitos
        String counterStr = String.format("%03d", currentCounter);

        // Obtener código del tipo de servicio
        String typeCode = serviceType.getCodigo();

        return counterStr + typeCode + dateKey;
    }

    // Método para inicializar contadores desde base de datos al iniciar
    public void initializeCounter(String serviceType, String dateKey, int lastCounter) {
        String counterKey = serviceType + "_" + dateKey;
        dailyCounters.put(counterKey, lastCounter);
    }
}
