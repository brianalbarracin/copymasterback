package co.edu.sena.tu_unidad.repository;

import co.edu.sena.tu_unidad.entity.MachineMovementEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineMovementRepository extends JpaRepository<MachineMovementEntity, Long> {
    List<MachineMovementEntity> findByMachineIdOrderByEffectiveDateDesc(Long machineId);
}
