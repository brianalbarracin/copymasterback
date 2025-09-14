package co.edu.sena.tu_unidad.repository;

import co.edu.sena.tu_unidad.entity.PartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartRepository extends JpaRepository<PartEntity, Long> {
    PartEntity findBySku(String sku);
}


// 