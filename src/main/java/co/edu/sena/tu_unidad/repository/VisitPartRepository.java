package co.edu.sena.tu_unidad.repository;

import co.edu.sena.tu_unidad.entity.VisitPartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitPartRepository extends JpaRepository<VisitPartEntity, Long> {
    List<VisitPartEntity> findByServiceVisitId(Long serviceVisitId);
}
