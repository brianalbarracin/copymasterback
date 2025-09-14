package co.edu.sena.tu_unidad.repository;

import co.edu.sena.tu_unidad.entity.MeterReadingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeterReadingRepository extends JpaRepository<MeterReadingEntity, Long> {
    List<MeterReadingEntity> findByMachineIdOrderByReadingDateDesc(Long machineId);
}


// 