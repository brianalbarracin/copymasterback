package co.edu.sena.tu_unidad.repository;

import co.edu.sena.tu_unidad.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
    Optional<LocationEntity> findByCode(String code);
}


// 