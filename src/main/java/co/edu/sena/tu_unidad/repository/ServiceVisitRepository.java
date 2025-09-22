package co.edu.sena.tu_unidad.repository;

import co.edu.sena.tu_unidad.entity.ServiceVisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceVisitRepository extends JpaRepository<ServiceVisitEntity, Long> {
    List<ServiceVisitEntity> findByServiceRequestId(Long serviceRequestId);
    List<ServiceVisitEntity> findByTechnicianId(Long technicianId);

}


// 