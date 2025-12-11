package co.edu.sena.tu_unidad.repository;

import co.edu.sena.tu_unidad.entity.ReadingTonerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReadingTonerRepository extends JpaRepository<ReadingTonerEntity, Long> {
    List<ReadingTonerEntity> findByMachineIdOrderByReadingDateDesc(Long machineId);
    List<ReadingTonerEntity> findByTechnicianId(Long technicianId);
}
