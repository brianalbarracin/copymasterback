package co.edu.sena.tu_unidad.repository;

import co.edu.sena.tu_unidad.entity.MachineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MachineRepository extends JpaRepository<MachineEntity, Long> {
    Optional<MachineEntity> findByCompanySerial(String companySerial);
    Optional<MachineEntity> findByCompanyNumber(String companyNumber);
    List<MachineEntity> findByModel(String model);
}


// 