package co.edu.sena.tu_unidad.repository;

import co.edu.sena.tu_unidad.entity.ServiceRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequestEntity, Long> {
    List<ServiceRequestEntity> findByMachineId(Long machineId);
    List<ServiceRequestEntity> findByCustomerId(Long customerId);
    List<ServiceRequestEntity> findByReportedAtBetween(OffsetDateTime start, OffsetDateTime end);
    ServiceRequestEntity findTopByMachineIdAndRootCauseAndReportedAtAfterOrderByReportedAtAsc(Long machineId, String rootCause, OffsetDateTime after);

}


// 